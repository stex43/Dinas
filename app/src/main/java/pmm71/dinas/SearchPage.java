package pmm71.dinas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by HP on 02.12.2017.
 */

public class SearchPage extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView textView;
    ArrayAdapter<String> adapter;
    ImageButton searchButton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        textView = (AutoCompleteTextView) findViewById(R.id.etName);
        String[] countries = getResources().getStringArray(R.array.example_array);
// Здесь нужно как-то получить список названий всех рецептов, пока тут могут выводиться только строковые константы из файла ресурсов
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        searchButton = findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra("category", "");
        intent.putExtra("searchStr", textView.getText().toString());
                startActivity(intent);
    }



}