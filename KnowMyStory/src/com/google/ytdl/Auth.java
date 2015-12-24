/*
 * Know My Story 
 */
package com.google.ytdl;

import java.io.IOException;
import java.util.List;

import android.content.Context;

import com.google.android.gms.common.Scopes;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTubeScopes;

public class Auth {

	// Register an API key here: https://code.google.com/apis/console
	public static final String KEY = "257458966745-o0ariu7n90g1bdqr32c0l1jfrp8bpta9.apps.googleusercontent.com";
	public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};

	public static final String REFRESH_TOKEN="1/r15R_1GGAhXHcgW1Se3vWY6aAp0iRv7gxHq8AxouXUUMEudVrK5jSpoR30zcRFq6";
	//public static final String REFRESH_TOKEN="1/NXQNY-GyLTOS1YjCq4C5ApoltiHXuwGFHnu0zAYBgswMEudVrK5jSpoR30zcRFq6";
	
	public static final String CLIENT_ID="713681317222-crm861r9v698fmcc3i6onoup1iniqnh1.apps.googleusercontent.com";
	
	
	//public static final String CLIENT_ID="184749014984-9ecdg72hp5qppvdpn8j59kb0ktkb52q0.apps.googleusercontent.com";

	public static final String CLIENT_SECRET="juHJ88K_8DSsyOl2C1zzJaua";
	//public static final String CLIENT_SECRET="df-g0NNr-Wht0jldTMykubvc";


	/**
	 * Define a global instance of the HTTP transport.
	 */
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/**
	 * Define a global instance of the JSON factory.
	 */
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/**
	 * Authorizes the installed application to access user's protected data.
	 *
	 * @param scopes              list of scopes needed to run youtube upload.
	 * @param credentialDatastore name of the credential datastore to cache OAuth tokens
	 */

	static Context ctx;
	public static Credential authorize(List<String> scopes, String credentialDatastore,Context mCtx) throws IOException {
		ctx=mCtx;

		TokenResponse response=new GoogleRefreshTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET).execute();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY)               
				.build()
				.setFromTokenResponse(response);		return credential;
	}
}
