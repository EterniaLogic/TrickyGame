package team5.trickygame.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;

import team5.trickygame.R;

/**
 * Created by Andrew on 10/25/2015.
 */
public class MusicManager {
    private static final String TAG = "MusicManager";
    private static HashMap player = new HashMap();

    public static float getMusicVolume(Context context) {
        return 100.0f;
    }

    public static void start(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.backmusic);
        float volume = getMusicVolume(context);
        Log.d(TAG, "Setting music volume to " + volume);
        mp.setVolume(volume, volume);
        player.put(1, mp);
        try {
            mp.setLooping(true);
            mp.start();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static void pause() {
        MediaPlayer p = (MediaPlayer) player.get(1);
        if (p.isPlaying()) {
                p.pause();
            }
    }

    public static void updateVolumeFromPrefs(Context context) {
        try {
            float volume = getMusicVolume(context);
            Log.d(TAG, "Setting music volume to " + volume);
            MediaPlayer p = (MediaPlayer) player.get(1);
            p.setVolume(volume, volume);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static void release() {
        Log.d(TAG, "Releasing media players");
        MediaPlayer mp = (MediaPlayer) player.get(1);
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.release();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        player.clear();
    }
}