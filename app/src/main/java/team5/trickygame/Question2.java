package team5.trickygame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Question2 extends Question {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);


        //Initialize UI Components
        final Button OptA = (Button) findViewById(R.id.RestartBtn);
        final Button OptB = (Button) findViewById(R.id.toprightbtn);
        final Button OptC = (Button) findViewById(R.id.botleftbtn);
        final Button OptD = (Button) findViewById(R.id.optD);

        final TextView livesTxt = (TextView) findViewById(R.id.livesText);

        livesTxt.setText(GameManager.getInstance().getLivesStr());

        OptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                // Simple and quick "Next Question" solver
                GameManager.getInstance().gotoNextQuestion(Question2.this);
            }
        });

        OptC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question2.this, livesTxt);
            }
        });

        OptD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question2.this, livesTxt);
            }
        });

        OptA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question2.this, livesTxt);
            }
        });

    }

}
