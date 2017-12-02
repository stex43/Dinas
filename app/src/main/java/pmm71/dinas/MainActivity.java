package pmm71.dinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    Button button2;
    EditText stringSearch;
    ImageButton search;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        textView = (TextView)findViewById(R.id.textView);
        stringSearch = (EditText)findViewById(R.id.etName);



        search = findViewById(R.id.imageButton);
        search.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        switch (view.getId())
        {
            case R.id.button:
                intent.putExtra("name", "all");
                break;

            case R.id.imageButton:
                intent.putExtra("name",stringSearch.getText().toString());
                break;

            case R.id.button2:
                intent = new Intent(this, RecipeCategories.class);
                intent.putExtra("name",stringSearch.getText().toString());
                break;
        }
        startActivity(intent);
    }
}
