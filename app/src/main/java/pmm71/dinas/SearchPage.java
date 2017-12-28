package pmm71.dinas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


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

    Spinner spinnerCategories;

    String[] categories = new String[]{ "Все блюда", "Салаты", "Десерты", "Завтраки", "Мясные блюда",
            "Супы", "Напитки", "Морепродукты", "Гарниры", "Выпечка" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        oursApp = (OursApplication) this.getApplication();

        searchString = (AutoCompleteTextView) findViewById(R.id.etName);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oursApp.recipeNames);
        searchString.setAdapter(adapter);

        searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(this);

        seekBarKcal = (SeekBar) findViewById(R.id.seekBarKcal);
        kolKcal = (TextView) findViewById(R.id.KolKcal);
        kolKcal.setText(seekBarKcal.getProgress() + " Ккал");


        seekBarKcal.setOnSeekBarChangeListener(createSeekbar(" Ккал"));

        seekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
        kolTime = (TextView) findViewById(R.id.KolTime);
        kolTime.setText(seekBarTime.getProgress() + " мин");

        seekBarTime.setOnSeekBarChangeListener(createSeekbar(" мин"));


        ArrayList<TextView> textViewIn = new ArrayList<>();
        ArrayList<TextView> textViewEx = new ArrayList<>();

        LinearLayout inclIngrLinLayout, exclIngrLinLayout;

        TextView isInclIngr, isExclIngr;

        isInclIngr = (TextView) findViewById(R.id.IsInclIngr);
        isExclIngr = (TextView) findViewById(R.id.IsExclIngr);

        inclIngr = (Button) findViewById(R.id.inclIngr);
        inclIngr.setOnClickListener(this);

        exclIngr = (Button) findViewById(R.id.exclIngr);
        exclIngr.setOnClickListener(this);

        inclIngrLinLayout = (LinearLayout) findViewById(R.id.linLayoutIncl);
        exclIngrLinLayout = (LinearLayout) findViewById(R.id.linLayoutExcl);

        final String[] ingr = oursApp.ingredietns.toArray(new String[oursApp.ingredietns.size()]);
        final boolean[] mCheckedItemsIn = new boolean[ingr.length];
        for (boolean elem : mCheckedItemsIn)
            elem = false;

        final boolean[] mCheckedItemsEx = new boolean[ingr.length];
        for (boolean elem : mCheckedItemsEx)
            elem = false;

        alertIn = createDialog(ingr, mCheckedItemsIn, "для включения", inclingrList, textViewIn, isInclIngr, inclIngrLinLayout);
        alertEx = createDialog(ingr, mCheckedItemsEx, "для исключения", exclingrList, textViewEx, isExclIngr, exclIngrLinLayout);


        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);

        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.row, categories);


        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setSelection(0, false);

        spinnerCategories.setOnItemSelectedListener(createSpinnerAdapter());
    }



    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);

        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.selectedCategory);
            label.setText(categories[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            switch (categories[position])
            {
                case "Все блюда":
                    icon.setImageResource(R.drawable.all2);
                    break;
                case "Салаты":
                    icon.setImageResource(R.drawable.salad);
                    break;
                case "Десерты":
                    icon.setImageResource(R.drawable.desert);
                    break;
                case "Завтраки":
                    icon.setImageResource(R.drawable.breakfasts);
                    break;
                case "Мясные блюда":
                    icon.setImageResource(R.drawable.meat);
                    break;
                case "Супы":
                    icon.setImageResource(R.drawable.soups);
                    break;
                case "Напитки":
                    icon.setImageResource(R.drawable.drinking);
                    break;
                case "Морепродукты":
                    icon.setImageResource(R.drawable.sea);
                    break;
                case "Гарниры":
                    icon.setImageResource(R.drawable.garnish);
                    break;
                case "Выпечка":
                    icon.setImageResource(R.drawable.bake);
                    break;

            }
            return row;
        }
    }


    private AdapterView.OnItemSelectedListener createSpinnerAdapter()
    {
        AdapterView.OnItemSelectedListener spinerAdapter= new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Выбранная категория: " + categories[pos], Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        };
        return spinerAdapter;
    }


    private SeekBar.OnSeekBarChangeListener createSeekbar(final String type)
    {
        SeekBar.OnSeekBarChangeListener seekBar = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                kolKcal.setText(String.valueOf(progress)+type);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        return seekBar;
    }


    private AlertDialog createDialog(final String[] ingr, final boolean[] mCheckedItems, final String type,
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





    private void createTextView(ArrayList <TextView> textView, ArrayList <String> ingrList,
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
                int pos = spinnerCategories.getSelectedItemPosition();
                if (pos==0)
                    extras.putString("category", "");
                else
                    extras.putString("category", categories[pos]);
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