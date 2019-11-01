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
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); 										// **Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; 										// **Change this, base API URL:  https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-home_timeline
	public static final String REST_CONSUMER_KEY = BuildConfig.CONSUMER_KEY;       								// **Change this inside apikey.properties
	public static final String REST_CONSUMER_SECRET = BuildConfig.CONSUMER_SECRET; 								// **Change this inside apikey.properties
	public static final String FALLBACK_URL =  																	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
			"https://codepath.github.io/android-rest-client-template/success.html";
	public static final String REST_CALLBACK_URL_TEMPLATE = 													// See https://developer.chrome.com/multidevice/android/intents
			"intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET,
				null,  																					// OAuth2 scope, null for OAuth1
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host), context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL)
		);
	}

	public void getInterestingnessList(JsonHttpResponseHandler handler){ 										// **CHANGE THIS. DEFINE METHODS for different API endpoints here
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		RequestParams params = new RequestParams();																// Can specify query string params directly or through RequestParams.
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint. Ex: getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body). Ex: RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    ex: client.get(apiUrl, params, handler);
	 *    ex: client.post(apiUrl, params, handler);
	 */
}



