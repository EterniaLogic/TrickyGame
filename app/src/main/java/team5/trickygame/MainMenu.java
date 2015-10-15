package team5.trickygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {


    Credits credits;
    Leaderboards l;


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

	public void goToOptions(View V) {
        Toast.makeText(this, "Clicked on Options Button", Toast.LENGTH_LONG).show();
	}

	public void goToLeaderboards(View V) {
        Toast.makeText(this, "Clicked on Leaderboards Button", Toast.LENGTH_LONG).show();
	}

	public void startGame(View V) {
		// TODO - implement MainMenu.startGame

        Intent intent = new Intent(MainMenu.this, Question1.class);

        this.startActivity(intent);
        finish();
	}

	public void goToCredits(View V) {
		// TODO - implement MainMenu.goToCredits
	}
}
