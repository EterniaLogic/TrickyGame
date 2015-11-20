package team5.trickygame.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import team5.trickygame.R;

public class SoundService extends Service {
    private static final String TAG = "MyService";
    static MediaPlayer player;
    private static float musicVolume = 1.0f;
    private static float gameVolume = 1.0f;
    private final IBinder mBinder = new SoundBinder();

    public class SoundBinder extends Binder {
        public SoundService getService() {
            return SoundService.this;
        }
    }

    public static float getMusicVolume() {
        return musicVolume;
    }
    public static float getGameVolume() {
        return gameVolume;
    }
    public static void pauseMusic()
    {
        player.pause();
    }
    public static void playMusic()
    {
        player.start();
    }
    public static void setGameVolume(float requestedVolume) {
        gameVolume = requestedVolume;
        //player.setVolume(requestedVolume,requestedVolume);
    }
    public static void setMusicVolume(float requestedVolume) {
        musicVolume = requestedVolume;
        player.setVolume(musicVolume, musicVolume);

    }
    private Messenger outMessenger;

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("service", "onBind");
        return mBinder;
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        Foreground.init(this.getApplication());
        Foreground.get(this).addListener(listener);
        player = MediaPlayer.create(this, R.raw.backmusic);
        player.setLooping(true); // Set looping
        player.start();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onCreate();
        Foreground.get(this).removeListener(listener);
        player.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart");
       // player.start();
        return START_NOT_STICKY;
    }
    public Foreground.Listener listener = new Foreground.Listener(){
        public void onBecameForeground(Context c){
            if(!player.isPlaying()) {
                player = MediaPlayer.create(c, R.raw.backmusic);
                player.setLooping(true); // Set looping
                player.setVolume(musicVolume,musicVolume);
                player.start();
            }
        }
        public void onBecameBackground(){
            player.stop();
        }
    };
}
