package team5.trickygame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView status = (TextView) findViewById(R.id.endGameStat);

        Intent intent = getIntent();

        status.setText(intent.getStringExtra("val"));

        if (intent.getStringExtra("color").equals("red")){
            status.setTextColor(Color.RED);
        } else{
            status.setTextColor(Color.GREEN);
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

    public void goToOptions() {
        // TODO - implement EndGame.goToOptions

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

        this.startActivity(intent);
        finish();
    }
}