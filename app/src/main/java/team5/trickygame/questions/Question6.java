package team5.trickygame.questions;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

/**
 * Created by Daniel Medina Sada on 9/30/2015.
 */


public class Question6 extends Question implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private TextView tv;
    private ImageView drawer;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mGravity;
    private float[] mGeomagnetic;
    float[] g = new float[3];
    int inclination;
    private ImageView nextQbtn;
    private TextView livesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // set our MainGamePanel as the View
        setContentView(R.layout.activity_question6);
        // Keep the screen on
        // https://developer.android.com/training/scheduling/wakelock.html#screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Grab the layout TextView
        //tv = (TextView) findViewById(R.id.tv_tilt);
        drawer = (ImageView) findViewById(R.id.drawerQ6);
        nextQbtn = (ImageView) findViewById(R.id.nextQbtn);
        nextQbtn.setVisibility(View.INVISIBLE);
        livesText = (TextView) findViewById(R.id.livesTxt);

        // Setup the sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Log.d(TAG, "accelerometer is null");
        }
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            Log.d(TAG, "magnetometer is null");
        }

        // Detect the window position
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                Log.e(TAG, "Rotation 0");
                break;
            case Surface.ROTATION_90:
                Log.e(TAG, "Rotation 90");
                break;
            case Surface.ROTATION_180:
                Log.e(TAG, "Rotation 180");
                break;
            case Surface.ROTATION_270:
                Log.e(TAG, "Rotation 270");
                break;
            default:
                Log.w(TAG, "Rotation unknown");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * Convert degrees to absolute tilt value between 0-100
     */
    private int degreesToPower(int degrees) {
        // Tilted back towards user more than -90 deg
        if (degrees < -90) {
            degrees = -90;
        }
        // Tilted forward past 0 deg
        else if (degrees > 0) {
            degrees = 0;
        }
        // Normalize into a positive value
        degrees *= -1;
        // Invert from 90-0 to 0-90
        degrees = 90 - degrees;
        // Convert to scale of 0-100
        float degFloat = degrees / 90f * 100f;
        return (int) degFloat;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d(TAG, "onSensorChanged()");
        if (event.values == null) {
            Log.w(TAG, "event.values is null");
            return;
        }
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
            default:
                Log.w(TAG, "Unknown sensor type " + sensorType);
                return;
        }
        if (mGravity == null) {
            Log.w(TAG, "mGravity is null");
            return;
        }
        if (mGeomagnetic == null) {
            Log.w(TAG, "mGeomagnetic is null");
            return;
        }
        float R[] = new float[9];
        if (! SensorManager.getRotationMatrix(R, null, mGravity, mGeomagnetic)) {
            Log.w(TAG, "getRotationMatrix() failed");
            return;
        }



        float orientation[] = new float[9];
        SensorManager.getOrientation(R, orientation);
        // Orientation contains: azimuth, pitch and roll - we'll use roll


        float roll = orientation[2];
        int rollDeg = (int) Math.round(Math.toDegrees(roll));
        int power = degreesToPower(rollDeg);
        //Log.d(TAG, "deg=" + rollDeg + " power=" + power);
        //tv.setText(String.valueOf(power));


        //TODO: Compare to degrees with power
        g = event.values.clone();

        double norm_Of_g = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);

        // Normalize the accelerometer vector
        g[0] = g[0] / (float) norm_Of_g;
        g[1] = g[1] / (float) norm_Of_g;
        g[2] = g[2] / (float) norm_Of_g;

        inclination = (int) Math.round(Math.toDegrees(Math.acos(g[2])));

        if (inclination < 25 || inclination > 155)
        {
            // device is flat
            drawer.setImageResource(team5.trickygame.R.mipmap.foundkeydrawer);
            nextQbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            // device is not flat
            drawer.setImageResource(team5.trickygame.R.mipmap.keydrawer);
            nextQbtn.setVisibility(View.INVISIBLE);
        }

    }

    public void wrongButton(View v){

        GameManager.getInstance().checkEndGame(Question6.this, livesText);

    }


    public void GoToNextQuestion(View v){

        GameManager.getInstance().gotoNextQuestion(Question6.this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
