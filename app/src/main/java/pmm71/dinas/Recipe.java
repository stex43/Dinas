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
        recipeLayout = findViewById(R.id.recipeLayout);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);

        Resources resources = this.getResources();
        int recId = this.getResources().getIdentifier(rec, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String str;

        try {
            //заголовок - название рецепта
            str = br.readLine();
            TextView text = new TextView(this);
            text.setText(str);
            recipeLayout.addView(text, lParams);

            //главное изображение рецепта
            str = br.readLine();
            int pictId = this.getResources().getIdentifier(str,
                    "drawable", this.getPackageName());
            ImageView image = new ImageView(this);
            image.setImageResource(pictId);
            recipeLayout.addView(image, lParams);

            //ингридиенты
            StringBuilder helpStr = new StringBuilder("Ингридиенты\n");
            text = new TextView(this);
            while (!Objects.equals(str = br.readLine(), ""))
                helpStr.append(str).append("\n");
            text.setText(helpStr);
            recipeLayout.addView(text, lParams);

            //рецепт
            int i = 0;
            while ((str = br.readLine()) != null) {
                text = new TextView(this);
                text.setText(String.format("Шаг %d\n", i + 1) + str);
                recipeLayout.addView(text, lParams);

                str = br.readLine();
                if (!Objects.equals(str, "")) {
                    pictId = this.getResources().getIdentifier(str,
                            "drawable", this.getPackageName());
                    image = new ImageView(this);
                    image.setImageResource(pictId);
                    recipeLayout.addView(image, lParams);
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
