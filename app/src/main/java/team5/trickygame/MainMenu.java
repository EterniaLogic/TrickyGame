package team5.trickygame;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import team5.trickygame.questions.Question1;
import team5.trickygame.util.MusicManager;

public class MainMenu extends Activity {
    public static boolean firstRun = true;
    boolean continueMusic = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        if(firstRun) {
            // App has been started for the first time:
            firstRun = false;


            // Get the google account, used later for the LeaderboardServer.
            Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
            for (Account account : accounts) {
                if(account.name.endsWith("@gmail.com")){
                    // tell the GameManager that we have an identity
                    GameManager.getInstance().setAccount(account.name);
                    break; // get out of here.
                }
            }

            // set a default account if none exists (This goes to an Anonymous account)
            // Anonymous accounts cannot get a score.
            if(GameManager.getInstance().noAccount()){
                GameManager.getInstance().setAccount("Anonymous");
            }
        }
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

    public void goToSound(View V) {
        //Toast.makeText(this, "Clicked on Sound Button", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainMenu.this, SoundActivity.class);
        startActivity(intent);
    }

    public void goToLeaderboards(View V) {
        Intent intent = new Intent(MainMenu.this, LeaderboardActiviy.class);
        startActivity(intent);
    }

    public void startGame(View V) {
        // Important for time and score keeping!
        // Note: on GameManager line 56, you can add additional questions.
        Intent intent = new Intent(MainMenu.this, Question1.class);

        GameManager.getInstance().startQuiz();
        this.startActivity(intent);
        finish();
    }

    public void goToCredits(View V) {
        Intent intent = new Intent(MainMenu.this, CreditsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation/keyboard change
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onConfigurationChanged(newConfig);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this);
    }
}
