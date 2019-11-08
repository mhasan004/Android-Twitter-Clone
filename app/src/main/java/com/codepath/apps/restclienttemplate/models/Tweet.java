package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
// This is a class to turn that Tweet the user made into a tweet object. What the Twitter returns is basically a tweet object (this class). What user tweeted this, tweet body, when was it tweeted?
// The "User" property of this "Tweet" object is kind of special. Why? The Twitter API returns a "user" which contains the name, screenName, and profile pic of the person who tweeted
// ***So need to make a "User" java object to represent that JSOn "User object"....see User.java
public class Tweet {
    public User user;                                                                                                       //***Made this class. This is a User object. User object contains the the name, screenName, and the person's Profile pic from the API (turns JSON object to a java object-which is Users- basically)
    public String body;
    public String createdAt;

    // 1) Construction the Tweet from the API by turning extracting the fields we want from the JSON data and inserting to to this Tweet object we made!
    public static Tweet fromJsonObj_returnTweetObj(JSONObject jsonObject) throws JSONException {                            //***SUMMARY*** The twitter response gives us an JSOn response (Json Object) -> made a "Tweet" class to hold specific fields from that response (representing JSON object by a java object). Body and Create are simple key-val pairs and can get with jsonObject.getString(). *** BUT: the "user" filed is an object itself. So I made a "User" class to represent the User JSON object by a java object!
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");                                                                   //the twitter api response will give u a lot of json objects which are the tweets (see doc)
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJsonObj_returnUserObj(jsonObject.getJSONObject("user"));                                      //in the response there is field called "user" that itself is an json object.
                                                                                                                            //*** SMART SOLUTION: "user' is a "User" json object. I will make a "User" model/class that will have a method that will take in a JSON object and return an java object!
        return tweet;
    }

    // 2) We aren't going be dealing with just one tweet but many tweets -> so we will get back an array of JSON object (arrow of tweets basically)
    // SO we can loop through that JSON ARRAY of tweets and use the function we made to convert them into tweet objects!
    public static List<Tweet> fromJsonArray_returnListOfTweetObjects(JSONArray jsonArray) throws JSONException {            //***a function that returns a List of Tweet objects.
        List<Tweet> tweets = new ArrayList<>();                                                                             // initialize a new List that is an array
        for (int i=0; i < jsonArray.length(); ++i){
            Log.i("hasan", "          Making List Of Tweets: JSONObj "+i+" = "+jsonArray.getJSONObject(i));
            tweets.add(fromJsonObj_returnTweetObj(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }


}
