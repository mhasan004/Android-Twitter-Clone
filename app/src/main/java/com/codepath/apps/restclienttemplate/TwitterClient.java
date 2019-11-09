package com.codepath.apps.restclienttemplate;
import android.content.Context;

import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
public class TwitterClient extends OAuthBaseClient
{
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); 										//***Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; 										//***Change this, base API URL:  https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-home_timeline
	public static final String REST_CONSUMER_KEY = BuildConfig.CONSUMER_KEY;       								//***Change this inside apikey.properties
	public static final String REST_CONSUMER_SECRET = BuildConfig.CONSUMER_SECRET; 								//***Change this inside apikey.properties
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html"; 																	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";
			// See https://developer.chrome.com/multidevice/android/intents
			//in twitter dev site, i said i want the application to go to "intent://" which is this

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				null,  																					// OAuth2 scope, null for OAuth1
				String.format(REST_CALLBACK_URL_TEMPLATE,
						context.getString(R.string.intent_host), context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL)
		);
	}

	public void getHomeTimeline(JsonHttpResponseHandler handler){ 										//***CHANGE THIS. DEFINE METHODS for different API endpoints here
		String apiUrl = getApiUrl("statuses/home_timeline.json");									//*** endpoint.  from the api url given in twitter documentation: https://api.twitter.com/1.1/statuses/home_timeline.json
		RequestParams params = new RequestParams();														// Can specify query string params directly or through RequestParams.
		params.put("count", 25);																		//*** Number of records to retrieve <- parameter from twitter doc (count <= 200)
		params.put("since_id", 1); 																		//*** returns ID greater than this number (used to get most recent tweets <- parameter from twitter doc (count <= 200)
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint. Ex: getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body). Ex: RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    ex: client.get(apiUrl, params, handler);
	 *    ex: client.post(apiUrl, params, handler);
	 */



	//P2v3 Copied "getHomeTimeline" to handle publishing tweets POST API:https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/post-statuses-update
	public void publishTweet(String tweetContent, JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");									// CHanging the API URL
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("status", tweetContent);															// Only got one parameter, the string we want to tweet out, which is the "tweetContent"
		client.post(apiUrl, params, "", handler);												// Making a post request now, had to add a "body" parameter, its empty for now
	}
}



