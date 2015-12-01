package team5.trickygame;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by eternia on 9/30/2015.
 */
public class TrickyGameTestCase extends ActivityInstrumentationTestCase2<MainMenu> {
    public TrickyGameTestCase() {
        super(MainMenu.class);
    }

    public void testActivityExists() {
        // make sure that this activity exists
        Log.v("[Activity Test]", "Start Activity Test");
        assertNotNull(getActivity());
    }

    public void testGameManager() throws InterruptedException {
        // Make sure that the GameManager Class has been declared
        Log.v("[GameManager Test]", "Start GameManager Test");
        assertNotNull(GameManager.getInstance());

        // Make sure that the thread is running
        assertEquals(true, GameManager.getInstance().running);

        GameManager.getInstance().die();
    }
}
