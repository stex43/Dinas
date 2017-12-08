package pmm71.dinas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class Recipe extends AppCompatActivity {
    LinearLayout recipeLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Intent intent = getIntent();
        String rec = intent.getStringExtra("recipeFile");

        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        int wrap = LinearLayout.LayoutParams.MATCH_PARENT;
        recipeLayout = findViewById(R.id.recipeLayout);
        LinearLayout.LayoutParams lParamsHeader = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        LinearLayout.LayoutParams lParamsText = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);

        Resources resources = this.getResources();
        int recId = this.getResources().getIdentifier(rec, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str;

        try {
            int margin = resources.getInteger(R.integer.headerMargin);
            float density = getApplicationContext().getResources().getDisplayMetrics().density;
            lParamsHeader.setMargins(GlobalThings.pxFromDp(margin, density), 0, 0, 0);
            margin = resources.getInteger(R.integer.textMargin);
            lParamsText.setMargins(GlobalThings.pxFromDp(margin, density), 0, 0, 0);
            //заголовок - название рецепта
            str = br.readLine();
            TextView text = new TextView(this);
            text.setText(str);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                text.setTextAppearance(R.style.header);
            }
            else {
                text.setTextColor(getResources().getColor(R.color.headerColor));
                text.setTextSize(COMPLEX_UNIT_SP, 20);
            }
            recipeLayout.addView(text, lParamsHeader);

            str = br.readLine();
            //главное изображение рецепта
            int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
            str = br.readLine();
            int pictId = this.getResources().getIdentifier(str,
                    "drawable", this.getPackageName());
            ImageView image = new ImageView(this);
            image.setImageResource(pictId);
            LinearLayout.LayoutParams lParamsImage = new LinearLayout.LayoutParams(
                    wrap, (int) (height / 2.2));
            recipeLayout.addView(image, lParamsImage);

            //вмонтируем сюда категорию блюда, чтобы потом разбить рецепты по подгруппам в соответствии с этими категориями
            //я не знаю, нужно ли ее печатать
            str = br.readLine();
            /*text = new TextView(this);
            text.setText("Категория: ".concat(str));
            recipeLayout.addView(text, lParams);*/

            // время и ккал
            str = br.readLine();
            text = new TextView(this);
            text.setText(String.format("Время: %s", str));
            recipeLayout.addView(text, lParamsText);

            str = br.readLine();
            text = new TextView(this);
            text.setText(String.format("ккал: %s", str));
            recipeLayout.addView(text, lParamsText);

            //ингредиенты
            text = new TextView(this);
            text.setText("Ингредиенты");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                text.setTextAppearance(R.style.header);
            }
            else {
                text.setTextColor(getResources().getColor(R.color.headerColor));
                text.setTextSize(COMPLEX_UNIT_SP, 20);
            }
            recipeLayout.addView(text, lParamsHeader);

            StringBuilder helpStr = new StringBuilder();
            text = new TextView(this);
            while (!Objects.equals(str = br.readLine(), ""))
                helpStr.append(str).append("\n");
            text.setText(helpStr);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                text.setTextAppearance(R.style.text);
            }
            else {
                text.setTextColor(getResources().getColor(R.color.textColor));
                text.setTextSize(COMPLEX_UNIT_SP, 16);
            }
            recipeLayout.addView(text, lParamsText);

            //рецепт
            int i = 0;
            while ((str = br.readLine()) != null) {
                text = new TextView(this);
                text.setText(String.format("Шаг %d", i + 1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text.setTextAppearance(R.style.header);
                }
                else {
                    text.setTextColor(getResources().getColor(R.color.headerColor));
                    text.setTextSize(COMPLEX_UNIT_SP, 20);
                }
                recipeLayout.addView(text, lParamsHeader);

                text = new TextView(this);
                text.setText(str);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text.setTextAppearance(R.style.text);
                }
                else {
                    text.setTextColor(getResources().getColor(R.color.textColor));
                    text.setTextSize(COMPLEX_UNIT_SP, 16);
                }
                recipeLayout.addView(text, lParamsText);

                str = br.readLine();
                if (!Objects.equals(str, "")) {
                    pictId = this.getResources().getIdentifier(str,
                            "drawable", this.getPackageName());
                    image = new ImageView(this);
                    image.setImageResource(pictId);
                    recipeLayout.addView(image, lParamsImage);
                }
                i++;
            }
            in.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


