package pmm71.dinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 02.12.2017.
 */

public class SearchPage extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView textView;
    ArrayAdapter<String> adapter;
    ImageButton searchButton;
    OursApplication oursApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        oursApp = (OursApplication)this.getApplication();

        textView = findViewById(R.id.etName);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oursApp.recipeNames);
        textView.setAdapter(adapter);

        searchButton = findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(this);


        TextView rt = findViewById(R.id.textView18);
        String srt = "";
        for (String elem : oursApp.ingredietns)
            srt = srt + elem + "\n";
        rt.setText(srt);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SearchResults.class);
        Bundle extras = new Bundle();
        extras.putString("searchStr", textView.getText().toString());
        extras.putStringArrayList("inclIngr", new ArrayList<String>());
        extras.putStringArrayList("exclIngr", new ArrayList<String>());
        extras.putInt("time", 0);
        extras.putInt("kcal", 0);
        extras.putString("category", "");
        intent.putExtra("search", extras);
                startActivity(intent);
    }



}