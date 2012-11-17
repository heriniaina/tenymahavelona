package com.baiboly.katolika;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
 
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchViewActivity extends BaseActivity {

        
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.search);
                
                Spinner spinner = (Spinner) findViewById(R.id.searchBook);
                
                final List<String> books = Utils.getBookList();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, books);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                
        }

        public void onClickSearchButton(View button) {

                
                final EditText searchText = (EditText) findViewById(R.id.searchText);
                String stext = searchText.getText().toString();
                
                final Spinner searchBook = (Spinner) findViewById(R.id.searchBook);
                //String sbook = searchBook.getSelectedItem().toString();
                int sbook = searchBook.getSelectedItemPosition();
                        
                
                
                Intent SearchResultActivity = new Intent(this, SearchResultActivity.class);

                SearchResultActivity.putExtra("com.baiboly.katolika.searchText", stext);
                SearchResultActivity.putExtra("com.baiboly.katolika.searchBook", sbook);
                startActivity(SearchResultActivity);

        }

}