package com.netcomps.oauth_example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OAuthMain extends Activity {

	private SharedPreferences prefs;
	TextView console;
	Button launchOauth;
	ArrayList<MailContacts> mList;
	ListView mListView;
	EditText inputSearch;
	MyAdapter adapter;
	Button get;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		mListView = (ListView) findViewById(R.id.list_contacts);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		get = (Button) findViewById(R.id.get);
		launchOauth = (Button) findViewById(R.id.button_start_oauth);
		// Button clearCredentials = (Button)
		// findViewById(R.id.button_delete_tokens);
		// Button getContacts = (Button) findViewById(R.id.button_get_contacts);

		launchOauth.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						RequestTokenActivity.class));

			}
		});
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		get.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (adapter != null) {

					ArrayList<MailContacts> mArrayGmailList = adapter
							.getCheckedItems();

					String gmails = "";
					for (int i = 0; i < mArrayGmailList.size(); i++) {

						gmails += mArrayGmailList.get(i).getContactEmail()
								+ "\n";
					}
					Toast.makeText(getApplicationContext(), gmails,
							Toast.LENGTH_LONG).show();
				}

			}
		});
		mListView.setTextFilterEnabled(true);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out
						.println("Text [" + s + "] - Start [" + start
								+ "] - Before [" + before + "] - Count ["
								+ count + "]");
				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					adapter.resetData();
				}

				adapter.getFilter().filter(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void getContacts() {
		mList = new ArrayList<MailContacts>();
		String jsonOutput = "";
		try {
			jsonOutput = makeSecuredReq(C.GET_CONTACTS_FROM_GOOGLE_REQUEST,
					getConsumer(this.prefs));

			JSONObject jsonResponse = new JSONObject(jsonOutput);
			JSONObject m = (JSONObject) jsonResponse.get("feed");
			// This is for owner email and his name
			JSONArray authorEmail = m.getJSONArray("author");
			JSONObject ownerNameObject = authorEmail.getJSONObject(0)
					.getJSONObject("name");
			JSONObject ownerMailObject = authorEmail.getJSONObject(0)
					.getJSONObject("email");
			// String ownerName=ownerNameObject.getString("$t");
			// String ownerMail = ownerMailObject.getString("$t");
			System.out.println(ownerNameObject.getString("$t") + " "
					+ ownerMailObject.getString("$t"));
			;
			// This is for all emails and contact names
			JSONArray entries = (JSONArray) m.getJSONArray("entry");

			System.out.println(entries.length());

			for (int i = 0; i < entries.length(); i++) {
				JSONObject entry = entries.getJSONObject(i);
				if (entry.has("gd$email")) {
					JSONArray email = entry.getJSONArray("gd$email");
					JSONObject name = entry.getJSONObject("title");
					if (email.getJSONObject(0).getString("address") != null
							&& email.getJSONObject(0).getString("address")
									.length() > 0) {
						// && name.getString("$t")!=null &&
						// name.getString("$t").length()>0
						MailContacts mContact = new MailContacts();
						mContact.setContactEmail(email.getJSONObject(0)
								.getString("address").toString());
						mContact.setContactName(name.getString("$t"));
						mList.add(mContact);

					}
				}

			}

			displayList();

		} catch (Exception e) {
			Log.e(C.TAG, "Error executing request", e);
			console.setText("Error retrieving contacts : " + jsonOutput);
		}
	}

	public class CustomComparator implements Comparator<MailContacts> {

		@Override
		public int compare(MailContacts arg0, MailContacts arg1) {
			// TODO Auto-generated method stub
			return arg0.getContactEmail().compareTo(arg1.getContactEmail());
		}
	}

	private void displayList() {
		// TODO Auto-generated method stub
		Collections.sort(mList, new CustomComparator());

		adapter = new MyAdapter(getApplicationContext(), mList);
		mListView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isOAuthSuccessful()) {
			// OAuth successful, try getting the contacts
			// console.setText("OAuth successful, try getting the contacts");

			launchOauth.setVisibility(View.GONE);

			getContacts();

		} else {
			console = new TextView(this);

			console.setText("OAuth failed, no tokens, Click on the Do OAuth Button.");
		}
	}

	/**
	 * private void clearCredentials() { SharedPreferences prefs =
	 * PreferenceManager.getDefaultSharedPreferences(this); final Editor edit =
	 * prefs.edit(); edit.remove(OAuth.OAUTH_TOKEN);
	 * edit.remove(OAuth.OAUTH_TOKEN_SECRET); edit.commit(); }
	 */

	private boolean isOAuthSuccessful() {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, null);
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, null);
		if (token != null && secret != null)
			return true;
		else
			return false;
	}

	private OAuthConsumer getConsumer(SharedPreferences prefs) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(C.CONSUMER_KEY,
				C.CONSUMER_SECRET);
		consumer.setTokenWithSecret(token, secret);
		return consumer;
	}

	private String makeSecuredReq(String url, OAuthConsumer consumer)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		Log.i(C.TAG, "Requesting URL : " + url);
		consumer.sign(request);
		HttpResponse response = httpclient.execute(request);
		Log.i(C.TAG, "Statusline : " + response.getStatusLine());
		InputStream data = response.getEntity().getContent();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(data));
		String responeLine;
		StringBuilder responseBuilder = new StringBuilder();
		while ((responeLine = bufferedReader.readLine()) != null) {
			responseBuilder.append(responeLine);
		}
		Log.i(C.TAG, "Response : " + responseBuilder.toString());
		return responseBuilder.toString();
	}

}
/**
 * 
 */

