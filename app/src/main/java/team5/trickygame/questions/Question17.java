package team5.trickygame.questions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

import team5.trickygame.GameManager;
import team5.trickygame.MainMenu;
import team5.trickygame.R;
import team5.trickygame.util.QuestionTimeScore;

/**
 * Created by James Ewings
 */
public class Question17 extends Question {
    TextView livesText;
    TextView text;
    LinkedList<QuestionTimeScore> stats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question17);

        livesText = (TextView) findViewById(R.id.livesText);
        livesText.setText(GameManager.getInstance().getLivesStr());
        text = (TextView) findViewById(R.id.fakeStats);

        stats = GameManager.getInstance().endQuizStats();
        text.setText("You lose?\n"+"Score: "+GameManager.getInstance().getLives()+"\nTime: "+stats.getLast().getHumanTime());
        text.setTextColor(Color.RED);
    }

    public void rightAnswer(View v) {
        GameManager.getInstance().gotoNextQuestion(Question17.this);
    }

    public void wrongAnswer(View v) {
        int lives = GameManager.getInstance().getLives();
        GameManager.getInstance().checkEndGame(Question17.this, livesText);
        text.setText("You lose?\n"+"Score: "+GameManager.getInstance().getLives()+"\nTime: "+stats.getLast().getHumanTime());
        text.setTextColor(Color.RED);
    }
    public void goToMainMenu(View V) {
        Intent intent = new Intent(Question17.this, MainMenu.class);

        this.startActivity(intent);
        finish();

    }

    public void restartGame(View V) {
        // Important for time and score keeping!
        GameManager.getInstance().startQuiz(this);
    }
}
