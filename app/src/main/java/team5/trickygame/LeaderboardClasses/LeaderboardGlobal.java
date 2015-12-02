package team5.trickygame.LeaderboardClasses;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import team5.trickygame.GameManager;
import team5.trickygame.util.Command;
import team5.trickygame.util.QuestionTimeScore;

/**
 * Created by eternia (Brent Clancy) on 10/13/15.
 */

// The Leaderboard Server uses POST http requests along with crc, Anti-DDOS, back-end checks, ect.
// to ensure that the Leaderboards are accurate and "Real".
// The Top score possible per difficulty is (questions*50,000)/difficulty


// All network stuff will shove an error if it is force on the "Main Thread".

public class LeaderboardGlobal {
    private static LeaderboardGlobal instance;
    HttpClient httpclient;
    HttpPost httppost;
    private String serverAddr = "https://eternialogic.com/TrickyGame/";
    private String iToken = "", cKey = "";
    private List<String> lKey = new LinkedList<>();
    private boolean serverThere=false;


    // REF: http://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
    private LeaderboardGlobal(){
        // begin scheduling leaderboard data
        GameManager.getInstance().taskManager.add(new AsyncKey());
    }

    public static LeaderboardGlobal getInstance(){
        if(instance == null) instance = new LeaderboardGlobal();
        return instance;
    }

    // Sets the initial account to use
    // Anonymous get their "own" category
    public void setAccount(String accName){
        LinkedList<BasicNameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("a", accName));
        doRequest("sa", pairs);
    }

    public void postQuestion(QuestionTimeScore qts){
        // post this question to Global
        LeaderboardGlobal.AsyncQ qpass = new LeaderboardGlobal.AsyncQ();
        qpass.LBS = this;
        qpass.passed = qts;
        GameManager.getInstance().taskManager.add(qpass);
    }

    public void startQuiz(){
        // start the quiz
        // TODO: setup at start of quiz 
    }

    public void endQuiz(QuestionTimeScore fullTime){
        // end the quiz and reports the full time taken
        // TODO: setup at end of quiz
    }

    public List<List<QuestionTimeScore>> getLeaderboard(int scores) {
        LinkedList<List<QuestionTimeScore>> list = new LinkedList<>();

        return list;
    }

////////////////////////////////////// Different Thread ////////////////////////////////////////////

    // post a question that has been dealt by the user, combine
    public void doPostQuestion(QuestionTimeScore qts){
        LinkedList<BasicNameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("q", Integer.toString(qts.getQuestion())));
        pairs.add(new BasicNameValuePair("t", Long.toString(qts.getTime())));
        pairs.add(new BasicNameValuePair("s", Float.toString(qts.getScore())));
        doRequest("nq", pairs);
    }

    private void keyChallenge(String input){
        int challenge = Integer.parseInt(input);
        LinkedList<BasicNameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("k", Integer.toString(challenge)));
        pairs.add(new BasicNameValuePair("s", lKey.get(challenge)));
        doRequest("kc", pairs);
    }

    // keyed request
    private String doRequest(String req, List<BasicNameValuePair> pairs){
        if(!serverThere) return "";
        try{
            httpclient = HttpClients.createDefault();
            /*TrustStrategy trustStrategy = new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    for (X509Certificate cert: chain) {
                        System.err.println(cert);
                    }
                    return false;
                }

            };

            SSLSocketFactory sslsf = new SSLSocketFactory("TLS", null, null, keystore, null,
                    trustStrategy, new AllowAllHostnameVerifier());
            Scheme https = new Scheme("https", 888, sslsf);
            httpclient.getConnectionManager().getSchemeRegistry().register(https);*/

            httppost = new HttpPost(serverAddr);

            // Generate initial key:
            iToken = UUID.randomUUID().toString();

            // Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<>(2);
            params.add(new BasicNameValuePair(iToken, cKey)); // initial challenge pair
            params.add(new BasicNameValuePair("r", "ip")); // operation

            for(Iterator<BasicNameValuePair> i = pairs.iterator(); i.hasNext(); ){
                params.add(i.next());
            }


            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = null;

            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try {
                    InputStream instream = entity.getContent();
                    try {
                        // Use data gathered as a pre-set key
                        String ckt = IOUtils.toString(instream, "UTF-8");
                        Log.v("[LBS 1] raw output: ", ckt);
                        if(ckt.startsWith("chk-")){
                            // a key challenge has been introduced.
                            keyChallenge(ckt.substring(4));
                        }else{
                            return ckt;
                        }
                    } finally {
                        instream.close();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class AsyncQ implements Command {
        public Object passed=null;
        public LeaderboardGlobal LBS=null;
        public AsyncQ(){

        }

        public void execute(){
            LBS.doPostQuestion((QuestionTimeScore) passed);
        }
    }

    public static class AsyncA implements Command {
        public String passed="";
        public LeaderboardGlobal LBS=null;
        public AsyncA(){

        }

        public void execute(){
            LBS.setAccount(passed);
        }
    }

    public class AsyncKey implements Command {
        public int timeout=3000;

        public void execute()
        {
            if(!serverThere) return;
            try{
                httpclient = HttpClients.createDefault();
                httppost = new HttpPost(serverAddr);

                // Generate initial key:
                iToken = UUID.randomUUID().toString();

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<>(2);
                params.add(new BasicNameValuePair("i", iToken)); // initial token

                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                //Execute and get the response.
                HttpResponse response = null;

                response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try {
                        InputStream instream = entity.getContent();
                        try {
                            // Use data gathered as a pre-set key
                            lKey.add(cKey);
                            cKey = IOUtils.toString(instream, "UTF-8");
                            Log.v("[LBS 2] raw output: ", cKey);
                        } finally {
                            instream.close();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            timeout = 3000; // 1k * 100ms = 10kms ~= 30 seconds
            GameManager.getInstance().taskManager.add(new AsyncKey());
        }
    }

}
