package team5.trickygame;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import team5.trickygame.util.Command;
import team5.trickygame.util.QuestionTimeScore;

/**
 * Created by eternia (Brent Clancy) on 9/30/2015.
 */


// The GameManager is a thread that manages score
public class GameManager extends Thread {
    // static members
    private static final String TAG = GameManager.class.getSimpleName();

    private static GameManager instance = null;

    // non-static members
    public boolean running;
    private boolean quit;

    private LinkedList<QuestionTimeScore> questionScores = new LinkedList<QuestionTimeScore>(); // for mid-game statistics
    private long startTime = 0, lastQTime=0; // used for end-game and mid-game statistics
    private float score = 0;
    private int questionNum = 0, lives=0; // question Number used for end-game
    private boolean gameStarted=false;
    ConcurrentLinkedQueue<Object> taskManager = new ConcurrentLinkedQueue<Object>();

    // Questions list: (Actual Android activities)
    private LinkedList<Class<? extends Question>> questions = new LinkedList<Class<? extends Question>>();

    // internal usage for the LeaderboardServer
    private String account="";
    private LeaderboardServer LBS;

    GameManager(){
        // initialize other variables
        quit=false;
        running = false;
        this.start();

        LBS = new LeaderboardServer(this);

        // TODO: Add every question here
        questions.add(Question1.class);
    }

    public static GameManager getInstance() {
        if(instance == null)
            instance = new GameManager();

        return instance;
    }


    public void setAccount(String name){
        this.account = name;
        //LBS.setAccount(this.account);
        LeaderboardServer.AsyncA async = new LeaderboardServer.AsyncA();
        async.passed = this.account;
        async.LBS = this.LBS;
        this.taskManager.add(async);
    }

    public boolean noAccount(){
        return this.account.equals("");
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
        txt.setText(Integer.toString(GameManager.getInstance().getLives()));
        if (GameManager.getInstance().getLives()==0){
            Intent intent = new Intent(thisQ,EndGameActivity.class);
            intent.putExtra("val", "You lose");
            intent.putExtra("color","red");
            thisQ.startActivity(intent);
            thisQ.finish();
        }
    }

    // Start the quiz, this updates all relevant fields these include:
    //  # of questions
    //  amount of time spent for each question
    public void startQuiz(){
        this.startTime = System.currentTimeMillis();
        this.lastQTime=System.currentTimeMillis(); // since we started the first question
        this.questionNum = 0; // number of correct questions
        this.score = 0.0f; // new game
        this.questionScores.clear(); // clear out times
        this.lives=5; // divided by difficulty
        this.gameStarted = true; // used when getting data out of endGame
    }

    // Increments the Question #, also uses a LinkedList for
    //  End-game statistics, used for score-keeping
    public void incQuestionNumber(){
        this.questionNum++; // a question was correct!
        long sysms = System.currentTimeMillis();
        QuestionTimeScore qts = new QuestionTimeScore(questionNum, (sysms-this.lastQTime));
        score += qts.getScore(); // total score tally
        questionScores.add(qts); // add QTS

        this.lastQTime=System.currentTimeMillis();

        // post this question
        LeaderboardServer.AsyncQ qpass = new LeaderboardServer.AsyncQ();
        qpass.LBS = this.LBS;
        qpass.passed = qts;
        taskManager.add(qpass);
    }



    public float getTotalScore(){ // return the current score
        return score;
    }

    // returns a list of all of the scores, appended is the total score
    public LinkedList<QuestionTimeScore> endQuizStats(){
        // has endQuizStats been called before?
        if(this.gameStarted) {
            this.gameStarted = false; // prevent it from being called again
            questionScores.add(new QuestionTimeScore(questionNum, (System.currentTimeMillis() - this.startTime), score));
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
                if(t instanceof LeaderboardServer.AsyncKey){
                    final LeaderboardServer.AsyncKey request = (LeaderboardServer.AsyncKey)t;
                    // Asyncronously timed key retrieval
                    if(request.timeout <= 0){
                        request.timeout = 3000; // 30 seconds
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                try { Thread.sleep(500); } catch (InterruptedException e) {}

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
