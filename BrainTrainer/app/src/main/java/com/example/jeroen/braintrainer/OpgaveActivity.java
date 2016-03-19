package com.example.jeroen.braintrainer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class OpgaveActivity extends AppCompatActivity {
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button[] buttons;
    private int correctAntwoord;
    private int aantalCorrect;
    private int aantalBeurten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opgave);

        timerTextView = (TextView) findViewById(R.id.textViewTimer);
        scoreTextView = (TextView) findViewById(R.id.textViewScore);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridAntwoorden);
        buttons = new Button[4];
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = (Button) gridLayout.getChildAt(i);
        }
        startTimer();
        maakSom();
    }

    private void startTimer(){
        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int seconden = (int) millisUntilFinished / 1000;
                timerTextView.setText(String.valueOf(seconden));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("score",aantalCorrect);
                startActivity(intent);
            }
        }.start();
    }

    private void maakSom(){
        Random randomizer = new Random();
        int getal1 = randomizer.nextInt(50) + 1;
        int getal2 = randomizer.nextInt(50) + 1;
        TextView somTextView = (TextView) findViewById(R.id.textViewSom);
        somTextView.setText(String.valueOf(getal1)+" + "+getal2);
        correctAntwoord = getal1+getal2;

        int correcteKnop = randomizer.nextInt(4);
        buttons[correcteKnop].setText(String.valueOf(correctAntwoord));
        for (int i = 0; i < buttons.length; i++){
            if (i != correcteKnop){
                int incorrectAntwoord = randomizer.nextInt(50) + 1;
                while (incorrectAntwoord == correctAntwoord){
                    incorrectAntwoord = randomizer.nextInt(50) + 1;
                }
                buttons[i].setText(String.valueOf(incorrectAntwoord));
            }
        }

    }

    public void checkUitkomst(View view){
        aantalBeurten++;
        Button button = (Button) view;
        int antwoord = Integer.parseInt(button.getText().toString());
        if (antwoord == correctAntwoord){
            aantalCorrect++;
        }
        scoreTextView.setText(String.valueOf(aantalCorrect)+"/"+aantalBeurten);
        maakSom();
    }
}
