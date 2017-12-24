package pmm71.dinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 02.12.2017.
 */

public class SearchPage extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView searchString;
    ArrayAdapter<String> adapter;
    ImageButton searchButton;
    OursApplication oursApp;

    TextView kolKal, kolTime;
    SeekBar seekBarKkal, seekBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        oursApp = (OursApplication)this.getApplication();

        searchString = findViewById(R.id.etName);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oursApp.recipeNames);
        searchString.setAdapter(adapter);

        searchButton = findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(this);

        seekBarKkal = (SeekBar) findViewById(R.id.seekBarKkal);
        kolKal = (TextView) findViewById(R.id.KolKkal);
        kolKal.setText(seekBarKkal.getProgress()+" Ккал");

        seekBarKkal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    kolKal.setText(String.valueOf(progress)+" Ккал");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        seekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
        kolTime = (TextView) findViewById(R.id.KolTime);
        kolTime.setText( seekBarTime.getProgress()+" мин");

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                kolTime.setText(String.valueOf(progress)+" мин");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


/*        TextView rt = findViewById(R.id.textView18);
        String srt = "";
        for (String elem : oursApp.ingredietns)
            srt = srt + elem + "\n";
        rt.setText(srt);*/
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SearchResults.class);
        Bundle extras = new Bundle();
        extras.putString("searchStr", searchString.getText().toString());
        extras.putStringArrayList("inclIngr", new ArrayList<String>());
        extras.putStringArrayList("exclIngr", new ArrayList<String>());
        extras.putInt("time", seekBarTime.getProgress());
        extras.putInt("kcal", seekBarKkal.getProgress());
        extras.putString("category", "");
        intent.putExtra("search", extras);
                startActivity(intent);
    }



}