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
<<<<<<< HEAD

    public void testSQLiteWin() throws Exception {
        assertNotNull(getActivity());
        Log.v("[SQLite Test]", "Start SQLite Test");
=======
    public void testSQLiteWin() throws Exception{
        assertNotNull(getActivity());
        Log.v("[SQLite Test]","Start SQLite Test");
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
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
<<<<<<< HEAD
        for (int i = 1; i < 100; i++) {
            long time = r.nextInt(high - low) + low;
=======
        for(int i=1;i<100;i++) {
            long time = r.nextInt(high-low)+low;
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
            LBL.postQuestion(new QuestionTimeScore(i, time));
            total += time;
        }
        LBL.endQuiz(new QuestionTimeScore(1, total));

        // get the list of total times
        List<List<QuestionTimeScore>> scores = LBL.getLeaderboard(10);
        assertNotNull(scores); // TEST

        Iterator<List<QuestionTimeScore>> s = scores.iterator();
<<<<<<< HEAD
        while (s.hasNext()) {
=======
        while(s.hasNext()){
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
            List<QuestionTimeScore> singlescorelist = s.next();

            Log.v("[SQLite Test]", "Score:");
            Iterator<QuestionTimeScore> sQuestion = singlescorelist.iterator();
<<<<<<< HEAD
            while (sQuestion.hasNext()) {
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion() > 0); // TEST
                assertTrue(qts.getScore() > 0.0f); // TEST
                Log.v("[SQLite Test]", "----Question: " + qts.getQuestion() + " Score: " + qts.getScore() + " Time: " + qts.getHumanTime());
=======
            while(sQuestion.hasNext()){
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion()>0); // TEST
                assertTrue(qts.getScore()>0.0f); // TEST
                Log.v("[SQLite Test]","----Question: "+qts.getQuestion()+" Score: "+qts.getScore()+" Time: "+qts.getHumanTime());
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
            }
        }

        LBL.clearQuizTimes();
    }

<<<<<<< HEAD
    public void testSQLiteTONS() throws Exception {
=======
    public void testSQLiteTONS() throws Exception{
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
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
<<<<<<< HEAD
        for (int j = 0; j < 10; j++) {
=======
        for(int j=0;j<10;j++) {
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
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
<<<<<<< HEAD
        while (s.hasNext()) {
=======
        while(s.hasNext()){
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
            List<QuestionTimeScore> singlescorelist = s.next();

            Log.v("[SQLite Test]", "Score:");
            Iterator<QuestionTimeScore> sQuestion = singlescorelist.iterator();
<<<<<<< HEAD
            while (sQuestion.hasNext()) {
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion() > 0); // TEST
                assertTrue(qts.getScore() > 0.0f); // TEST
                Log.v("[SQLite Test]", "----Question: " + qts.getQuestion() + " Score: " + qts.getScore() + " Time: " + qts.getHumanTime());
=======
            while(sQuestion.hasNext()){
                QuestionTimeScore qts = sQuestion.next();
                assertTrue(qts.getQuestion()>0); // TEST
                assertTrue(qts.getScore()>0.0f); // TEST
                Log.v("[SQLite Test]","----Question: "+qts.getQuestion()+" Score: "+qts.getScore()+" Time: "+qts.getHumanTime());
>>>>>>> 0286c442bc563b819563dc62db4ce00fc20d174f
            }
        }

        LBL.clearQuizTimes();
    }
}
