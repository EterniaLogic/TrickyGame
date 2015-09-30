package team5.trickygame;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by eternia on 9/30/2015.
 */
public class TrickyGameTestCase extends ActivityInstrumentationTestCase2<TrickyGame> {
    public TrickyGameTestCase() {
        super(TrickyGame.class);
    }

    public void testActivityExists() {
        // make sure that this activity exists
        assertNotNull(getActivity());
    }

    public void testGameManager() {
        // Make sure that the GameManager Class has been declared
        assertNotNull(GameManager.getInstance());

        // Make sure that the thread is running
        assertEquals(true, GameManager.getInstance().running);
    }
}
