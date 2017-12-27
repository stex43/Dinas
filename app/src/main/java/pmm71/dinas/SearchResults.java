package pmm71.dinas;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {
    LinearLayout searchResults;
    ArrayList<LinearLayout> recipesList = new ArrayList<>();
    ArrayList<OursApplication.keysToRecipe> searchRes;
    OursApplication oursApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        searchResults = findViewById(R.id.searchResLayout);

        oursApp = (OursApplication) this.getApplication();

        Intent intent = getIntent();
        Bundle extras = intent.getBundleExtra("search");
        String category = extras.getString("category");
        String searchStr = extras.getString("searchStr");
        ArrayList<String> inclIngr = extras.getStringArrayList("inclIngr");
        ArrayList<String> exclIngr = extras.getStringArrayList("exclIngr");
        int time = extras.getInt("time");
        int kcal = extras.getInt("kcal");
        
        searchRes = oursApp.searchRecipes(category,
                searchStr, inclIngr, exclIngr, kcal, time);

        isResultOfSearch();

        for (OursApplication.keysToRecipe keys : searchRes)
            addRecipe(keys);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("category", searchRes.get(v.getId()).getCategory());
        intent.putExtra("recipeName", searchRes.get(v.getId()).getRecipeName());
        startActivity(intent);
    }

    private void addRecipe(OursApplication.keysToRecipe recipe) {
            // добавление layout для рецепта
            LinearLayout reclinearLayout = new LinearLayout(this);
            int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = oursApp.resources.getInteger(R.integer.searchResHeightLayout);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(matchParent,
                    oursApp.pxFromDp(height));
            reclinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            reclinearLayout.setId(recipesList.size());
            reclinearLayout.setOnClickListener(this);
            reclinearLayout.setBackgroundResource(R.drawable.recipe_border);
            reclinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            recipesList.add(reclinearLayout);
            searchResults.addView(reclinearLayout, lParams);

            // добавление картинки рецепта
            height = oursApp.resources.getInteger(R.integer.searchResSizeImage);
            String str = oursApp.database.get(recipe.getCategory()).get(recipe.getRecipeName()).
                    getImage(0);
            int pictId = this.getResources().getIdentifier(str,
                    "drawable", this.getPackageName());
            ImageView image = new ImageView(this);
            image.setImageResource(pictId);
            lParams = new LinearLayout.LayoutParams(oursApp.pxFromDp(height),
                    oursApp.pxFromDp(height));
            int margin = oursApp.resources.getInteger(R.integer.searchResImageMargin);
            lParams.setMargins(oursApp.pxFromDp(margin), 0,0,0);
            reclinearLayout.addView(image, lParams);

            //  добавление название рецепта
            TextView text = new TextView(this);
            text.setText(recipe.getRecipeName());
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

            reclinearLayout.addView(text, lParams);
    }

    private void isResultOfSearch()
    {
        TextView isRes = findViewById(R.id.isResTextView);
        LinearLayout isResLinLayout = findViewById(R.id.isResLinLayout);


        if(searchRes.isEmpty()){
            isRes.setText("По запросу ничего не найдено.");
            isRes.setGravity(Gravity.CENTER_HORIZONTAL);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int height = size.y;
            isResLinLayout.setPadding(0,height/4, 0, 0);
            ImageView noRes = new ImageView(this);
            noRes.setImageResource(R.drawable.noresult);
            noRes.setScaleType(ImageView.ScaleType.CENTER);
            isResLinLayout.addView(noRes);
        }

        else
            isRes.setText("Найденные результаты:");
        isRes.setHeight(oursApp.pxFromDp(oursApp.pxFromDp(20)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isRes.setTextAppearance(R.style.isRes);
        }
        else {
            isRes.setTextColor(getResources().getColor(R.color.isResColor));
            isRes.setTextSize(COMPLEX_UNIT_SP, 18);
        }
    }
}