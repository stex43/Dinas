package pmm71.dinas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchPage extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView searchString;
    ArrayAdapter<String> adapter;
    ImageButton searchButton;
    OursApplication oursApp;

    TextView kolKcal, kolTime;
    SeekBar seekBarKcal, seekBarTime;

    AlertDialog alertIn, alertEx;
    Button inclIngr, exclIngr;

    ArrayList <String> inclingrList = new ArrayList<>();
    ArrayList <String> exclingrList = new ArrayList<>();

    ArrayList <TextView> textViewIn = new ArrayList<>();
    ArrayList <TextView> textViewEx = new ArrayList<>();

    LinearLayout inclIngrLinLayout, exclIngrLinLayout;

    TextView isInclIngr, isExclIngr;

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

        seekBarKcal = (SeekBar) findViewById(R.id.seekBarKcal);
        kolKcal = (TextView) findViewById(R.id.KolKcal);
        kolKcal.setText(seekBarKcal.getProgress()+" Ккал");

        seekBarKcal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    kolKcal.setText(String.valueOf(progress)+" Ккал");
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


        isInclIngr = findViewById(R.id.IsInclIngr);
        isInclIngr.setOnClickListener(this);
        isExclIngr = findViewById(R.id.IsExclIngr);

        inclIngr = findViewById(R.id.inclIngr);
        inclIngr.setOnClickListener(this);

        final String[] ingr = oursApp.ingredietns.toArray(new String[oursApp.ingredietns.size()]);
        final boolean[] mCheckedItemsIn = new boolean[ingr.length];
        for (boolean elem : mCheckedItemsIn)
            elem = false;

        final boolean[] mCheckedItemsEx = new boolean[ingr.length];
        for (boolean elem : mCheckedItemsEx)
            elem = false;

        inclIngrLinLayout = findViewById(R.id.linLayoutIncl);
        alertIn = createDialog(ingr, mCheckedItemsIn, "для включения", inclingrList, textViewIn, isInclIngr, inclIngrLinLayout);

        exclIngr = findViewById(R.id.exclIngr);
        exclIngr.setOnClickListener(this);

        exclIngrLinLayout = findViewById(R.id.linLayoutExcl);
        alertEx = createDialog(ingr, mCheckedItemsEx, "для исключения", exclingrList, textViewEx, isExclIngr, exclIngrLinLayout);




    }

    public AlertDialog createDialog (final String[] ingr, final boolean[] mCheckedItems, final String type,
                                     final ArrayList <String> ingrList, final ArrayList <TextView> textView,
                                     final TextView isEmptyList, final LinearLayout IngrLinLayout)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите "+type+":")
                .setCancelable(false)
                .setMultiChoiceItems(ingr, mCheckedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                mCheckedItems[which] = isChecked;
                            }
                        })

                // Добавляем кнопки
                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                StringBuilder state = new StringBuilder();
                                ingrList.clear();
                                String res="";
                                for (int i = 0; i < ingr.length; i++) {
                                    if (mCheckedItems[i]) {
                                        ingrList.add(ingr[i]);
                                        res += ingr[i] + "\n";
                                    }
                                }

                                createTextView(textView, ingrList, IngrLinLayout, isEmptyList, type);

                                if (!ingrList.isEmpty())
                                    state.append("Ингредиенты "+type+":\n"+res);
                                else
                                    state.append("Ингредиенты не выбраны\n");

                                Toast.makeText(getApplicationContext(),
                                        state.toString(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();

                            }
                        });

        return builder.create();
    }





    public void createTextView(ArrayList <TextView> textView, ArrayList <String> ingrList,
                               LinearLayout IngrLinLayout, TextView isEmptyList, String type)
    {
        for (TextView item : textView)
            item.setVisibility(View.GONE);

        if (ingrList.isEmpty())
            isEmptyList.setText("Список ингредиентов "+type+" пуст.");
        else
            isEmptyList.setText("Выбранные ингредиенты "+type+":");
        for (String elem : ingrList) {
            TextView tv = new TextView(this);
            tv.setText(elem);
            // set item content in view
            IngrLinLayout.addView(tv);
            textView.add(tv);
        }
       /* ListAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, inclingrList);

        final int adapterCount = adapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
            View item = adapter.getView(i, null, null);
            inclIngrLinLayout.addView(item);
        }
*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonSearch:
                Intent intent = new Intent(this, SearchResults.class);
                Bundle extras = new Bundle();
                extras.putString("searchStr", searchString.getText().toString());
                extras.putStringArrayList("inclIngr", inclingrList);
                extras.putStringArrayList("exclIngr", exclingrList);
                extras.putInt("time", seekBarTime.getProgress());
                extras.putInt("kcal", seekBarKcal.getProgress());
                extras.putString("category", "");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.inclIngr:
                alertIn.show();
                break;

            case R.id.exclIngr:
                alertEx.show();
                break;

        }
    }



}