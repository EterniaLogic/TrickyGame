package team5.trickygame.questions;
/*
 *
 *   Created by Daniel Medina Sada
 *
 */
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.util.Log;
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

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    int shakeCount = 0;

    ImageView can;


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


        can.setImageResource(R.drawable.sodacan1);
        AnimationDrawable canExplodeAnimation;

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
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
    public void handleShakeEvent(int count) {
        System.out.print("Shake Count: " + count);
        Log.e("Shake Count", String.valueOf(shakeCount));
        shakeCount += count;
        if (shakeCount > 12) {
            can.setImageDrawable(null);
            // Load the ImageView that will host the animation and
            // set its background to our AnimationDrawable XML resource.
            can.setBackgroundResource(R.drawable.soda_explode);
            AnimationDrawable frameAnimation = (AnimationDrawable) can.getBackground();
            frameAnimation.start();

        } else if (shakeCount > 8) {
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