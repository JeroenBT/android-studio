package com.example.jeroen.valutacurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView resultText = (TextView) findViewById(R.id.textView);
        final EditText inputText = (EditText) findViewById(R.id.editText);
        Button euroToDollar = (Button) findViewById(R.id.button_euroToDollar);
        Button dollarToEuro = (Button) findViewById(R.id.button_dollarToEuro);
        euroToDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double euro = Double.parseDouble(inputText.getText().toString());

                double result = euro * 1.12;
                DecimalFormat REAL_FORMATTER = new DecimalFormat("$ 0.##");
                resultText.setText(REAL_FORMATTER.format(result));
            }
        });

        dollarToEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double dollar = Double.parseDouble(inputText.getText().toString());

                double result = dollar * 0.89;
                DecimalFormat REAL_FORMATTER = new DecimalFormat("€ 0.##");
                resultText.setText(REAL_FORMATTER.format(result));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
