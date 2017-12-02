package pmm71.dinas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by HP on 02.12.2017.
 */

public class RecipeCategories extends AppCompatActivity implements View.OnClickListener {
    LinearLayout searchResults;
    Resources resources;
    ArrayList<LinearLayout> recipesList = new ArrayList<>();
    int numRes;
    //ArrayList<String> recipesNamesList = new ArrayList<>();
    ArrayList<String> recipesRawList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        searchResults = findViewById(R.id.searchResLayout);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        resources = this.getResources();

        addCategories("Салаты");
        addCategories("Закуски");
        addCategories("Мясные блюда");
        addCategories("Супы");
        addCategories("Десерты");
    }

    private void addCategories(String str)
    {
            LinearLayout category = new LinearLayout(this);
            int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                    200);
            category.setOrientation(LinearLayout.HORIZONTAL);
            category.setId(recipesList.size());
            category.setOnClickListener(this);
            recipesList.add(category);
            searchResults.addView(category, lParams);


            TextView text = new TextView(this);
            //recipesNamesList.add(str);
            text.setText(str);

            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            lParams = new LinearLayout.LayoutParams(wrapContent, 75);
            category.addView(text, lParams);
    }

    @Override
    public void onClick(View v) {

    }



}