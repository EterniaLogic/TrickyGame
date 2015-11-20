package team5.trickygame.questions;
/*
 *
 *   Created by Daniel Medina Sada
 *
 */

import android.content.Context;
<<<<<<< HEAD
<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> origin/Daniel
=======
>>>>>>> master
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> origin/Daniel
=======
>>>>>>> origin/Daniel
=======
>>>>>>> master
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.Q10Classes.ShakeDetector;
import team5.trickygame.R;

public class Question10 extends Question   {

    boolean doneShaking = false;
    int shakeCount = 0;
    ImageView can;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);
        can = (ImageView) findViewById(R.id.sodaCan);

        final Button wrongAnswer = (Button) findViewById(R.id.Q10Wrongbtn);

        // Set current Lives
        final TextView livesTxt = (TextView) findViewById(R.id.livesText);
        livesTxt.setText(GameManager.getInstance().getLivesStr());

        // Remove life if wrong answer and go to EndGame screen if no more lives
        wrongAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                GameManager.getInstance().checkEndGame(Question10.this, livesTxt);
            }
        });

        //Set initial image
        can.setImageResource(R.drawable.sodacan1);
        AnimationDrawable canExplodeAnimation;

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        //Count the shakes
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });
    }

    //When there is a shake
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public void handleShakeEvent(int count){
        System.out.print("Shake Count: "+ count);
        Log.e("Shake Count", String.valueOf(shakeCount));
=======
    public void handleShakeEvent(int count) {
    //Count the shakes for can to explode
>>>>>>> origin/Daniel
=======
    public void handleShakeEvent(int count) {
    //Count the shakes for can to explode
>>>>>>> origin/Daniel
=======
    public void handleShakeEvent(int count) {
    //Count the shakes for can to explode
>>>>>>> master
        shakeCount += count;
        if (shakeCount > 12 && !doneShaking){
            // timeout for end of animation, then goto the next question:
            doneShaking = true;
            can.setImageDrawable(null);
            new Thread(new Runnable(){
                public void run(){
                    try {
                        // wait for n milliseconds
                        Thread.sleep(3000);

                        // Goto the next question
                        GameManager.getInstance().setTimeMod(3000);
                        GameManager.getInstance().gotoNextQuestion(Question10.this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // Load the ImageView that will host the animation and
            // set its background to our AnimationDrawable XML resource.
            can.setBackgroundResource(R.drawable.soda_explode);
            AnimationDrawable frameAnimation = (AnimationDrawable) can.getBackground();
            frameAnimation.start();
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        }
        else if (shakeCount > 8){
=======
        } else if (shakeCount > 8) {
>>>>>>> origin/Daniel
=======
        } else if (shakeCount > 8) {
>>>>>>> origin/Daniel
=======
=======

>>>>>>> origin/Daniel
        } else if (shakeCount > 8) {
>>>>>>> master
            can.setImageResource(R.drawable.sodacan4);
        } else if (shakeCount > 4) {
            can.setImageResource(R.drawable.sodacan3);
        } else if (shakeCount > 2) {
            can.setImageResource(R.drawable.sodacan2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question10, menu);
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
