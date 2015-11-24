/*Created by James Ewings*/

package team5.trickygame.questions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Random;

import team5.trickygame.GameManager;
import team5.trickygame.R;


public class Question4 extends Question {
    //Initiate an array to hold different buttons
    Button[] buttons = new Button[5];
    //Text to display lives
    TextView livesText;
    //Holds right btn number
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        //Put buttons into the array
        buttons[0] = (Button) findViewById(R.id.cup1Btn);
        buttons[1] = (Button) findViewById(R.id.cup2Btn);
        buttons[2] = (Button) findViewById(R.id.cup3Btn);
        buttons[3] = (Button) findViewById(R.id.cup4Btn);
        buttons[4] = (Button) findViewById(R.id.wordBtn);

        livesText = (TextView) findViewById(R.id.livesText);
        livesText.setText(GameManager.getInstance().getLivesStr());

        //Get a random number and store in index
        Random rand = new Random();
        index = rand.nextInt(5);
    }

    //This checks to see if the user got the correct answer
    public void check(View v) {
        //index holds the correct answer, if index matches the button then we move to next question else they lose a life
        switch (v.getId()) {
            case R.id.cup1Btn:
                if (index == 0)
                    GameManager.getInstance().gotoNextQuestion(Question4.this);
                else GameManager.getInstance().checkEndGame(Question4.this, livesText);
                break;
            case R.id.cup2Btn:
                if (index == 1)
                    GameManager.getInstance().gotoNextQuestion(Question4.this);
                else GameManager.getInstance().checkEndGame(Question4.this, livesText);
                break;
            case R.id.cup3Btn:
                if (index == 2)
                    GameManager.getInstance().gotoNextQuestion(Question4.this);
                else GameManager.getInstance().checkEndGame(Question4.this, livesText);
                break;
            case R.id.cup4Btn:
                if (index == 3)
                    GameManager.getInstance().gotoNextQuestion(Question4.this);
                else GameManager.getInstance().checkEndGame(Question4.this, livesText);
                break;
            case R.id.wordBtn:
                if (index == 4)
                    GameManager.getInstance().gotoNextQuestion(Question4.this);
                else GameManager.getInstance().checkEndGame(Question4.this, livesText);
                break;
        }
    }

}
