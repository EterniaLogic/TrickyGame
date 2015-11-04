package team5.trickygame;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class SoundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
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

    public void musicVolume(View v) {
        ImageView temp = (ImageView) v;


        if (temp.getTag().equals("unmuted")) {
            temp.setImageResource(R.drawable.muted_speaker);
            temp.setTag("muted");
        } else {
            temp.setImageResource(R.drawable.speaker);
            temp.setTag("unmuted");
        }
    }


    public void effectsVolume(View v){

        ImageView temp = (ImageView) v;


        if (temp.getTag().equals("unmuted")) {
            temp.setImageResource(R.drawable.muted_speaker);
            temp.setTag("muted");
        } else {
            temp.setImageResource(R.drawable.speaker);
            temp.setTag("unmuted");
        }
    }
}
