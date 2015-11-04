package team5.trickygame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

import team5.trickygame.questions.Question1;
import team5.trickygame.util.QuestionTimeScore;

public class EndGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView status = (TextView) findViewById(R.id.endGameStat);

        Intent intent = getIntent();

        // force built-in rounding
        LinkedList<QuestionTimeScore> qtsList = GameManager.getInstance().endQuizStats();
        long totalScore = (long)qtsList.getLast().getScore();


        status.setText(intent.getStringExtra("val")+"\nScore: "+totalScore+"\nTime: "+qtsList.getLast().getHumanTime());

        if (intent.getStringExtra("color").equals("red")){
            status.setTextColor(Color.RED);
        } else{
            status.setTextColor(Color.rgb(0,150,100));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void goToMainMenu(View V) {
        // TODO - implement MainMenu.startGame

        Intent intent = new Intent(EndGameActivity.this, MainMenu.class);

        this.startActivity(intent);
        finish();

    }

    public void restartGame(View V) {
        // TODO - implement MainMenu.startGame

        Intent intent = new Intent(EndGameActivity.this, Question1.class);

        // Important for time and score keeping!
        GameManager.getInstance().startQuiz(this);

        this.startActivity(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation/keyboard change
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onConfigurationChanged(newConfig);
        }
    }
}
