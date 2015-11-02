package team5.trickygame.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by eternia on 10/13/15.
 */
public class QuestionTimeScore {
    private float score=0;
    private int question=0;
    private long time=0;
    private static int difficulty = 1; // score coefficient value

    public QuestionTimeScore(int question, long time){
        this.question = question;
        this.time = time;

        // Gradient score:
        //  ((k/difficulty)*Question)/time ::: k is a time constant in milliseconds
        score = (((500000.0f)/(float)difficulty)*(float)this.question)/(float)time;
    }

    // End of game score, time
    public QuestionTimeScore(int question_final, long time_final, float score_final){
        this.question = question_final;
        this.time = time_final;
        this.score = score_final;
    }

    // REF: http://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format
    // This ref was used because DateTime only assumes 1969 is when life began in the universe
    public String getHumanTime(){
        long milliseconds = time - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long hours = TimeUnit.MILLISECONDS.toHours(time);


        // formatter
        return String.format("%d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }

    public int getQuestion(){
        return question;
    }

    public long getTime(){
        return time;
    }

    public float getScore(){
        return score;
    }
}
