package team5.trickygame.questions;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question3 extends Question {
    TextView livesText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);
        livesText = (TextView) findViewById(R.id.livesTxt);
        livesText.setText(GameManager.getInstance().getLivesStr());
    }

    public void dotPress(View v){
        GameManager.getInstance().gotoNextQuestion(this);
    }

    public void notDotPress(View v){
        GameManager.getInstance().checkEndGame(this,  (TextView) findViewById(R.id.livesTxt));
    }
}
