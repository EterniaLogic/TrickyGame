package team5.trickygame.questions;

<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java
import android.animation.ObjectAnimator;
import team5.trickygame.ShakeDetector;
import android.app.Activity;
=======
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
=======
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java
import android.widget.ImageView;

<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java
public class Question10 extends Question   {
=======
import team5.trickygame.GameManager;
import team5.trickygame.R;
import team5.trickygame.ShakeDetector;

public class Question10 extends Question {
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java

    int shakeCount = 0;
    boolean doneShaking = false;
    ImageView can;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java
    int shakeCount = 0;

    ImageView can;

    TextView livesTxt;
=======
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);

        can = (ImageView) findViewById(R.id.sodaCan);

        final Button wrongAnswer = (Button) findViewById(R.id.Q10Wrongbtn);

        final TextView livesTxt = (TextView) findViewById(R.id.livesText);

        livesTxt.setText(GameManager.getInstance().getLivesStr());


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

<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java
    public void handleShakeEvent(int count) {
        System.out.print("Shake Count: " + count);
        Log.e("Shake Count", String.valueOf(shakeCount));
        shakeCount += count;
        if (shakeCount > 12) {
=======
    public void handleShakeEvent(int count){
        System.out.print("Shake Count: "+ count);
        Log.e("Shake Count", String.valueOf(shakeCount));
        shakeCount += count;
        if (shakeCount > 12 && !doneShaking){
            // timeout for end of animation, then goto the next question:
            doneShaking = true;
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java
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
<<<<<<< HEAD:app/src/main/java/team5/trickygame/Question10.java

            //TODO: Connect to next Question
        } else if (shakeCount > 8) {
=======
        }
        else if (shakeCount > 8){
>>>>>>> master:app/src/main/java/team5/trickygame/questions/Question10.java
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


}
