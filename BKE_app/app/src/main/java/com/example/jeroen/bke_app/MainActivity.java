package com.example.jeroen.bke_app;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int spelerActief;
    int[] spelVerloop = {0,0,0,0,0,0,0,0,0};
    int[][] winnaars = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    int spelerGewonnen;
    boolean spelAfgelopen;
    TextView eindeSpelTextView;
    Button nieuwSpelButton;
    LinearLayout eindeSpelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spelerActief = 1;
        spelerGewonnen = 0;
        eindeSpelTextView = (TextView) findViewById(R.id.eindeSpelTextView);
        nieuwSpelButton = (Button) findViewById(R.id.nieuwSpelButton);
        eindeSpelLayout = (LinearLayout) findViewById(R.id.eindeSpelLayout);
        eindeSpelLayout.setVisibility(View.GONE);
    }

    public void setImg(View view){
        ImageView imageView = (ImageView) view;
        int index = Integer.parseInt(imageView.getTag().toString());

        if(spelVerloop[index] == 0){
            spelVerloop[index] = spelerActief;
            if(spelerActief == 1){
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.kruisje);
                imageView.animate().translationY(0f).setDuration(300l);
                checkEindeSpel();
                spelerActief = 2;
            }else{
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.rondje);
                imageView.animate().translationY(0f).setDuration(300l);
                checkEindeSpel();
                spelerActief = 1;
            }
            if (spelAfgelopen){
                eindigSpel();
            }
        }
    }

    private void checkEindeSpel(){
        for(int[] winnaar:winnaars){
            boolean isWinnaar = true;
            for(int i:winnaar){
                if(spelVerloop[i] != spelerActief){
                    isWinnaar = false;
                    break;
                }
            }
            if (isWinnaar){
                spelerGewonnen = spelerActief;
                spelAfgelopen = true;
            }
        }
        //gelijkspel situatie
        if (spelerGewonnen == 0){
            spelAfgelopen = true;
            for(int i:spelVerloop){
                if(i == 0){
                    spelAfgelopen = false;
                    break;
                }
            }
        }
    }

    private void eindigSpel(){
        eindeSpelLayout.setVisibility(View.VISIBLE);
        switch(spelerGewonnen){
            case 0:
                eindeSpelTextView.setText("Gelijk spel, opnieuw spelen?");
                break;
            case 1:
                eindeSpelTextView.setText("Speler 1 heeft gewonnen, opnieuw spelen?");
                break;
            case 2:
                eindeSpelTextView.setText("Speler 2 heeft gewonnen, opnieuw spelen?");
                break;
        }
    }

    public void nieuwSpel(View view){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < spelVerloop.length; i++){
            spelVerloop[i] = 0;
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(0);
        }
        spelerActief = 1;
        spelAfgelopen = false;
        spelerGewonnen = 0;
        eindeSpelLayout.setVisibility(View.GONE);
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
