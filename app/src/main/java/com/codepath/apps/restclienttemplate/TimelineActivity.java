package com.codepath.apps.restclienttemplate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    public static final String TAG = "hasan";
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;                                                                             // Remember: the API return a JSOn object that is basically a tweet. We made a java "Tweet" class that will hold the fields we want. Since we will be getting a ton of tweet objects, we made a list of tweet objects
    TweetsAdapter adapter;

    //1) making an instance of the twitter client
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);                                            //2) Get the client + RecyclerView. Set the RecyclerView layout to LinearLayout + set adapter
        rvTweets = findViewById(R.id.rvTweets);                                                           // a) FInd the RecyclerView
        tweets = new ArrayList<>();                                                                     // b) Initialize the list of tweets and adapter
        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));                               // c) RecyclerView setup: layout manager and adapter
        rvTweets.setAdapter(adapter);                                                                       //pass in the adapter we just made
        populateHomeTimeLine();                                                                     //3)
    }

    private void populateHomeTimeLine()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler() {                                      //3) Get the JSON object the API send to us?
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess (TimelineActivity): " + json.toString());
                JSONArray jsonArray =  json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray_returnListOfTweetObjects(jsonArray));              // Using the function from "tweet.java" to turn JSON array to a list of Tweet objects and adding it to the "tweet" List we made
                    adapter.notifyDataSetChanged();                                                      //*****
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception!!! (TimelineActivity)");
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "onFailure  (from TimelineActivity.java): " + response , throwable);
            }
        });
    }
}
