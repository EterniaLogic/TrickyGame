package team5.trickygame.questions;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import team5.trickygame.GameManager;
import team5.trickygame.R;


/*
 * Created by Feyi Oyewole
 */

public class Question12 extends Question {
    Button[] buttonChoices = new Button[4];
    TextView livesTxt;

    private static ImageView imgView;
    private static ImageView imgView1;
    private static ImageView imgView2;
    private static ImageView imgView3;

    private static Button buttonSw;
    private static Button buttonSw1;
    private static Button buttonSw2;
    private static Button buttonSw3;

    private int current_image_index;
    int[] images = {R.mipmap.six_image, R.mipmap.three_image, R.mipmap.two_image, R.mipmap.zero_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question12);
        buttonClick();

        //livesTxt = (TextView) findViewById(R.id.livesText);
        //livesTxt.setText(GameManager.getInstance().getLivesStr());

    }
    public void checkCorrect (View v){
        if (v.getId() == R.id.FLbttn)
            GameManager.getInstance().gotoNextQuestion(Question12.this);
        else
            GameManager.getInstance().checkEndGame(Question12.this, livesTxt);


    }
    public void buttonClick() {
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView1 = (ImageView) findViewById(R.id.imageView2);
        imgView2 = (ImageView) findViewById(R.id.imageView3);
        imgView3 = (ImageView) findViewById(R.id.imageView4);

        buttonSw = (Button) findViewById(R.id.Leftbttn);
        buttonSw1 = (Button) findViewById(R.id.leftbttn);
        buttonSw2 = (Button) findViewById(R.id.rightbttn);
        buttonSw3 = (Button) findViewById(R.id.Rightbttn);


        buttonSw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image_index++;
                        current_image_index = current_image_index % images.length;
                        imgView2.setImageResource(images[current_image_index]);

                    }
                }
        );
        buttonSw1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image_index++;
                        current_image_index = current_image_index % images.length;
                        imgView3.setImageResource(images[current_image_index]);

                    }
                }
        );
        buttonSw2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image_index++;
                        current_image_index = current_image_index % images.length;
                        imgView.setImageResource(images[current_image_index]);

                    }
                }
        );
        buttonSw3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image_index++;
                        current_image_index = current_image_index % images.length;
                        imgView1.setImageResource(images[current_image_index]);

                    }
                }
        );



    }
}
