package team5.trickygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import team5.trickygame.LeaderboardClasses.LeaderboardGlobal;
import team5.trickygame.LeaderboardClasses.LeaderboardLocal;
import team5.trickygame.questions.Question;
import team5.trickygame.questions.Question1;
import team5.trickygame.questions.Question10;
import team5.trickygame.questions.Question11;
import team5.trickygame.questions.Question12;
import team5.trickygame.questions.Question13;
import team5.trickygame.questions.Question14;
import team5.trickygame.questions.Question17;
import team5.trickygame.questions.Question2;
import team5.trickygame.questions.Question3;
import team5.trickygame.questions.Question4;
import team5.trickygame.questions.Question5;
import team5.trickygame.questions.Question6;
import team5.trickygame.questions.Question7;
import team5.trickygame.util.Command;
import team5.trickygame.util.QuestionTimeScore;




/**
 * Created by eternia (Brent Clancy) on 9/30/2015.
 */


// The GameManager is a thread that manages score
public class GameManager extends Thread {
    // static members
    private static final String TAG = GameManager.class.getSimpleName();

    // Enforce Singletons
    private static GameManager instance; //
    private static LeaderboardGlobal LBG;
    private static LeaderboardLocal LBL; // SQLLite file manager for data
    private static Context context;
    // non-static members
    public boolean running;
    public boolean sound;
    public ConcurrentLinkedQueue<Object> taskManager = new ConcurrentLinkedQueue<>();
    private boolean quit;
    private LinkedList<QuestionTimeScore> questionScores = new LinkedList<>(); // for mid-game statistics
    private long startTime = 0, lastQTime=0, timeminus=0; // used for end-game and mid-game statistics
    private float score = 0;
    private int questionNum = 0, lives=0; // question Number used for end-game
    private boolean gameStarted=false;
    // Questions list: (Actual Android activities)
    private LinkedList<Class<? extends Question>> questions = new LinkedList<>();
    // internal usage for the LeaderboardGlobal
    private String account="";
    private Question currentQuestion;


    private GameManager(){
        // initialize other variables
        quit=false;
        running = false;

        // TODO: Add questions in startQuiz()
    }

    public static GameManager getInitialInstance(Context context_){
        if(instance == null) {
            context = context_;
            instance = new GameManager();

            instance.start();
            LBL = LeaderboardLocal.getInstance();
            LBG = LeaderboardGlobal.getInstance();
        }
        return getInstance();
    }

    // getInstance makes a new GameManager
    //  Enforces a singleton
    public static GameManager getInstance() throws NullPointerException{
        if(instance == null) throw new NullPointerException("GameManager: Tried to get unknown instance");
        return instance;
    }

    public static Context getContext() throws NullPointerException{
        if(context == null) throw new NullPointerException("GameManager: Tried to get unknown Context");
        return context;
    }

    @Override
    public void finalize(){
        die();
    }

    public void setAccount(String name){
        this.account = name;
        //LBG.setAccount(this.account);
        LeaderboardGlobal.AsyncA async = new LeaderboardGlobal.AsyncA();
        async.passed = this.account;
        async.LBS = LBG;
        this.taskManager.add(async);
    }

    public boolean noAccount(){
        return this.account.equals("");
    }

    public Class<Question> getNextQuestionClass(Class<? extends Question> thisQuestion){
        int index = questions.indexOf(thisQuestion)+1;
        Class<Question> question = (Class<Question>)questions.get(index);
        return (index >= questions.size()) ? null : question;
    }

    // goes to the next question in the gameList
    public void gotoNextQuestion(Question thisQuestion){
        boolean next=false;

        Iterator i = questions.iterator();
        while(i.hasNext()){
            Class<? extends Question> item = (Class<? extends Question>)i.next();
            // If next is true, this "Next" one is the next question.
            // Otherwise, if it doesnt exist, then the player has finished the game.
            if(next){
                // our destined question has been reached!
                next = false;

                // this question was completed
                incQuestionNumber();

                // Intent to goto the next question

                Intent intent = new Intent(thisQuestion, item);
                thisQuestion.startActivity(intent);
                thisQuestion.finish();
                break; // exit out of iterator loop
            }else{
                // iterate through
                if(item.getName().equals(thisQuestion.getClass().getName())){
                    next=true; // goto the next question
                }
            }
        }

        // next was never reached, assume that the game has finished!
        if(next){
            GameManager.getInstance().incQuestionNumber();

            Intent intent = new Intent(thisQuestion,EndGameActivity.class);
            intent.putExtra("val","You completed the Tricky Quiz");
            intent.putExtra("color","Green");
            thisQuestion.startActivity(intent);
            thisQuestion.finish();
        }
    }

    /**		+    // goes to the next question in the gameList
     -     *
     -     * @param value
     -     */
    public void setTimeMod(long value){
        timeminus=value;
    }

    public int getLives(){
        return lives;
    }

    public String getLivesStr(){
        return Integer.toString(lives);
    }

    public void killLife(){
        lives--;
    }

    public void checkEndGame(Question thisQ, TextView txt){
        GameManager.getInstance().killLife();
        if(txt != null) txt.setText(Integer.toString(GameManager.getInstance().getLives()));
        if (GameManager.getInstance().getLives()==0){
            Intent intent = new Intent(thisQ,EndGameActivity.class);
            intent.putExtra("val", "You lose");
            intent.putExtra("color","red");
            thisQ.startActivity(intent);
            thisQ.finish();
        }

        Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
    }

    public boolean getGameStarted(){
        return gameStarted;
    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }

    public void setCurrentQuestion(Question question){
        currentQuestion = question;
    }

    // Start the quiz, this updates all relevant fields these include:
    //  # of questions
    //  amount of time spent for each question
    public void startQuiz(Activity thisActivity){
        this.startTime = System.currentTimeMillis();
        this.lastQTime=System.currentTimeMillis(); // since we started the first question
        this.questionNum = 0; // number of correct questions
        this.score = 0.0f; // new game
        this.questionScores.clear(); // clear out times
        this.lives=5; // divided by difficulty
        this.gameStarted = true; // used when getting data out of endGame
        LBL.startQuiz();
        LBG.startQuiz();

        ///////////////// TODO: ADD QUESTIONS HERE //////////////////
        questions.clear(); // clear the questions

        LinkedList<Class<? extends Question>> Tier1 = new LinkedList<>();
        LinkedList<Class<? extends Question>> Tier2 = new LinkedList<>();

        Tier1.add(Question1.class);
        Tier1.add(Question2.class);
        Tier1.add(Question3.class);
        Tier1.add(Question4.class);
        Tier1.add(Question5.class);
        Tier1.add(Question6.class);
        Collections.shuffle(Tier1);

        Tier2.add(Question7.class);
        Tier2.add(Question10.class);
        Tier2.add(Question11.class);
        Tier2.add(Question12.class);
        Tier2.add(Question13.class);
        Tier2.add(Question14.class);
        Collections.shuffle(Tier2);


        for (int i = 0; i < Tier1.size(); i++){
            questions.add(Tier1.get(i));
        }

        for (int i = 0; i < Tier2.size(); i++){
            questions.add(Tier2.get(i));
        }

        questions.add(Question17.class);

        ///////////////////////////// END ///////////////////////////

        Class<? extends Question> q = questions.getFirst();
        Intent intent = new Intent(thisActivity, q);
        thisActivity.startActivity(intent);
        if(!thisActivity.getLocalClassName().equals("MainMenu"))
            thisActivity.finish();
    }

    // Increments the Question #, also uses a LinkedList for
    //  End-game statistics, used for score-keeping
    public void incQuestionNumber(){
        this.questionNum++; // a question was correct!
        long sysms = System.currentTimeMillis();
        QuestionTimeScore qts = new QuestionTimeScore(questionNum, (sysms-this.lastQTime)-timeminus);
        score += qts.getScore(); // total score tally
        questionScores.add(qts); // add QTS
        timeminus=0;

        this.lastQTime=System.currentTimeMillis();

        // post this question to Local and Global
        LBL.postQuestion(qts);
        LBG.postQuestion(qts);
    }



    public float getTotalScore(){ // return the current score
        return score;
    }

    public void clearLocalLeaderboard(){
        LBL.clearQuizTimes();
    }

    public List<List<QuestionTimeScore>> getLeaderboard(boolean isGlobal, int scores){
        if(isGlobal){
            return LBG.getLeaderboard(scores);
        }else{
            return LBL.getLeaderboard(scores);
        }
    }

    // returns a list of all of the scores, appended is the total score
    public LinkedList<QuestionTimeScore> endQuizStats(){
        // has endQuizStats been called before?
        if(this.gameStarted) {
            this.gameStarted = false; // prevent it from being called again
            long time=0, score=0;

            Iterator<QuestionTimeScore> sQuestion = questionScores.iterator();
            while(sQuestion.hasNext()) {
                QuestionTimeScore qts = sQuestion.next();
                time+=qts.getTime();
                score+=qts.getScore();
            }

            QuestionTimeScore qtsEnd = new QuestionTimeScore(questionNum, time, score);
            questionScores.add(qtsEnd);
            LBL.endQuiz(qtsEnd);
            LBG.endQuiz(qtsEnd);
        }
        return questionScores;
    }


    // instance keepalive (GC wont eat me!)
    public void run(){
        running=true; // used for assertions

        // finite loop for thread, keeps class alive
        while(!quit) {
            // do operations here.
            // a ConcurrentLinkedQueue would be useful for asynchronous operations, this normally is
            // used with a while loop until the queue is empty
            while(!taskManager.isEmpty()){
                // get an object
                Object t = taskManager.poll();
                if(t instanceof LeaderboardGlobal.AsyncKey){
                    final LeaderboardGlobal.AsyncKey request = (LeaderboardGlobal.AsyncKey)t;
                    // Asyncronously timed key retrieval
                    if(request.timeout <= 0){
                        request.timeout = 300; // 30 seconds
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                try { Thread.sleep(500); } catch (InterruptedException e) {
                                    Log.d(TAG, "[run] Thread sleep threw an error!");
                                }

                                // run this method
                                request.execute();
                            }
                        });
                    }else{
                        request.timeout--;
                    }
                }else{
                    // standard raw command
                    Command com = (Command)t;
                    com.execute();
                }
            }

            try {
                Thread.sleep(100); // 100ms sleep Timer
            }catch(Exception e){
                Log.d(TAG, "[run] Thread sleep threw an error!");
            }
        }

        // end of thread
        quit=false;
        running=false; // used for assertions
    }

    // Manages end-of-life
    public void die(){

        // thread killing does not apply.
        this.interrupt();
    }


}
