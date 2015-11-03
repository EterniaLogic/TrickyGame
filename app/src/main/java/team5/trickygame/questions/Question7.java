package team5.trickygame.questions;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;

public class Question7 extends Question {
    TextView livesTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question7);

        // Generate notification (outside of App)
        // Problem: If you fail the quiz then use this, you will still be able to
        //          continue the quiz.
        Intent intent = new Intent(this, Question10.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 01, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("You are so smart");
        builder.setContentText("Press here for next question");
        builder.setNumber(101);
        builder.setSmallIcon(android.R.color.transparent);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManger =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManger.notify(01, notification);

        // Helps keep the GameManager Question ticker in sync
        GameManager.getInstance().incQuestionNumber();

        livesTxt = (TextView) findViewById(R.id.livesText);
        livesTxt.setText(GameManager.getInstance().getLivesStr());
    }
    public void checkCorrect(View v)
    {
        // Used if an answer was incorrect?
        GameManager.getInstance().checkEndGame(Question7.this, livesTxt);
    }

}
