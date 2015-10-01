package team5.trickygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity {

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        // Start up a persistent GameManager to keep score, along with database manager instance
        gameManager = new GameManager();
        gameManager.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
		Options options = new Options();
		//throw new UnsupportedOperationException();
	}

	public void goToLeaderboards() {
		LeaderboardScreen leaderboardScreen = new LeaderboardScreen();
		//throw new UnsupportedOperationException();
	}

	public void startGame() {
		// TODO - implement MainMenu.startGame
		throw new UnsupportedOperationException();
	}

	public void goToCredits() {
		// TODO - implement MainMenu.goToCredits
        Credits credits = new Credits();
		throw new UnsupportedOperationException();
	}
}