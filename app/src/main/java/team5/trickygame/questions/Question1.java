package team5.trickygame.questions;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question1 extends Question {
    Button[] buttonChoices = new Button[4];
    TextView livesTxt;

    int correctButton;

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

        Random rand = new Random();
        correctButton = rand.nextInt(2);
    }

    public void checkCorrect(View v) {
        ColorDrawable c = (ColorDrawable) v.getBackground();
        if (correctButton == 0) {
            switch (c.getColor()) {
                case Color.BLUE: // goto the next question
                    GameManager.getInstance().gotoNextQuestion(Question1.this);
                    break;
                default: // Remove a life and goto the end-game screen if the lives hit 0
                    GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                    break;
            }
        }
        else {
            switch(v.getId()){
                case R.id.topleftbtn:
                    if (buttonChoices[0].getText().equals("BLUE"))
                        GameManager.getInstance().gotoNextQuestion(Question1.this);
                    else GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                    break;
                case R.id.toprightbtn:
                    if (buttonChoices[1].getText().equals("BLUE"))
                        GameManager.getInstance().gotoNextQuestion(Question1.this);
                    else GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                    break;
                case R.id.botleftbtn:
                    if (buttonChoices[2].getText().equals("BLUE"))
                        GameManager.getInstance().gotoNextQuestion(Question1.this);
                    else GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                    break;
                case R.id.botrightbtn:
                    if (buttonChoices[3].getText().equals("BLUE"))
                        GameManager.getInstance().gotoNextQuestion(Question1.this);
                    else GameManager.getInstance().checkEndGame(Question1.this, livesTxt);
                    break;
            }
        }

    }
    private void initButtonColors() {
        Integer c[] = {Color.RED,Color.BLUE,Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.GREEN};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.addAll(Arrays.<Integer>asList(c));

        Collections.shuffle(colors);

        for(int i = 0; i < 4; i++)
        {
            buttonChoices[i].setBackgroundColor(colors.get(i));
            buttonChoices[i].setTextColor(colors.get(i+1));
        }

        boolean isBlue = false;



        for (int i = 0; i < 4; i++){
            ColorDrawable d = (ColorDrawable) buttonChoices[i].getBackground();
            if (d.getColor() == Color.BLUE)
                isBlue = true;
        }

        if (!isBlue)
            correctButton = 1;
    }


}
