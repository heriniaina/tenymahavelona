package com.baiboly.katolika;

import java.util.ArrayList;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import org.apache.http.util.ByteArrayBuffer;
import 	java.io.BufferedInputStream;
import android.net.Uri;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;
import android.content.SharedPreferences;


public class MalayalamBibleActivity extends BaseActivity {
	private Context context = null;
    private Handler mHandler;
	
	private static boolean preferenceChanged = false;
	
	public static void setPreferenceChanged(boolean preferenceChanged) {
		MalayalamBibleActivity.preferenceChanged = preferenceChanged;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this;
		preferenceChanged = false;
        mHandler = new Handler();
        
        /* Get Last Update Time from Preferences */
        SharedPreferences prefs = getPreferences(0);
        long lastUpdateTime =  prefs.getLong("lastUpdateTime", 0);
        
        
        /* Should Activity Check for Updates Now? */
        /* monthly check */
        if ((lastUpdateTime + (30 * 24 * 60 * 60 * 1000)) < System.currentTimeMillis()) {

            /* Save current timestamp for next Check*/
            lastUpdateTime = System.currentTimeMillis();            
            SharedPreferences.Editor editor = getPreferences(0).edit();
            editor.putLong("lastUpdateTime", lastUpdateTime);
            editor.commit();        

            /* Start Update */            
            checkUpdate.start();
        }
		
		getContent();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(preferenceChanged) {
			preferenceChanged = false;
			getContent();
		}
	}

	private void getContent() {
		Preference pref = Preference.getInstance(this);
		
		setContentView(R.layout.main);
		
		showContent();
	}
	
	/*
	private void showInfo() {
		Button info = (Button) findViewById(R.id.infoButton);
	    info.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showInfoActivity(context);
			}
		});
	}
	*/
	
	private void showContent() {		
		//showInfo();
		
		Button back = (Button) findViewById(R.id.backButton);
	    back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		Preference pref = Preference.getInstance(this);
		int renderingFix = pref.getRendering();
		float fontSize = pref.getFontSize();
		
		if(pref.getSecLanguage() == Preference.LANG_NONE) {
			showSingleLanguage(renderingFix, fontSize, pref.getLanguage());
		}
		else {
			showTwoLanguages(renderingFix, fontSize, pref.getLanguage(), pref.getSecLanguage(), pref.getLanguageLayout());
		}		
	}
	
	private void showSingleLanguage(int renderingFix, float fontSize, int language) {
		Resources res = getResources();
		//Typeface tf = language == Preference.LANG_MALAYALAM ? Typeface.createFromAsset(getAssets(),
		//		res.getString(R.string.font_name)) : null;
		
		int rowLayout = R.layout.bookrow;
		int rowHeaderLayout = R.layout.tablerowsection;
		
		TextView tv = (TextView) findViewById(R.id.heading);
        tv.setText(R.string.books);
		//if(tf == null) {
		//	tv.setText(R.string.bookseng);
		//}
		//else {
		//	tv.setTypeface(tf);
		//	tv.setText(ComplexCharacterMapper.fix(res.getString(R.string.books), renderingFix));
		//}
		
		TableLayout tl = (TableLayout) findViewById(R.id.booksLayout);
		tl.removeAllViews();
		
		TableRow tr;
		TextView t;

		final ArrayList<Book> books = Utils.getBooks();
		
		LayoutInflater inflater = getLayoutInflater();
		Book book = null;
		for(int c=0; c<73; c++) {
			book = books.get(c);
			
			if (c == 0) {
				tr = (TableRow)inflater.inflate(rowHeaderLayout, tl, false);
				t = (TextView) tr.findViewById(R.id.section);
				
				t.setTextSize(fontSize);
                
				// if(tf == null) {
					// t.setText(R.string.oldtestamenteng);
				// }
				// else {
					//t.setTypeface(tf);
					t.setText(R.string.oldtestament);
				// }
				
				tl.addView(tr);
			} else if (c == 46) {
				tr = (TableRow)inflater.inflate(rowHeaderLayout, tl, false);
				t = (TextView) tr.findViewById(R.id.section);
				
				t.setTextSize(fontSize);
				// if(tf == null) {
					// t.setText(R.string.newtestamenteng);
				// }
				// else {
					// t.setTypeface(tf);
					t.setText(R.string.newtestament);
				// }
				
				tl.addView(tr);
			}
			
			tr = (TableRow)inflater.inflate(rowLayout, tl, false);
			t = (TextView) tr.findViewById(R.id.book);
			
			tr.setClickable(true);
			tr.setId(c+1);
			tr.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Book book = books.get(v.getId() - 1);
					if(book.getChapters() == 1) {
						finish();
						Intent chapterView = new Intent(context, ChapterViewActivity.class);
						chapterView.putExtra("com.baiboly.katolika.Book", book);
						chapterView.putExtra("chapterId", 1);
						startActivity(chapterView);
					}
					else {
						finish();
						Intent chaptersView = new Intent(context, ChaptersActivity.class);
						chaptersView.putExtra("com.baiboly.katolika.Book", book);
					    startActivity(chaptersView);
					}
				}
			});
			t.setTextSize(fontSize);
			// if(tf == null) {
				// t.setText(book.getEnglishName());
			// }
			// else {
				// t.setTypeface(tf);
				t.setText(book.getName());
			// }
			
			tl.addView(tr);
		}
	}
	
	private void showTwoLanguages(int renderingFix, float fontSize, int language, int secLanguage, int layout) {
		Resources res = getResources();
		Typeface tf = Typeface.createFromAsset(getAssets(),
				res.getString(R.string.font_name));
		
		boolean showBoth = true;
		if(language != Preference.LANG_MALAYALAM && secLanguage != Preference.LANG_MALAYALAM) {
			showBoth = false;
		}
		
		int rowLayout = showBoth ? R.layout.bookrowboth : R.layout.bookrow;
		int rowHeaderLayout = showBoth ? R.layout.tablerowsectionboth : R.layout.tablerowsection;
		
		TextView tv = (TextView) findViewById(R.id.heading);
		if(language == Preference.LANG_MALAYALAM) {
			tv.setTypeface(tf);
			tv.setText(ComplexCharacterMapper.fix(res.getString(R.string.books), renderingFix));
		}
		else {
			tv.setText(res.getString(R.string.bookseng));
		}
		
		tv = (TextView) findViewById(R.id.headingSec);
		//tv.setVisibility(View.VISIBLE);
		if(secLanguage == Preference.LANG_MALAYALAM) {
			tv.setTypeface(tf);
			tv.setText(ComplexCharacterMapper.fix(res.getString(R.string.books), renderingFix));
		}
		else {
			tv.setText(res.getString(R.string.bookseng));
		}
		
		TableLayout tl = (TableLayout) findViewById(R.id.booksLayout);
		tl.removeAllViews();
		
		TableRow tr;
		TextView t;

		final ArrayList<Book> books = Utils.getBooks();
		
		LayoutInflater inflater = getLayoutInflater();
		
		int len = 73;
		Book book = null;
		for(int c=0; c<len; c++) {
			book = books.get(c);
			
			if (c == 0) {
				tr = (TableRow)inflater.inflate(rowHeaderLayout, tl, false);
				t = (TextView) tr.findViewById(R.id.section);
				
				t.setTextSize(fontSize);
				if(language == Preference.LANG_MALAYALAM) {
					t.setTypeface(tf);
					t.setText(R.string.oldtestament);
				}
				else {
					t.setText(R.string.oldtestamenteng);
				}
				
				if(showBoth) {
					t = (TextView) tr.findViewById(R.id.sectionSec);
					t.setTextSize(fontSize);
					if(secLanguage == Preference.LANG_MALAYALAM) {
						t.setTypeface(tf);
						t.setText(R.string.oldtestament);
					}
					else {
						t.setText(R.string.oldtestamenteng);
					}
				}
				
				tl.addView(tr);
			} else if (c == 39) {
				if(layout == Preference.LAYOUT_SIDE_BY_SIDE) {
					tl = (TableLayout) findViewById(R.id.booksLayoutNT);
				}
				
				tr = (TableRow)inflater.inflate(rowHeaderLayout, tl, false);
				t = (TextView) tr.findViewById(R.id.section);
				
				t.setTextSize(fontSize);
				if(language == Preference.LANG_MALAYALAM) {
					t.setTypeface(tf);
					t.setText(R.string.newtestament);
				}
				else {
					t.setText(R.string.newtestamenteng);
				}
				
				if(showBoth) {
					t = (TextView) tr.findViewById(R.id.sectionSec);
					t.setTextSize(fontSize);
					if(secLanguage == Preference.LANG_MALAYALAM) {
						t.setTypeface(tf);
						t.setText(R.string.newtestament);
					}
					else {
						t.setText(R.string.newtestamenteng);
					}
				}
				
				tl.addView(tr);
			}
			
			tr = (TableRow)inflater.inflate(rowLayout, tl, false);
			t = (TextView) tr.findViewById(R.id.book);
			
			tr.setClickable(true);
			tr.setId(c+1);
			tr.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Book book = books.get(v.getId() - 1);
					if(book.getChapters() == 1) {
						finish();
						Intent chapterView = new Intent(context, ChapterViewActivity.class);
						chapterView.putExtra("com.baiboly.katolika.Book", book);
						chapterView.putExtra("chapterId", 1);
						startActivity(chapterView);
					}
					else {
						finish();
						Intent chaptersView = new Intent(context, ChaptersActivity.class);
						chaptersView.putExtra("com.baiboly.katolika.Book", book);
					    startActivity(chaptersView);
					}
				}
			});
			t.setTextSize(fontSize);
			if(language == Preference.LANG_MALAYALAM) {
				t.setTypeface(tf);
				t.setText(book.getName());
			}
			else {
				t.setText(book.getEnglishName());
			}
			
			if(showBoth) {
				t = (TextView) tr.findViewById(R.id.bookSec);
				t.setTextSize(fontSize);
				if(secLanguage == Preference.LANG_MALAYALAM) {
					t.setTypeface(tf);
					t.setText(book.getName());
				}
				else {
					t.setText(book.getEnglishName());
				}
			}

			tl.addView(tr);
		}
	}	
    
   	public void onAppMenuLeftClickEvent(View sender)
	{
            startActivity(new Intent(MalayalamBibleActivity.this, SearchViewActivity.class));
            //startActivity(new Intent(this, SearchViewActivity.class));
	}
    

    /* This Thread checks for Updates in the Background */
    private Thread checkUpdate = new Thread() {
        public void run() {
            try {
                URL updateURL = new URL("http://baiboly.katolika.org/media/com.baiboly.katolika/version.txt");                
                URLConnection conn = updateURL.openConnection(); 
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                
                int current = 0;
                while((current = bis.read()) != -1){
                     baf.append((byte)current);
                }

                /* Convert the Bytes read to a String. */
                final String s = new String(baf.toByteArray());         
                
                /* Get current Version Number */
                int curVersion = getPackageManager().getPackageInfo("com.baiboly.katolika", 0).versionCode;
                int newVersion = Integer.valueOf(s);
                
                
                
                /* Is a higher version than the current already out? */
                if (newVersion > curVersion) {
                    /* Post a Handler for the UI to pick up and open the Dialog */
                    mHandler.post(showUpdate);
                }                
            } catch (Exception e) {
            }
        }
    };

    /* This Runnable creates a Dialog and asks the user to open the Market */ 
    private Runnable showUpdate = new Runnable(){
           public void run(){
            new AlertDialog.Builder(MalayalamBibleActivity.this)
            //.setIcon(R.drawable.icon)
            .setTitle("Voatra vaovao")
            .setMessage("Nisy version vaovao nivoaka. Tianao hojerena ny momba azy?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                            /* User clicked OK so do some stuff */
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:com.baiboly.katolika"));
                            startActivity(intent);
                    }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                            /* User clicked Cancel */
                    }
            })
            .show();
           }
    };        

}