/**
 * 
 */
package team5.trickygame.Q6Classes;


import team5.trickygame.Q6Classes.Q6Ball;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import team5.trickygame.Q6Classes.Q6MainThread;
import team5.trickygame.R;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class Q6GameControl extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = Q6GameControl.class.getSimpleName();

	private Q6MainThread thread;
	private Q6Ball ballObj;
	private Bitmap wallObj = BitmapFactory.decodeResource(getResources(), R.mipmap.wall);

	public Q6GameControl(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		ballObj = new Q6Ball(BitmapFactory.decodeResource(getResources(), R.mipmap.ball), 50, 50);



        // create the game loop thread
		thread = new Q6MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			ballObj.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (ballObj.isTouched()) {
				// the droid was picked up and is being dragged
				ballObj.setX((int)event.getX());
				ballObj.setY((int)event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (ballObj.isTouched()) {
				ballObj.setTouched(false);
			}
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
        //TODO: Fix wall position
        //canvas.drawBitmap(wallObj.createScaledBitmap(new Bitmap(), canvas.getWidth() / 2, canvas.getHeight() / 2, true));

        ballObj.draw(canvas);
	}
}
