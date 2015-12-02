package team5.trickygame.questions;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

/**
 * Created by Feyi Oyewole
 */
public class Question5 extends Question{
    TextView livesTxt;
    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        livesTxt = (TextView) findViewById(R.id.livesText);
        livesTxt.setText(GameManager.getInstance().getLivesStr());

    }

    public void isWrong(View v) {
        GameManager.getInstance().checkEndGame(Question5.this, livesTxt);
    }

    public void isRight(View v) {
        GameManager.getInstance().gotoNextQuestion(Question5.this);
    }
}
