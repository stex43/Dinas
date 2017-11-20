package pmm71.dinas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Recipe extends AppCompatActivity {
    LinearLayout mainLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Intent intent = getIntent();
        String rec = intent.getStringExtra("recipeFile");

        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        mainLayout = (LinearLayout) findViewById(R.id.recipeLayout);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);

        Resources resources = this.getResources();
        int recId = this.getResources().getIdentifier(rec, "raw", this.getPackageName());
        InputStream in = resources.openRawResource(recId);
        //InputStream in = resources.openRawResource(R.raw.rec1);
        String inputText = inputStreamToString(in);
        String[] splittedText = inputText.split("\n");

        for (int i = 0; i < splittedText.length; i++)
        {
            TextView text = new TextView(this);
            text.setText(String.format("Шаг %d\n", i + 1) + splittedText[i]);
            mainLayout.addView(text, lParams);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String inputStreamToString(InputStream stream){
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
