package team5.trickygame.questions;
<<<<<<< Updated upstream:app/src/main/java/team5/trickygame/questions/Question1.java
/*
 *
 *   Created by Daniel Medina Sada and Andrew Scibeck
 *
 */
=======

>>>>>>> Stashed changes:app/src/main/java/team5/trickygame/questions/Question1.java
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

<<<<<<< Updated upstream:app/src/main/java/team5/trickygame/questions/Question1.java

=======
>>>>>>> Stashed changes:app/src/main/java/team5/trickygame/questions/Question1.java
public class Question1 extends Question {
    Button[] buttonChoices = new Button[4];
    TextView livesTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        buttonChoices[0] = (Button) findViewById(R.id.topleftbtn);
        buttonChoices[1] = (Button) findViewById(R.id.toprightbtn);
        buttonChoices[2]= (Button) findViewById(R.id.botleftbtn);
        buttonChoices[3]= (Button) findViewById(R.id.botrightbtn);
        initButtonColors();
        livesTxt = (TextView) findViewById(R.id.livesText);
        livesTxt.setText(GameManager.getInstance().getLivesStr());
    }

    public void checkCorrect(View v)
    {
        ColorDrawable c = (ColorDrawable) v.getBackground();
        switch (c.getColor()) {
            case Color.BLUE: // goto the next question
                GameManager.getInstance().gotoNextQuestion(Question1.this);
                break;
            default: // Remove a life and goto the end-game screen if the lives hit 0
                GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                break;
        }
    }

    private void initButtonColors() {
        int[] colors = {Color.RED,Color.BLUE,Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.GREEN};
        //TODO Make it random
        for(int i = 0; i < 4; i++)
        {
            buttonChoices[i].setBackgroundColor(colors[i]);
            buttonChoices[i].setTextColor(colors[i+1]);
        }
    }
}
