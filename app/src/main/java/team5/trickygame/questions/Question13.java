package team5.trickygame.questions;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Random;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question13 extends Question {

    NumberPicker np1;
    NumberPicker np2;
    NumberPicker np3;

    TextView lives;
    ImageView safeBox;
    ImageButton nextQBtn;

    int[] correctPass;
    int[] currentPass = new int[]{0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question13);

        Random rn = new Random();
        correctPass = new int[]{rn.nextInt(10),rn.nextInt(10),rn.nextInt(10)};

        nextQBtn = (ImageButton) findViewById(R.id.NextBtn);
        nextQBtn.setVisibility(View.INVISIBLE);
        safeBox = (ImageView) findViewById(R.id.SafeBoxImg);

        safeBox.setImageResource(R.mipmap.safeclosed);

        lives = (TextView) findViewById(R.id.livesTxt);
        lives.setText(GameManager.getInstance().getLivesStr());

        np1 = (NumberPicker) findViewById(R.id.numberPicker);
        np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np3 = (NumberPicker) findViewById(R.id.numberPicker3);
        np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np1.setMaxValue(9);
        np1.setMinValue(0);

        np2.setMaxValue(9);
        np2.setMinValue(0);

        np3.setMaxValue(9);
        np3.setMinValue(0);

        final MediaPlayer mpCorrect = MediaPlayer.create(this, R.raw.correctpass);
        final MediaPlayer mpWrong = MediaPlayer.create(this, R.raw.wrongpass);
        mpCorrect.setVolume(3.0f,3.0f);
        mpWrong.setVolume(0.7f,0.7f);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (newVal == correctPass[0]){
                    mpCorrect.start();

                }
                else{
                    mpWrong.start();
                }
                currentPass[0] = newVal;
            }
        });

        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (newVal == correctPass[1]){
                    mpCorrect.start();
                }
                else{
                    mpWrong.start();
                }

                currentPass[1] = newVal;
            }
        });

        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (newVal == correctPass[2]){
                    mpCorrect.start();
                }
                else{
                    mpWrong.start();
                }

                currentPass[2] = newVal;
            }
        });
    }

    public void checkIfCorrect(View v){
        Log.e(String.valueOf(currentPass[0]),String.valueOf(correctPass[0]));
        if (currentPass[0] == correctPass[0] & currentPass[1] == correctPass[1] & currentPass[2] == correctPass[2]){
            safeBox.setImageResource(R.mipmap.safeopened);
            nextQBtn.setVisibility(View.VISIBLE);
            GameManager.getInstance().gotoNextQuestion(Question13.this);
        }else{
            GameManager.getInstance().checkEndGame(Question13.this, lives);
        }
    }

    public void pressedWrongBtn(View v){
            GameManager.getInstance().checkEndGame(Question13.this, lives);
    }

    public void goToNextQuestion(View v){
            GameManager.getInstance().gotoNextQuestion(Question13.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_question13, menu);
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
}
