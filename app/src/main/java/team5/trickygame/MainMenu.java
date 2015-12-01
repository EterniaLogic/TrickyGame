package team5.trickygame;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import team5.trickygame.util.SoundService;

public class MainMenu extends Activity {
    public static boolean firstRun = true;
    private SoundService soundService;
    protected ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            soundService = ((SoundService.SoundBinder) binder).getService();
            Log.d("ServiceConnection", "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            soundService = null;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(this,SoundService.class);
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        Log.d("activity", "onPause");
        if (soundService != null) {
            unbindService(mConn);
            soundService = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        if(firstRun) {
            // App has been started for the first time:
            firstRun = false;
            GameManager.getInitialInstance(this.getBaseContext());
            // Get the google account, used later for the LeaderboardGlobal.
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

            soundService = new SoundService();
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
        GameManager.getInstance().startQuiz(this);
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

}
