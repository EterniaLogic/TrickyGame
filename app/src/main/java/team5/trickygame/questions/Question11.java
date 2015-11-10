package team5.trickygame.questions;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question11 extends Question {

    //Text to display lives
    TextView livesText;
    //Text to display timer
    TextView timerText;
    //Timer variable
    Count timer = new Count(30000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question11);

        livesText = (TextView) findViewById(R.id.livesText);
        livesText.setText(GameManager.getInstance().getLivesStr());

        timerText = (TextView) findViewById(R.id.timer);
        timer.start();

    }

    public void isWrong(View v){
        GameManager.getInstance().checkEndGame(Question11.this, livesText);
    }

    public void isRight(View v) {
        timer.cancel();
        GameManager.getInstance().gotoNextQuestion(Question11.this);

    }


    public class Count extends CountDownTimer {

        public Count(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //Displays seconds remaining
        @Override
        public void onTick(long millisUntilFinished) {
            long milsec = millisUntilFinished;
            String text = String.format("00:%02d ",
                    TimeUnit.MILLISECONDS.toSeconds(milsec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milsec)));
            timerText.setText(text);
        }
        //When timer reaches 0, player loses the game
        @Override
        public void onFinish() {
            timerText.setText("00:00 ");
            int x = GameManager.getInstance().getLives();
            while (x != 0){
                GameManager.getInstance().checkEndGame(Question11.this, livesText);
                x = GameManager.getInstance().getLives();
            }
        }
    }
}
