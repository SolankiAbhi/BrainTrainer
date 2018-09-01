package com.braintrainer.solanki.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton,playAgainButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    TextView resultTextView,scoreTextView,sumTextView,timerTextView;
    MediaPlayer mediaPlayer;
    Button button0,button1,button2,button3;
    int score = 0;
    int numberOfQuestions = 0;
    ConstraintLayout gameLayout;

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        CountDownTimer start = new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {
                if (l/1000 < 10){
                    timerTextView.setText("0"+String.valueOf(l/1000)+ "s");
                } else {
                    timerTextView.setText(String.valueOf(l/1000)+ "s");
                }
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("DONE! TIME UP");
                resultTextView.animate().scaleXBy(0.20f).scaleYBy(0.20f).alpha(0.75f).setDuration(2500).start();
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer(View view){
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.buttonpush);
        mediaPlayer.start();
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            resultTextView.setText("Correct!");
            score++;
        } else {
            resultTextView.setText("Wrong :(");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void start(View view) {
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.buttonpush);
        mediaPlayer.start();
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    public void newQuestion() {
        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = random.nextInt(4);
        answers.clear();

        for (int i=0;i<4;i++) {
            if (i == locationOfCorrectAnswer){
                answers.add(a+b);
            } else {
                int wrongAnswer = random.nextInt(41);
                while (wrongAnswer == a + b){
                    wrongAnswer = random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTtextView);
        startButton = (Button) findViewById(R.id.startButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        gameLayout = (ConstraintLayout) findViewById(R.id.gameLayout);

        startButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
    }
}
