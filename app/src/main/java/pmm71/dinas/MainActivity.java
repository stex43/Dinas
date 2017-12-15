package pmm71.dinas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout[] linearLayouts = new LinearLayout[12];
    AlertDialog alert;
    OursApplication oursApp;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oursApp = (OursApplication)this.getApplication();

        oursApp.ReadFiles();

        linearLayouts[0] = findViewById(R.id.search);

        linearLayouts[1] = findViewById(R.id.autors);

        linearLayouts[2] = findViewById(R.id.desert);

        linearLayouts[3] = findViewById(R.id.brekf);

        linearLayouts[4] = findViewById(R.id.meat);

        linearLayouts[5] = findViewById(R.id.salad);

        linearLayouts[6] = findViewById(R.id.soup);

        linearLayouts[7] = findViewById(R.id.drink);

        linearLayouts[8] = findViewById(R.id.seaprod);

        linearLayouts[9] = findViewById(R.id.allrecipe);

        linearLayouts[10] = findViewById(R.id.garnish);

        linearLayouts[11] = findViewById(R.id.bake);


        for (LinearLayout linlayout : linearLayouts) {
            linlayout.setOnClickListener(this);
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder
                    .setTitle("Об авторах")
                    .setIcon(R.drawable.ic_tykva)
                    .setView(R.layout.dialog)
                    .setPositiveButton("OK", null);
        }
        else {
            builder
                    .setTitle("Об авторах")
                    .setMessage(R.string.informaAboutAuthors)
                    .setIcon(R.drawable.ic_tykva)
                    .setPositiveButton("OK", null);
        }
        alert = builder.create();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        Intent intent2 = new Intent(this, SearchPage.class);
        Bundle extras = new Bundle();
        extras.putString("searchStr", "");
        extras.putStringArrayList("inclIngr", new ArrayList<String>());
        extras.putStringArrayList("exclIngr", new ArrayList<String>());
        switch (view.getId())
        {
            case R.id.autors:
                alert.show();
                break;

            case R.id.search:
                startActivity(intent2);
                break;

            case R.id.desert:
                extras.putString("category", "Десерты");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.brekf:
                extras.putString("category", "Завтраки");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.meat:
                extras.putString("category", "Мясные блюда");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.salad:
                extras.putString("category", "Салаты");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.soup:
                extras.putString("category", "Супы");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.drink:
                extras.putString("category", "Напитки");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.seaprod:
                extras.putString("category", "Морепродукты");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.allrecipe:
                extras.putString("category", "");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.garnish:
                extras.putString("category", "Гарниры");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;

            case R.id.bake:
                extras.putString("category", "Выпечка");
                intent.putExtra("search", extras);
                startActivity(intent);
                break;
        }
    }
}
