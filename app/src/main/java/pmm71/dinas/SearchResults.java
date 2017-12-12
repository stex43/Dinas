package pmm71.dinas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {
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

        numRes = resources.getInteger(R.integer.numRecipes);

       if (Objects.equals(name, "all")) {

            for (int i = 0; i < numRes; i++) {
                addRecipe("rec".concat(String.valueOf(i+1)));
            }
        }

        String a=name.substring(0,1);
        if (a.equals("1")){

            for (int i = 0; i < numRes; i++) {
                searchRecipeName("rec".concat(String.valueOf(i+1)), name.substring(1));
            }
        }

        else {

           for (int i = 0; i < numRes; i++) {
               searchRecipeCategory("rec".concat(String.valueOf(i+1)), name);
           }
        }
    }

    private void addRecipe(String rawName) {
        int recId = resources.getIdentifier(rawName, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str;
        try {
            recipesRawList.add(rawName);
            str = br.readLine();

            // добавление layout для рецепта
            LinearLayout recipe = new LinearLayout(this);
            int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = resources.getInteger(R.integer.searchResHeightLayout);
            float density = getApplicationContext().getResources().getDisplayMetrics().density;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                    GlobalThings.pxFromDp(height, density));
            recipe.setOrientation(LinearLayout.HORIZONTAL);
            recipe.setId(recipesList.size());
            recipe.setOnClickListener(this);
            recipe.setBackgroundResource(R.drawable.recipe_border);
            recipe.setGravity(Gravity.CENTER_VERTICAL);
            recipesList.add(recipe);
            searchResults.addView(recipe, lParams);

            // считывание названия рецепта
            TextView text = new TextView(this);
            //recipesNamesList.add(str);
            text.setText(str);

            // добавление картинки рецепта
            height = resources.getInteger(R.integer.searchResSizeImage);
            str = br.readLine();
            int pictId = this.getResources().getIdentifier(str,
                    "drawable", this.getPackageName());
            ImageView image = new ImageView(this);
            image.setImageResource(pictId);
            lParams = new LinearLayout.LayoutParams(GlobalThings.pxFromDp(height, density),
                    GlobalThings.pxFromDp(height, density));
            int margin = resources.getInteger(R.integer.searchResImageMargin);
            lParams.setMargins(GlobalThings.pxFromDp(margin, density), 0,0,0);
            recipe.addView(image, lParams);

            //  добавление название рецепта
            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            lParams = new LinearLayout.LayoutParams(matchParent, wrapContent);
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                text.setTextAppearance(R.style.header);
            }
            else {
                text.setTextColor(getResources().getColor(R.color.headerColor));
                text.setTextSize(COMPLEX_UNIT_SP, 20);
            }
            recipe.addView(text, lParams);

            in.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Recipe.class);
        intent.putExtra("recipeFile", recipesRawList.get(v.getId()));
        Toast.makeText(this, recipesRawList.get(v.getId()), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }



    private void searchRecipeName(String nameFile, String nameRecipe) {
        int recId = resources.getIdentifier(nameFile, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str = "";
            try {
                str = br.readLine();
                in.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            str = str.toLowerCase();

        str = str.toLowerCase();
        nameRecipe = nameRecipe.toLowerCase();
        if (str.contains(nameRecipe))
            addRecipe(nameFile);
    }






    private void searchRecipeCategory(String nameFile, String nameCat) {
        int recId = resources.getIdentifier(nameFile, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str = "";
        try {
            for (int i = 0; i < 4; i++)
                str = br.readLine();
            in.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        str = str.toLowerCase();
        nameCat = nameCat.toLowerCase();
        if (nameCat.equals(str))
            addRecipe(nameFile);
    }
}