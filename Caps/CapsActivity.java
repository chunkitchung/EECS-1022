// Chun-Kit Chung
// Done with partner Mohammad Shaikh
// https://youtu.be/69HO1Zh9-gQ

package com.example.acer.caps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CapsActivity extends AppCompatActivity {
    private Game game;
    private String question;
    private String answer;
    private int score;
    private int qNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caps_layout);

        this.game = new Game();
        game.qa();
        this.score = 0;
        this.qNumber = 1;
        this.question = (findViewById(R.id.question)).toString();
        this.answer =  (findViewById(R.id.answer)).toString();
        ask();
    }

    private void ask() {
        Game game = new Game();
        String string = game.qa();
        this.question = (string.substring(0,string.lastIndexOf("\n")));
        this.answer = string.substring(string.lastIndexOf("\n") + 1);

        ((TextView) findViewById(R.id.score)).setText("SCORE: " + score);
        ((TextView) findViewById(R.id.qNum)).setText("Q#" + qNumber);
        ((TextView) findViewById(R.id.question)).setText(question);
    }

    public void onDone(View v){
        String userAnswer = ((EditText)findViewById(R.id.answer)).getText().toString().toUpperCase();

        if(qNumber < 10){
            String Question = ((TextView)findViewById(R.id.qNum)).getText().toString() + ":" + question +"\n";
            userAnswer = userAnswer.toUpperCase();
            String correctAnswer = "Correct answer:" + answer;
            String old = ((TextView) findViewById(R.id.log)).getText().toString();

            String runningLog = Question + '\n' +"Your answer:" +  userAnswer + "\n" + correctAnswer;
            ((TextView) findViewById(R.id.log)).setText(runningLog + "\n\n" +  old);

            if (userAnswer.equals(answer.toUpperCase())){
                score++;
            }
            ((EditText)findViewById(R.id.answer)).setText("");
            qNumber++;
            ask();
        }

        if (qNumber == 10){
            ((TextView) findViewById(R.id.qNum)).setText("Game Over!");
            ((Button) findViewById(R.id.done)).setEnabled(false);
        }
    }
}