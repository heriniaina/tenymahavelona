package com.baiboly.katolika;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class Utils {
	private static ArrayList<Book> books = null;
	private static int renderingFix = 0;
	
	public static ArrayList<Book> getBooks() {
		return getBooks(false);
	}
	
	public static ArrayList<Book> getBooks(boolean recreate) {
		if(books == null || recreate) {
			books = new ArrayList<Book>(66);
			try {
				JSONArray ja = new JSONArray(booksJson);
				for (int i = 0; i < ja.length(); i++) {
					JSONArray ia = (JSONArray) ja.get(i);
                    books.add(new Book(ia.getInt(0), ComplexCharacterMapper.fix(ia.getString(1), renderingFix), ia.getInt(2), ia.getString(3), ia.getString(1)));
                }
			} catch (JSONException e) {}
		}
		
		return books;
	}
	
	public static void setRenderingFix(int value) {
		renderingFix = value;
	}
	
    private static final String booksJson = "[" +
        "[70, 'Jenezy', 50, 'Genesis']," + 
        "[68, 'Eksaody', 40, 'Exodus']," + 
        "[71, 'Levitika', 27, 'Leviticus']," +
        "[69, 'Fanisana', 36, 'Numbers']," + 
        "[67, 'Deotoronomy', 34, 'Deuteronomy']," + 
        "[17, 'Josoe', 24, 'Joshua']," + 
        "[30, 'Mpitsara', 21, 'Judges']," +
        "[23, 'Rota', 4, 'Ruth']," + 
        "[36, '1 Samoela', 31, '1 Samuel']," + 
        "[32, '2 Samoela', 24, '2 Samuel']," + 
        "[37, '1 Mpanjaka', 22, '1 Kings']," +
        "[34, '2 Mpanjaka', 25, '2 Kings']," + 
        "[39, '1 Tantara', 29, '1 Chronicles']," + 
        "[35, '2 Tantara', 36, '2 Chronicles']," +
        "[8, 'Esdrasa', 10, 'Ezra']," + 
        "[21, 'Nehemia', 13, 'Nehemiah']," + 
    "[25, 'Tobia', 14, 'Nehemiah']," + 
    "[14, 'Jodita', 16, 'Nehemiah']," + 
        "[9, 'Estera', 16, 'Esther']," + 
    "[38, '1 Makabeo', 16, 'Nehemiah']," + 
    "[33, '2 Makabeo', 15, 'Nehemiah']," + 
        "[13, 'Joba', 42, 'Job']," +
        "[72, 'Salamo', 150, 'Psalm']," + 
        "[31, 'Ohabolana', 31, 'Proverbs']," + 
        "[29, 'Mpitoriteny', 12, 'Ecclesiastes']," +
        "[73, 'Tononkira', 8, 'Song of Solomon']," + 
    "[28, 'Fahendrena', 19, 'Nehemiah']," + 
    "[27, 'Ekleziastika', 51, 'Nehemiah']," + 
        "[2, 'Izaia', 66, 'Isaiah']," + 
        "[12, 'Jeremia', 52, 'Jeremiah']," +
        "[65, 'Fitomaniana', 5, 'Lamentations']," + 
    "[6, 'Baroka', 6, 'Nehemiah']," + 
        "[10, 'Ezekiela', 48, 'Ezekiel']," + 
        "[7, 'Daniely', 14, 'Daniel']," +
        "[22, 'Osea', 14, 'Hosea']," + 
        "[15, 'Joely', 4, 'Joel']," + 
        "[5, 'Amosa', 9, 'Amos']," + 
        "[3, 'Abdiasa', 1, 'Obadiah']," +
        "[16, 'Jonasa', 4, 'Jonah']," + 
        "[19, 'Mikea', 7, 'Micah']," + 
        "[20, 'Nahoma', 3, 'Nahum']," + 
        "[11, 'Habakoka', 3, 'Habakkuk']," +
        "[24, 'Zefania', 3, 'Sofonia']," + 
        "[4, 'Akjea', 2, 'Haggai']," + 
        "[26, 'Zakaria', 14, 'Zechariah']," +
        "[18, 'Malakia', 3, 'Malachi']," + 
        "[64, 'Matio', 28, 'Matthew']," + 
        "[63, 'Marka', 16, 'Mark']," + 
        "[62, 'Lioka', 24, 'Luke']," +
        "[61, 'Joany', 21, 'John']," + 
        "[66, 'Asa', 28, 'Acts']," + 
        "[60, 'Romana', 16, 'Romans']," + 
        "[50, '1 Korintiana', 16, '1 Corinthians']," +
        "[42, '2 Korintiana', 13, '2 Corinthians']," + 
        "[57, 'Galata', 6, 'Galatians']," + 
        "[55, 'Efeziana', 6, 'Ephesians']," +
        "[56, 'Filipiana', 4, 'Phillippians']," + 
        "[59, 'Kolosiana', 4, 'Colossians']," +
        "[51, '1 Tesalonisiana', 5, '1 Thessalonians']," + 
        "[43, '2 Tesalonisiana', 3, '2 Thessalonians']," + 
        "[49, '1 Timote', 6, '1 Timothy']," +
        "[41, '2 Timote', 4, '2 Timothy']," + 
        "[54, 'Tito', 3, 'Titus']," + 
        "[53, 'Filemona', 1, 'Philemon']," +
        "[58, 'Hebrio', 13, 'Hebrews']," + 
        "[46, 'Jakoba', 5, 'James']," + 
        "[52, '1 Piera', 5, '1 Peter']," + 
        "[44, '2 Piera', 3, '2 Peter']," +
        "[48, '1 Joany', 5, '1 John']," + 
        "[40, '2 Joany', 1, '2 John']," + 
        "[45, '3 Joany', 1, '3 John']," + 
        "[47, 'Joda', 1, 'Jude']," +
        "[1, 'Fanambarana', 22, 'Revelation']]";
    }
