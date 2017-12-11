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

    LinearLayout ll1;
    LinearLayout[] linearLayouts = new LinearLayout[10];
    AlertDialog alert;

    ImageView helloy;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        for (LinearLayout linlayout : linearLayouts) {
            linlayout.setOnClickListener(this);
        }

       /* helloy = findViewById(R.id.searchimage);
        helloy.setOnClickListener(this);*/


/*        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Важное сообщение!")
                .setMessage("Покормите кота!")
                .setIcon(R.drawable.demas)
                .setCancelable(false)
                .setNegativeButton("ОК, иду на кухню",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alert = builder.create();*/

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder
                .setTitle("Об авторах")
                .setIcon(R.drawable.ic_tykva)
                .setView(R.layout.dialog)
                .setPositiveButton("OK", null);
        alert = builder.create();


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        Intent intent2 = new Intent(this, SearchPage.class);
        switch (view.getId())
        {
            case R.id.autors:
                alert.show();
                break;

            case R.id.search:
                startActivity(intent2);
                break;

            case R.id.desert:
                intent.putExtra("name", "Десерты");
                startActivity(intent);
                break;

            case R.id.brekf:
                intent.putExtra("name", "Завтраки");
                startActivity(intent);
                break;

            case R.id.meat:
                intent.putExtra("name", "Мясные блюда");
                startActivity(intent);
                break;

            case R.id.salad:
                intent.putExtra("name", "Салаты");
                startActivity(intent);
                break;

            case R.id.soup:
                intent.putExtra("name", "Супы");
                startActivity(intent);
                break;

            case R.id.drink:
                intent.putExtra("name", "Напитки");
                startActivity(intent);
                break;

            case R.id.seaprod:
                intent.putExtra("name", "Морепродукты");
                startActivity(intent);
                break;

            case R.id.allrecipe:
                intent.putExtra("name", "all");
                startActivity(intent);
                break;



        }



    }

}
