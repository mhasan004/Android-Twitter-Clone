package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;

    public static Tweet fromJsonObj_returnTweetObj(JSONObject jsonObject) throws JSONException {    //***SUMMARY*** The twitter response gives us an JSOn response (Json Object) -> made a "Tweet" class to hold specific fields from that response (representing JSON object by a java object). Body and Create are simple key-val pairs and can get with jsonObject.getString(). *** BUT: the "user" filed is an object itself. So I made a "User" class to represent the User JSON object by a java object!
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");                                           //the twitter api response will give u a lot of json objects which are the tweets (see doc)
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJsonObj_returnUserObj(jsonObject.getJSONObject("user"));              //in the response there is field called "user" that itself is an json object.
                                                                                                    //*** SMART SOLUTION: "user' is a "User" json object. I will make a "User" model/class that will have a method that will take in a JSON object and return an java object!
        return tweet;
    }

    public static List<Tweet> fromJsonArray_returnListOfTweetObjects(JSONArray jsonArray) throws JSONException {             //***a function that returns a List of Tweet objects.
        List<Tweet> tweets = new ArrayList<>();                                                     // initialize a new List that is an array
        for (int i=0; i < jsonArray.length(); ++i){
            Log.i("hasan", "          Making List Of Tweets: JSONObj "+i+" = "+jsonArray.getJSONObject(i));
            tweets.add(fromJsonObj_returnTweetObj(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }


}
