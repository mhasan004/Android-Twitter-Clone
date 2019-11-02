package com.codepath.apps.restclienttemplate;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    public static final String TAG = "hasan";
    TwitterClient client;                                                                           //1) making an instance of the twitter client
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);                              //2)
        populateHomeTimeLine();
    }

    private void populateHomeTimeLine()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler() {                                      //3)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess (from TimelineActivity.java): " + json.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "onFailure  (from TimelineActivity.java): " + response , throwable);
            }
        });
    }
}
