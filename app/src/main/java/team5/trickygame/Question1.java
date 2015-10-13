package team5.trickygame;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Question1 extends Question {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence
        setContentView(R.layout.activity_question1);


        //Initialize UI Components
        final Button OptA = (Button) findViewById(R.id.RestartBtn);
        final Button OptB = (Button) findViewById(R.id.MainMenuBtn);
        final Button OptC = (Button) findViewById(R.id.OptionsBtn);
        final Button OptD = (Button) findViewById(R.id.optD);

        final TextView livesTxt = (TextView) findViewById(R.id.livesText);

        livesTxt.setText(GameManager.getInstance().getLivesStr());

        OptD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                // Simple and quick "Next Question" solver
                GameManager.getInstance().gotoNextQuestion(Question1.this);
            }
        });

        OptA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
            }
        });

        OptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
            }
        });

        OptC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
            }
        });

    }
}
