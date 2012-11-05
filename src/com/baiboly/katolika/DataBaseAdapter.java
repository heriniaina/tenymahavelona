package com.baiboly.katolika;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseAdapter {
	private Context context;
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;

	public DataBaseAdapter(Context context) {
		this.context = context;
	}

	public DataBaseAdapter open() throws SQLException {
		dbHelper = new DataBaseHelper(context);
		database = dbHelper.getDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public Cursor fetchAllBooks() {
		StringBuilder q = new StringBuilder("SELECT b._id as book_id, b.b_sname as MalayalamShortName, count(t._id) as num_chptr, '' as EnglishShortName FROM b_boky as b LEFT JOIN b_toko as t ON t.t_b_id=b._id GROUP BY b._id, b.b_sname ORDER BY b.b_order");        
        return database.rawQuery(q.toString(), null);

	}
	
	public Cursor fetchChapter(int bookId, int chapterId, String table) {
		StringBuilder q = new StringBuilder("SELECT a.b_and as verse_id, a.b_text as verse_text FROM b_and as a LEFT JOIN b_toko as t ON t._id=a.b_t_id WHERE t.t_b_id = ? AND a.b_toko = ? ORDER BY a.b_and");        
        return database.rawQuery(q.toString(), new String[]{bookId + "", chapterId + ""});
	}
	
	public Cursor fetchVerses(int bookId, int chapterId, String table, ArrayList<String> verses, char lang) {
		StringBuilder q = new StringBuilder("SELECT a.b_and as verse_id, a.b_text as verse_text FROM b_and as a LEFT JOIN b_toko as t ON t._id=a.b_t_id WHERE t.t_b_id = ? AND a.b_toko = ? ");
		q.append(" AND a.b_and IN (");
		if(verses != null) {
			int count = 0;
			for(String v : verses) {
				if(v.charAt(0) == lang) {
					if(count++ > 0) {
						q.append(",");
					}
					q.append(v.substring(1));
				}
			}
		}
		q.append(") order by a.b_and");
		
		return database.rawQuery(q.toString(), new String[]{bookId + "", chapterId + ""});
	}
	
	public Cursor fetchVerses(int bookId, int chapterId, String table, ArrayList<Integer> verses) {
		StringBuilder q = new StringBuilder("SELECT a.b_and as verse_id, a.b_text as verse_text FROM b_and as a LEFT JOIN b_toko as t ON t._id=a.b_t_id WHERE t.t_b_id = ? AND a.b_toko = ? ");
		q.append(" AND a.b_and IN (");
		if(verses != null) {
			int count = 0;
			for(Integer v : verses) {
				if(count++ > 0) {
					q.append(",");
				}
				q.append(v);
			}
		}
		q.append(") order by a.b_and");
		
		return database.rawQuery(q.toString(), new String[]{bookId + "", chapterId + ""});
	}
}
