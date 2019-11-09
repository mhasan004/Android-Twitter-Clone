package com.codepath.apps.restclienttemplate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
    SwipeRefreshLayout swipeContainer;

    //1) making an instance of the twitter client
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApp.getRestClient(this);                                            //2) Get the client + RecyclerView. Set the RecyclerView layout to LinearLayout + set adapter

        swipeContainer = findViewById(R.id.swipeContainer);                                             // *(Pull of Refresh)* this wraps the recycler view. it tells when i swip screen. will se to refresh
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,                        // *(Pull of Refresh)* Changing the loading circle colors when i pull down to refresh
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {                // *(Pull of Refresh)* see the "Pull of Refresh" Guide
            @Override
            public void onRefresh() {
                Log.e(TAG, "Fetching New Data (TimelineActivity)");
                populateHomeTimeLine();
            }
        });

        rvTweets = findViewById(R.id.rvTweets);                                                         // a) Find the RecyclerView
        tweets = new ArrayList<>();                                                                     // b) Initialize the list of tweets and adapter
        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));                               // c) RecyclerView setup: layout manager and adapter
        rvTweets.setAdapter(adapter);                                                                   // pass in the adapter we just made
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
                    Log.i(TAG, "Adding Tweet onSuccess (TimelineActivity");
                    //tweets.addAll(Tweet.fromJsonArray_returnListOfTweetObjects(jsonArray));              // Using the function from "tweet.java" to turn JSON array to a list of Tweet objects and adding it to the "tweet" List we made
                    adapter.clear();                                                                // *(Pull of Refresh)* When i swipe down, i need to clear the adapter and the add the tweets
                    adapter.addAll(Tweet.fromJsonArray_returnListOfTweetObjects(jsonArray));         // *(Pull of Refresh)* after clearing adapter, add the tweets
                    adapter.notifyDataSetChanged();                                                      // need to notify adapter of changes
                    swipeContainer.setRefreshing(false);                                            // *(Pull of Refresh)* Refreshing is done so no need to show the loading indicator any more!
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

    // These Two functions are for the Menu ICON. Basically what happens when i click on the menu icon?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                                 // P2: Action Bar Menu: inflate "menu_main" xml file from the menu folder
        getMenuInflater().inflate(R.menu.menu_main, menu);                                                  // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                                           // P2: Action Bar Menu: Setting On CLick Listeners to the "compose" menu icon
        if (item.getItemId() == R.id.compose){                                                              // What to do when i click on the menu icon?
            //compose icon has been selected
            Toast.makeText(this,"Compose!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComposeActivity.class);                         // navigate to the new activity
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);                                                   // return true to compose the menu (process the click) or false to normal menu operation. See the onOptionItemSelected function def
    }
}
