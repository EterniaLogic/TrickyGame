package team5.trickygame;
/*
 *
 *   Created by Daniel Medina Sada
 *
 */

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import team5.trickygame.util.QuestionTimeScore;

// References:
//  http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65&aaid=90


public class LeaderboardActiviy  extends FragmentActivity implements
        ActionBar.TabListener {
    ListView Global, Local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_activiy);

        Global = (ListView) findViewById(R.id.listView);
        Local = (ListView) findViewById(R.id.listView2);

        drawGlobalBoard();
        drawLocalBoard();
    }

    public void drawGlobalBoard(){
        // Defined Array values to show in ListView
        LinkedList<String> values = new LinkedList<>();
        List<List<QuestionTimeScore>> scores = GameManager.getInstance().getLeaderboard(true, 10);

        // loop through global scores
        Iterator<List<QuestionTimeScore>> s = scores.iterator();
        while(s.hasNext()){
            List<QuestionTimeScore> singlescorelist = s.next();
            QuestionTimeScore lastQts = singlescorelist.get(singlescorelist.size() - 1);
            values.add(lastQts.getAccount()+" - "+lastQts.getScore()+" "+lastQts.getHumanTime());
        }

        // create the list adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, values);
        //Global.setAdapter(adapter);
    }

    public void drawLocalBoard(){
        Local = (ListView) findViewById(R.id.listView2);

        // Defined Array values to show in ListView
        LinkedList<String> values = new LinkedList<>();
        List<List<QuestionTimeScore>> scores = GameManager.getInstance().getLeaderboard(false, 10);

        // loop through the scores
        Iterator<List<QuestionTimeScore>> s = scores.iterator();
        while(s.hasNext()){
            List<QuestionTimeScore> singlescorelist = s.next();

            QuestionTimeScore lastQts = singlescorelist.get(singlescorelist.size()-1);
            values.add(lastQts.getScore()+" -- "+lastQts.getHumanTime());
        }

        // create the list adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, values.toArray(new String[values.size()]));
        Local.setAdapter(adapter);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
