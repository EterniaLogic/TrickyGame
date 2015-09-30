package team5.trickygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Question1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence
        setContentView(R.layout.activity_question1);

        //Initialize UI Components
        final Button OptA = (Button) findViewById(R.id.optA);
        final Button OptB = (Button) findViewById(R.id.optB);
        final Button OptC = (Button) findViewById(R.id.optC);
        final Button OptD = (Button) findViewById(R.id.optD);

        int lives = 3;

        OptD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Intent intent = new Intent(Question1.this,EndGame.class);
                //Question1.this.startActivity(intent);

            }
        });

        OptA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Remove Life
            }
        });

        OptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Remove Life
            }
        });

        OptC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //Remove Life
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
}
