package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
// This is a class to describe WHO the user is. The twitter API returns JSOn where "user" is a JSOn object with information such as name, screenName, profilepic, etc
// This class creates that User object basically. What is their name? their screenName? ProfilePic? We create this from the twitter API.
// ***Basically: Turning the "User" JSON object to a java object so we can use it! To make a "User" java object we obviously need to make a class:
public class User {
    public String name;
    public String screenName;
    public String profileImageURL;

    public static User fromJsonObj_returnUserObj(JSONObject jsonObject) throws JSONException {
        User user = new User();                                     //initialise new java object
        user.name = jsonObject.getString("name");
        user.screenName =  jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");
        return user;
    }
}
