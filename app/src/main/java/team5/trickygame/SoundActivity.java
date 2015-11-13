package team5.trickygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import team5.trickygame.util.SoundService;

public class SoundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound2);
        if(SoundService.getMusicVolume() == 0.0f) {
            ImageView imageview = (ImageView) findViewById(R.id.speaker1);
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.muted_speaker));
            imageview.setTag("muted");
        }
        if(SoundService.getGameVolume() == 0.0f)
        {
            ImageView imageview2 = (ImageView) findViewById(R.id.speaker2);
            imageview2.setImageDrawable(getResources().getDrawable(R.drawable.muted_speaker));
            imageview2.setTag("muted");
        }
    }


    public void musicVolume(View v) {
        ImageView temp = (ImageView) v;

        if (temp.getTag().equals("unmuted")) {
            SoundService.setMusicVolume(0.0f);
            temp.setImageResource(R.drawable.muted_speaker);
            temp.setTag("muted");
        } else {
            SoundService.setMusicVolume(1.0f);
            temp.setImageResource(R.drawable.speaker);
            temp.setTag("unmuted");
        }
    }


    public void effectsVolume(View v){

        ImageView temp = (ImageView) v;


        if (temp.getTag().equals("unmuted")) {
            SoundService.setGameVolume(0.0f);
            temp.setImageResource(R.drawable.muted_speaker);
            temp.setTag("muted");
        } else {
            SoundService.setGameVolume(1.0f);
            temp.setImageResource(R.drawable.speaker);
            temp.setTag("unmuted");
        }
    }
}
