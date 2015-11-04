package team5.trickygame.questions;

import android.os.Bundle;
<<<<<<< HEAD
=======
import android.app.Activity;
>>>>>>> master
import android.view.View;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question3 extends Question {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);
    }

    public void dotPress(View v){
        GameManager.getInstance().gotoNextQuestion(this);
    }

    public void notDotPress(View v){
        GameManager.getInstance().checkEndGame(this,  (TextView) findViewById(R.id.livesTxt));
    }
}
