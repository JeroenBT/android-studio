package com.example.jeroen.animations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView androidImg1;
    ImageView androidImg2;
    boolean isImg1Showing;
    boolean faded;
    boolean translated;
    boolean rotated;
    long anim_duration;
    SeekBar seekBar;
    RadioButton fadeButton;
    RadioButton translateButton;
    RadioButton rotateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        androidImg1 = (ImageView) findViewById(R.id.androidImg1);
        androidImg2 = (ImageView) findViewById(R.id.androidImg2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        fadeButton = (RadioButton) findViewById(R.id.fadeRadioButton);
        translateButton = (RadioButton) findViewById(R.id.translateRadioButton);
        rotateButton = (RadioButton) findViewById(R.id.rotateRadioButton);
        faded = true;
        translated = false;
        rotated = false;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                anim_duration = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fadeButton.setChecked(true);
        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    faded = true;
                    translated = false;
                    rotated = false;

                if (isImg1Showing){
                    androidImg2.setAlpha(0f);
                    androidImg2.setTranslationX(0f);
                    androidImg2.setScaleX(1f);
                    androidImg2.setScaleY(1f);
                } else {
                    androidImg1.setAlpha(0f);
                    androidImg1.setTranslationX(0f);
                    androidImg1.setScaleX(1f);
                    androidImg1.setScaleY(1f);
                }
            }
        });

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    faded = false;
                    translated = true;
                    rotated = false;

                if (isImg1Showing){
                    androidImg2.setAlpha(1f);
                    androidImg2.setTranslationX(-1000f);
                    androidImg2.setScaleX(1f);
                    androidImg2.setScaleY(1f);
                } else {
                    androidImg1.setAlpha(1f);
                    androidImg1.setTranslationX(1000f);
                    androidImg1.setScaleX(1f);
                    androidImg1.setScaleY(1f);
                }
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    faded = false;
                    translated = false;
                    rotated = true;

                if (isImg1Showing){
                    androidImg2.setAlpha(1f);
                    androidImg2.setTranslationX(0f);
                    androidImg2.setScaleX(0f);
                    androidImg2.setScaleY(0f);
                } else {
                    androidImg1.setAlpha(1f);
                    androidImg1.setTranslationX(0f);
                    androidImg1.setScaleX(0f);
                    androidImg1.setScaleY(0f);
                }
            }
        });

        isImg1Showing = true;
        anim_duration = 2500l;
        seekBar.setProgress((int)anim_duration);
        androidImg2.setAlpha(0f);
//        androidImg2.animate().translationX(-1000f).setDuration(0l);
//        androidImg2.animate().scaleX(0f).scaleY(0f).setDuration(0l);
    }

    public void animate(View view) {
        if (faded) {
            fade();
        }
        if (translated) {
            translate();
        }
        if (rotated) {
            rotate();
        }
        isImg1Showing = !isImg1Showing;
    }


    private void fade(){
        if (isImg1Showing){
            androidImg1.animate().alpha(0f).setDuration(anim_duration);
            androidImg2.animate().alpha(1f).setDuration(anim_duration);
        }else{
            androidImg1.animate().alpha(1f).setDuration(anim_duration);
            androidImg2.animate().alpha(0f).setDuration(anim_duration);
        }
    }

    private void translate(){
        if (isImg1Showing){
            androidImg1.animate().translationX(1000f).setDuration(anim_duration);
            androidImg2.animate().translationX(0f).setDuration(anim_duration);
        }else{
            androidImg1.animate().translationX(0f).setDuration(anim_duration);;
            androidImg2.animate().translationX(1000f).setDuration(anim_duration);
        }
    }

    private void rotate(){
        if (isImg1Showing){
            androidImg1.animate().rotation(360*2f).scaleX(0f).scaleY(0f).setDuration(anim_duration);
            androidImg2.animate().rotation(-360*2f).scaleX(1f).scaleY(1f).setDuration(anim_duration);
        }else{
            androidImg1.animate().rotation(-360*2f).scaleX(1f).scaleY(1f).setDuration(anim_duration);
            androidImg2.animate().rotation(360*2f).scaleX(0f).scaleY(0f).setDuration(anim_duration);
        }
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

