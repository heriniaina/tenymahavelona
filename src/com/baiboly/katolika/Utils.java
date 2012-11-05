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
        "[1, 'Jenezy', 50, 'Genesis']," + 
        "[2, 'Eksaody', 40, 'Exodus']," + 
        "[3, 'Levitika', 27, 'Leviticus']," +
        "[4, 'Fanisana', 36, 'Numbers']," + 
        "[5, 'Deotoronomy', 34, 'Deuteronomy']," + 
        "[6, 'Josoe', 24, 'Joshua']," + 
        "[7, 'Mpitsara', 21, 'Judges']," +
        "[8, 'Rota', 4, 'Ruth']," + 
        "[9, '1 Samoela', 31, '1 Samuel']," + 
        "[10, '2 Samoela', 24, '2 Samuel']," + 
        "[11, '1 Mpanjaka', 22, '1 Kings']," +
        "[12, '2 Mpanjaka', 25, '2 Kings']," + 
        "[13, '1 Tantara', 29, '1 Chronicles']," + 
        "[14, '2 Tantara', 36, '2 Chronicles']," +
        "[15, 'Esdrasa', 10, 'Ezra']," + 
        "[16, 'Nehemia', 13, 'Nehemiah']," + 
    "[17, 'Tobia', 14, 'Nehemiah']," + 
    "[18, 'Jodita', 16, 'Nehemiah']," + 
        "[19, 'Estera', 16, 'Esther']," + 
    "[20, '1 Makabeo', 16, 'Nehemiah']," + 
    "[21, '2 Makabeo', 15, 'Nehemiah']," + 
        "[22, 'Joba', 42, 'Job']," +
        "[23, 'Salamo', 150, 'Psalm']," + 
        "[24, 'Ohabolana', 31, 'Proverbs']," + 
        "[25, 'Mpitoriteny', 12, 'Ecclesiastes']," +
        "[26, 'Tononkira', 8, 'Song of Solomon']," + 
    "[27, 'Fahendrena', 19, 'Nehemiah']," + 
    "[28, 'Ekleziastika', 51, 'Nehemiah']," + 
        "[29, 'Izaia', 66, 'Isaiah']," + 
        "[30, 'Jeremia', 52, 'Jeremiah']," +
        "[31, 'Fitomaniana', 5, 'Lamentations']," + 
    "[32, 'Baroka', 6, 'Nehemiah']," + 
        "[33, 'Ezekiela', 48, 'Ezekiel']," + 
        "[34, 'Daniely', 14, 'Daniel']," +
        "[35, 'Osea', 14, 'Hosea']," + 
        "[36, 'Joely', 4, 'Joel']," + 
        "[37, 'Amosa', 9, 'Amos']," + 
        "[38, 'Abdiasa', 1, 'Obadiah']," +
        "[39, 'Jonasa', 4, 'Jonah']," + 
        "[40, 'Mikea', 7, 'Micah']," + 
        "[41, 'Nahoma', 3, 'Nahum']," + 
        "[42, 'Habak\u00f2ka', 3, 'Habakkuk']," +
        "[43, 'Zefania', 3, 'Sofonia']," + 
        "[44, 'Akjea', 2, 'Haggai']," + 
        "[45, 'Zakaria', 14, 'Zechariah']," +
        "[46, 'Malakia', 3, 'Malachi']," + 
        "[47, 'Matio', 28, 'Matthew']," + 
        "[48, 'Marka', 16, 'Mark']," + 
        "[49, 'Lioka', 24, 'Luke']," +
        "[50, 'Joany', 21, 'John']," + 
        "[51, 'Asa', 28, 'Acts']," + 
        "[52, 'Romana', 16, 'Romans']," + 
        "[53, '1 Korintiana', 16, '1 Corinthians']," +
        "[54, '2 Korintiana', 13, '2 Corinthians']," + 
        "[55, 'Galata', 6, 'Galatians']," + 
        "[56, 'Efeziana', 6, 'Ephesians']," +
        "[57, 'Filipiana', 4, 'Phillippians']," + 
        "[58, 'Kolosiana', 4, 'Colossians']," +
        "[59, '1 Tesalonisiana', 5, '1 Thessalonians']," + 
        "[60, '2 Tesalonisiana', 3, '2 Thessalonians']," + 
        "[61, '1 Timote', 6, '1 Timothy']," +
        "[62, '2 Timote', 4, '2 Timothy']," + 
        "[63, 'Tito', 3, 'Titus']," + 
        "[64, 'Filemona', 1, 'Philemon']," +
        "[65, 'Hebrio', 13, 'Hebrews']," + 
        "[66, 'Jakoba', 5, 'James']," + 
        "[67, '1 Piera', 5, '1 Peter']," + 
        "[68, '2 Piera', 3, '2 Peter']," +
        "[69, '1 Joany', 5, '1 John']," + 
        "[70, '2 Joany', 1, '2 John']," + 
        "[71, '3 Joany', 1, '3 John']," + 
        "[72, 'Joda', 1, 'Jude']," +
        "[73, 'Fanambar\u00e0na', 22, 'Revelation']]";
    }
