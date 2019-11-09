package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 140;
    public static final String TAG = "hasan";
    EditText etCompose;
    Button btnTweet;
    TwitterClient client;                                                                           // p2v3 when im posting tweet using the APi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btn_tweet);
        client = TwitterApp.getRestClient(this);                                            // p2v3: getting an instance to the rest client. references to this (the ComposeActivity)

        //1) Set a on click listener on the button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be empty :(", Toast.LENGTH_LONG).show();
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is over the 140 character limit :(", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(ComposeActivity.this, "Tweet Content", Toast.LENGTH_LONG).show();
                //2) After i click the button make a API call to twitter to publish tweet (if its under 140char limit)
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {             // the JSON we get back is a JSON object -> can turn to Tweet model/object using a method we made already
                        Log.i(TAG, "onSuccess to publish tweet (ComposeActivity)");
                        try {
                            Tweet tweet = Tweet.fromJsonObj_returnTweetObj(json.jsonObject);
                            Log.i(TAG, "published tweet says: "+ tweet.body);
                            Intent intent = new Intent();                                           // ***** a) When TimelineActivity opened this this an intent, it wanted a response. Here im making a new intent to respond back to TimelineActivity
                            intent.putExtra("tweet", Parcels.wrap(tweet));                   // ***** b) passing in the data to the intent with the code "tweet". NEED TO USE PARCELER library TO PASS THE DATA (ANNOYING). Making the tweet model into a parcelable object that getParcelableExtra() cna get
                            setResult(RESULT_OK, intent);                                           // ***** c) setting th result code to RESULT_OK. In the TimelineActivity, if i got this intent correctly, the RESULT_CODE over there will be the same
                            finish();                                                               // ***** d) finishing the intent feedback
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet (ComposeActivity)", throwable);
                    }
                });
            }
        });
    }
}
