package team5.trickygame.questions;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Random;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question14 extends Question {
    private float pivot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question14);
        Random r = new Random();
        pivot=r.nextFloat()*6;

        final TextView livesTxt = (TextView) findViewById(R.id.livesText);

        livesTxt.setText(GameManager.getInstance().getLivesStr());

        final RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar rating, float rate, boolean fromUser) {
                if(rate == 0.0f) return;
                final ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
                int calcPiv = Math.abs((int) (pivot - rate));
                Log.v("Rating: ", "" + rate+" of "+calcPiv);
                progress.setProgress(progress.getProgress() + calcPiv);
                rating.setRating(0.0f);

                if(progress.getMax() <= progress.getProgress()){
                    GameManager.getInstance().gotoNextQuestion(Question14.this);
                }
            }
        });
    }
}
