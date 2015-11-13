package team5.trickygame.LeaderboardClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import team5.trickygame.GameManager;
import team5.trickygame.util.QuestionTimeScore;

/**
 * Created by eternia on 11/3/15.
 */
public class LeaderboardLocal {
    private static LeaderboardLocal instance;
    private SQLiteDatabase db;
    private SQLiteDbHelper dbHelper;
    private long RunID; // SQL ID for the current game run

    // enforce singleton
    private LeaderboardLocal(){
        dbHelper = new SQLiteDbHelper(GameManager.getContext());
        db = dbHelper.getWritableDatabase();
    }

    public static LeaderboardLocal getInstance(){
        if(instance == null){
            instance = new LeaderboardLocal();
        }
        return instance;
    }

    public void startQuiz(){
        ContentValues values = new ContentValues();
        values.put("Time",0);
        values.put("Score",0);
        values.put("Question",0);
        this.RunID = db.insert("Leaderboard",null,values);
    }

    public void postQuestion(QuestionTimeScore qts){
        ContentValues values = new ContentValues();
        values.put("ID_LB",RunID);
        values.put("Time",Long.toString(qts.getTime()));
        values.put("Score",Float.toString(qts.getScore()));
        values.put("Question",Integer.toString(qts.getQuestion()));
        db.insert("Questions",null,values);
    }

    public void endQuiz(QuestionTimeScore fullTime){
        // end the quiz and reports the full time taken
        ContentValues values = new ContentValues();
        values.put("Time",Long.toString(fullTime.getTime()));
        values.put("Score",Float.toString(fullTime.getScore()));
        values.put("Question",Integer.toString(fullTime.getQuestion()));
        db.update("Leaderboard", values, "ID=" + RunID, null);
    }

    public void clearQuizTimes(){
        // clear all local times
        db.delete("Leaderboard",null,null);
        db.delete("Questions",null,null);
    }

    /*
      Returns an Array of QuestionTimeScore Array pairs
      The VERY last QTS of the array is the Full time for the quiz
     */
    public List<List<QuestionTimeScore>> getLeaderboard(int scores){
        LinkedList<List<QuestionTimeScore>> list = new LinkedList<>();
        Cursor cID = db.rawQuery("SELECT * FROM Leaderboard WHERE Score>0 ORDER BY Score DESC LIMIT "+scores+";", null);

        // go through the leaderboard
        while(cID.moveToNext()){
            LinkedList<QuestionTimeScore> qList = new LinkedList<>();
            long ID = cID.getLong(0);
            Cursor cQ = db.rawQuery("SELECT Question,Time,Score FROM Questions WHERE ID_LB=" + ID + " ORDER BY Question ASC;", null);
            // retrive all Questions from this query
            while (cQ.moveToNext()) {
                // loop through questions
                qList.add(new QuestionTimeScore(cQ.getInt(0), cQ.getLong(1), cQ.getFloat(2)));
            }

            // Add the final times
            qList.add(new QuestionTimeScore(cID.getInt(3), cID.getLong(1), cID.getFloat(2)));

            // add this High score to the mega-list
            list.add(qList);
        }
        return list;
    }
}

// referenced from:
//      http://developer.android.com/training/basics/data-storage/databases.html
class SQLiteDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Leaderboard.db";
    private static final String TABLE_Leaderboard = "Leaderboard";
    private static final String TABLE_Questions = "Questions";

    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_Leaderboard + "(ID INTEGER PRIMARY KEY, Time NUMERIC, Score NUMERIC, Question NUMERIC);");
        db.execSQL("CREATE TABLE " + TABLE_Questions + "(ID_LB NUMERIC, Score NUMERIC, Question NUMERIC, Time NUMERIC);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP " + TABLE_Leaderboard + ";");
        db.execSQL("DROP " + TABLE_Questions + ";");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}