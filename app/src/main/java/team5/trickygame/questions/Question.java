package team5.trickygame.questions;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;

import team5.trickygame.GameManager;
import team5.trickygame.R;

/**
 * Created by Eternia on 10/1/2015.
 */
public abstract class Question extends Activity {
    public Question(){
        super();
        GameManager.getInstance().setCurrentQuestion(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question1, menu);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation/keyboard change
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onConfigurationChanged(newConfig);
        }
    }

}
