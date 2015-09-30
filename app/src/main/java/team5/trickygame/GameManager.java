package team5.trickygame;

import android.util.Log;

/**
 * Created by eternia on 9/30/2015.
 */


// The GameManager is a thread that manages score
public class GameManager extends Thread {
    private static final String TAG = GameManager.class.getSimpleName();
    public static GameManager instance;
    private boolean quit;

    GameManager(){
        // save this instance to a static variable
        instance = this;

        // initialize other variables
        quit=false;
    }

    public void run(){
        // finite loop for thread, keeps class alive
        while(!quit) {
            // do operations here.
            // a ConcurrentLinkedQueue would be useful for asynchronous operations, this normally is
            // used with a while loop until the queue is empty

            try {
                Thread.sleep(100); // 100ms sleep Timer
            }catch(Exception e){
                Log.d(TAG, "[run] Thread sleep threw an error!");
            }
        }
        quit=false;
    }



    // Manages end-of-life
    public void die(){
        // Enforce an asynchronous stopper variable
        quit=true;

        // Wait until the main thread is dead (another fun while loop)
        while(quit){
            try {
                Thread.sleep(100); // 100ms Timer
            }catch(Exception e){
                Log.d(TAG, "[die] sleep threw an error!");
            }
        }

        // Finally, stop the thread, in case the above code does not fully stop it.
        try{
            this.join();
        }catch(Exception e){
            Log.d(TAG, "[die] Thread join threw an error!");
        }
    }
}
