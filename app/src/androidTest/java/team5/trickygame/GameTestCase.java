package team5.trickygame;

import android.test.ActivityInstrumentationTestCase2;

import team5.trickygame.questions.Question;

/**
 * Created by eternia on 9/30/2015.
 */
public class GameTestCase extends ActivityInstrumentationTestCase2<MainMenu> {
    public GameTestCase() {
        super(MainMenu.class);
    }

    public void testEndQuiz() throws InterruptedException {
        // test start quiz
        assertNotNull(getActivity());
        GameManager.getInstance().startQuiz(getActivity());
        Thread.sleep(100);

        for(int i=0;i<5;i++){
            GameManager.getInstance().checkEndGame(GameManager.getInstance().getCurrentQuestion(),null);
        }

        // confirm that the quiz has ended
        assertEquals(GameManager.getInstance().getLives(), 0);

        while(GameManager.getInstance().getGameStarted()) Thread.sleep(100);
        assertFalse(GameManager.getInstance().getGameStarted());
        GameManager.getInstance().die();
        getActivity().onDestroy(); // prevents leaking
        System.gc();
    }

    public void testQuestionProgression() throws Exception {
        // test start quiz
        int tries=0;
        assertNotNull(getActivity());
        GameManager gm = GameManager.getInstance();
        gm.startQuiz(getActivity());
        Thread.sleep(100);
        Question oldQuestion = gm.getCurrentQuestion();
        while(!gm.getGameStarted()){
            GameManager.getInstance().gotoNextQuestion(GameManager.getInstance().getCurrentQuestion());

            // sleep until the next question hits
            while(gm.getCurrentQuestion() == oldQuestion) {
                Thread.sleep(50);
                tries++;
                if(tries > 15) throw new Exception("Question never switches over!");
            }
            tries=0; // reset tries for next question
            oldQuestion = gm.getCurrentQuestion();
        }

        // confirm that the quiz has ended
        gm.die();
        getActivity().onDestroy(); // prevents leaking
        System.gc();
    }
}
