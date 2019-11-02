package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {                                                 //this is a
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
