package pmm71.dinas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {
    LinearLayout searchResults;
    Resources resources;
    ArrayList<LinearLayout> recipesList = new ArrayList<>();
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

        if (Objects.equals(name, "all")) {
            addRecipe("rec1");
            //addRecipe("rec2");
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

            LinearLayout recipe = new LinearLayout(this);
            int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                    120);
            recipe.setOrientation(LinearLayout.HORIZONTAL);
            recipe.setId(recipesList.size());
            recipe.setOnClickListener(this);
            recipesList.add(recipe);
            searchResults.addView(recipe, lParams);


            TextView text = new TextView(this);
            //recipesNamesList.add(str);
            text.setText(str);

            str = br.readLine();
            int pictId = this.getResources().getIdentifier(str,
                    "drawable", this.getPackageName());
            ImageView image = new ImageView(this);
            image.setImageResource(pictId);
            lParams = new LinearLayout.LayoutParams(60, 60);
            recipe.addView(image, lParams);

            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            lParams = new LinearLayout.LayoutParams(wrapContent, 75);
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
}
