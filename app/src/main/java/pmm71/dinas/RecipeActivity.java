package pmm71.dinas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class RecipeActivity extends AppCompatActivity {
    OursApplication oursApp;
    LinearLayout recipeLayout;
    Recipe recipe;
    LinearLayout.LayoutParams lParamsHeader;
    LinearLayout.LayoutParams lParamsText;
    LinearLayout.LayoutParams lParamsImage;
    TextView textView;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String recipeName = intent.getStringExtra("recipeName");

        // получаем рецепт из "базы данных"
        oursApp = (OursApplication) this.getApplication();
        recipe = oursApp.database.get(category).get(recipeName);

        //region Задание параметров
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        lParamsHeader = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        lParamsText = new LinearLayout.LayoutParams(wrapContent, wrapContent);
        lParamsImage = new LinearLayout.LayoutParams(matchParent, (int) (height / 2.2));

        int margin = oursApp.resources.getInteger(R.integer.headerMargin);
        lParamsHeader.setMargins(oursApp.pxFromDp(margin), 0, 0, 0);

        margin = oursApp.resources.getInteger(R.integer.textMargin);
        lParamsText.setMargins(oursApp.pxFromDp(margin), 0, 0, 0);
        //endregion

        recipeLayout = findViewById(R.id.recipeLayout);

        //заголовок - название рецепта
        addHeader(recipeName);

        //главное изображение рецепта
        //String str = oursApp.database.get("Салаты").get("Hello").getImage(1);
        String str = oursApp.database.get(category).get(recipeName).getImage(1);
        addImage(str);

        //вмонтируем сюда категорию блюда, чтобы потом разбить рецепты по подгруппам в соответствии с этими категориями
        //я не знаю, нужно ли ее печатать
        //str = br.readLine();
            /*textView = new TextView(this);
            textView.setText("Категория: ".concat(str));
            recipeLayout.addView(textView, lParams);*/

        // время и ккал
        addHeader("Время");
        addText(String.format("%d мин", recipe.getTime()));

        addHeader("ккал");
        addText(String.format("%d ккал", recipe.getKcal()));

        //ингредиенты
        addHeader("Ингредиенты");

        StringBuilder helpStr = new StringBuilder();
        textView = new TextView(this);
        for (int i = 0; i < recipe.getAmountIngredients(); i++)
            helpStr.append(recipe.getIngredient(i)).append("\n");
        addText(helpStr.toString());

        //рецепт
        for (int i = 0; i < recipe.getAmountSteps(); i++) {
            addHeader(String.format("Шаг %d", i + 1));
            addText(recipe.getStep(i));

            str = recipe.getImage(i + 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(str, "")) {
                    addImage(str);
                }
            }
        }
    }

    void addHeader(String text) {
        textView = new TextView(this);
        textView.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.header);
        } else {
            textView.setTextColor(getResources().getColor(R.color.headerColor));
            textView.setTextSize(COMPLEX_UNIT_SP, 20);
        }
        recipeLayout.addView(textView, lParamsHeader);
    }

    void addText(String text) {
        textView = new TextView(this);
        textView.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.text);
        } else {
            textView.setTextColor(getResources().getColor(R.color.textColor));
            textView.setTextSize(COMPLEX_UNIT_SP, 16);
        }
        recipeLayout.addView(textView, lParamsText);
    }

    void addImage(String image) {
        int pictId = this.getResources().getIdentifier(image, "drawable",
                this.getPackageName());
        imageView = new ImageView(this);
        imageView.setImageResource(pictId);
        recipeLayout.addView(imageView, lParamsImage);
    }
}



