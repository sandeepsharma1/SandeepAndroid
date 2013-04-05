package com.netcomps.oauth_example;


public class C {
	
	public static final String TAG = "OAuthExample";

	public static final String CONSUMER_KEY 	= "anonymous";
	public static final String CONSUMER_SECRET 	= "anonymous";

	public static final String SCOPE 			= "https://www.google.com/m8/feeds/";
	public static final String REQUEST_URL 		= "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL 		= "https://www.google.com/accounts/OAuthGetAccessToken";  
	public static final String AUTHORIZE_URL 	= "https://www.google.com/accounts/OAuthAuthorizeToken";
	
	public static final String GET_CONTACTS_FROM_GOOGLE_REQUEST = "https://www.google.com/m8/feeds/contacts/default/full?alt=json";
    //public static final String GET_CONTACTS_FROM_GOOGLE_REQUEST = "https://www.google.com/m8/feeds/contacts/liz%40gmail.com/max-results=json";
	
	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "oauth-example";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	public static final String	APP_NAME                = "OAuthExample";

}
