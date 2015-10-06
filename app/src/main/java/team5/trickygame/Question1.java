package team5.trickygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Question1 extends AppCompatActivity {

    int lives = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence
        setContentView(R.layout.activity_question1);



        //Initialize UI Components
        final Button OptA = (Button) findViewById(R.id.RestartBtn);
        final Button OptB = (Button) findViewById(R.id.MainMenuBtn);
        final Button OptC = (Button) findViewById(R.id.OptionsBtn);
        final Button OptD = (Button) findViewById(R.id.optD);

        final TextView livesTxt = (TextView) findViewById(R.id.livesText);

        livesTxt.setText(Integer.toString(lives));

        OptD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(Question1.this,EndGameActivity.class);
                intent.putExtra("val","You completed the Tricky Quiz");
                intent.putExtra("color","Green");
                Question1.this.startActivity(intent);
                finish();

            }
        });

        OptA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                checkEndGame(livesTxt);
            }
        });

        OptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                checkEndGame(livesTxt);

            }
        });

        OptC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                checkEndGame(livesTxt);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkEndGame(TextView txt){
        lives--;
        txt.setText(Integer.toString(lives));
        if (lives==0){
            Intent intent = new Intent(Question1.this,EndGameActivity.class);
            intent.putExtra("val", "You lose");
            intent.putExtra("color","red");
            Question1.this.startActivity(intent);
            finish();
        }
    }
}
