package com.baiboly.katolika;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static String[] DB_DELETE = {"baiboly-1-0.jpg"};
	private static String DB_PATH = "/data/data/com.baiboly.katolika/databases/";

	private static String DB_NAME = "baiboly-1-1.jpg";

	private String dbPath = null;
	private SQLiteDatabase bibleDB;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		
		try {
			createDataBase();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {
			try {
				copyDataBase();

			} catch (IOException e) {
				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = getDbPath() + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	
    /* private void copyDataBase() throws IOException {
		String path = getDbPath();
		
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		for(String df : DB_DELETE) {
			try {
				File f = new File(path + df);
				if(f != null && f.exists()) {
					f.delete();
				}
				
				if(!DB_PATH.equals(path)) {
					f = new File(DB_PATH + df);
					if(f != null && f.exists()) {
						f.delete();
					}
				}
			}
			catch(Exception e) {
				
			}
		}
		
		OutputStream databaseOutputStream = new FileOutputStream(
				path + DB_NAME);
		InputStream databaseInputStream;

		byte[] buffer = new byte[1024];
		
		int[] id = {
			R.raw.aaa,
			R.raw.aab,
			R.raw.aac,
			R.raw.aad,
			R.raw.aae,
			R.raw.aaf,
			R.raw.aag,
			R.raw.aah//,
		};
		
		for (int i : id) {
			databaseInputStream = myContext.getResources().openRawResource(i);
			while (databaseInputStream.read(buffer) > 0) {
				databaseOutputStream.write(buffer);
			}
			databaseInputStream.close();
		}
		
		databaseOutputStream.flush();
		databaseOutputStream.close();
	}
    */ 
     
    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        String path = getDbPath();
        
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		for(String df : DB_DELETE) {
			try {
				File f = new File(path + df);
				if(f != null && f.exists()) {
					f.delete();
				}
				
				if(!DB_PATH.equals(path)) {
					f = new File(DB_PATH + df);
					if(f != null && f.exists()) {
						f.delete();
					}
				}
			}
			catch(Exception e) {
				
			}
		}        
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = path + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = getDbPath() + DB_NAME;
		bibleDB = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

	}

	@Override
	public synchronized void close() {

		if (bibleDB != null)
			bibleDB.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private String getDbPath() {
		if(dbPath == null) {
			dbPath = DB_PATH;
			/*
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				dbPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.baiboly.katolika/db/";
			}
			else {
				dbPath = DB_PATH;
			}
			*/
		}
		
		return dbPath;
	}
	
	public SQLiteDatabase getDatabase() {
		if(this.bibleDB == null) {
			openDataBase();
		}
		return this.bibleDB;
	}
}