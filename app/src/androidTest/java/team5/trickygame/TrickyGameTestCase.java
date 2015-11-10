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
<<<<<<< HEAD
        Log.v("[Activity Test]", "Start Activity Test");
=======
        Log.v("[Activity Test]","Start Activity Test");
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
        assertNotNull(getActivity());
    }

    public void testGameManager() {
        // Make sure that the GameManager Class has been declared
<<<<<<< HEAD
        Log.v("[GameManager Test]", "Start GameManager Test");
=======
        Log.v("[GameManager Test]","Start GameManager Test");
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
        assertNotNull(GameManager.getInstance());

        // Make sure that the thread is running
        assertEquals(true, GameManager.getInstance().running);
    }

    public void testQuestions() {
        // loop through each question for every possibility
    }

<<<<<<< HEAD
    public void testQuestionScoring() {
        Log.v("[Scoring Test]", "Start Scoring Test");
=======
    public void testQuestionScoring(){
        Log.v("[Scoring Test]","Start Scoring Test");
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
    }
}
