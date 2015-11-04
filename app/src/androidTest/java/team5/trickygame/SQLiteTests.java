package team5.trickygame;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import team5.trickygame.LeaderboardClasses.LeaderboardLocal;
import team5.trickygame.util.QuestionTimeScore;

/**
 * Created by eternia on 11/4/15.
 */
public class SQLiteTests extends ActivityInstrumentationTestCase2<MainMenu> {
    public SQLiteTests() {
        super(MainMenu.class);
    }
    public void testSQLiteWin() throws Exception{
        assertNotNull(getActivity());
        Log.v("[SQLite Test]","Start SQLite Test");
        LeaderboardLocal LBL = LeaderboardLocal.getInstance();
        assertNotNull(LBL);

        // make a blank canvas to test with
        LBL.clearQuizTimes();

        // init some loop variables
        Random r = new Random(1232);
        long total = 0;
        int low = 100; // minimum ms for human reaction time
        int high = 10000; // really slow human (10 seconds)

        // start simulation
        LBL.startQuiz();
        for(int i=1;i<100;i++) {
            long time = r.nextInt(high-low)+low;
            LBL.postQuestion(new QuestionTimeScore(i, time));
            total += time;
        }
        LBL.endQuiz(new QuestionTimeScore(1, total));

        // get the list of total times
        List<List<QuestionTimeScore>> scores = LBL.getLeaderboard(10);
        assertNotNull(scores); // TEST

        Iterator<List<QuestionTimeScore>> s = scores.iterator();
        while(s.hasNext()){
            List<QuestionTimeScore> singlescorelist = s.next();

            Log.v("[SQLite Test]", "Score:");
            Iterator<QuestionTimeScore> sQuestion = singlescorelist.iterator();
            while(sQuestion.hasNext()){
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion()>0); // TEST
                assertTrue(qts.getScore()>0.0f); // TEST
                Log.v("[SQLite Test]","----Question: "+qts.getQuestion()+" Score: "+qts.getScore()+" Time: "+qts.getHumanTime());
            }
        }

        LBL.clearQuizTimes();
    }

    public void testSQLiteTONS() throws Exception{
        assertNotNull(getActivity());
        Log.v("[SQLite Test]", "Start SQLite Test");
        LeaderboardLocal LBL = LeaderboardLocal.getInstance();
        assertNotNull(LBL);

        // make a blank canvas to test with
        LBL.clearQuizTimes();

        // init some loop variables
        Random r = new Random(1232);
        long total = 0;
        int low = 100; // minimum ms for human reaction time
        int high = 10000; // really slow human (10 seconds)

        // start simulation
        for(int j=0;j<10;j++) {
            LBL.startQuiz();
            for (int i = 1; i < 10; i++) {
                long time = r.nextInt(high - low) + low;
                LBL.postQuestion(new QuestionTimeScore(i, time));
                total += time;
            }
            LBL.endQuiz(new QuestionTimeScore(1, total));
        }

        // get the list of total times
        List<List<QuestionTimeScore>> scores = LBL.getLeaderboard(10);
        assertNotNull(scores); // TEST

        Iterator<List<QuestionTimeScore>> s = scores.iterator();
        while(s.hasNext()){
            List<QuestionTimeScore> singlescorelist = s.next();

            Log.v("[SQLite Test]", "Score:");
            Iterator<QuestionTimeScore> sQuestion = singlescorelist.iterator();
            while(sQuestion.hasNext()){
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion()>0); // TEST
                assertTrue(qts.getScore()>0.0f); // TEST
                Log.v("[SQLite Test]","----Question: "+qts.getQuestion()+" Score: "+qts.getScore()+" Time: "+qts.getHumanTime());
            }
        }

        LBL.clearQuizTimes();
    }
}
