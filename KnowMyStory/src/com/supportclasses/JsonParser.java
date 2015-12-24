/*
 * Know My Story 
 */
package com.supportclasses;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class JsonParser {
	 
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
 
    public JsonParser() {
 
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
            List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                
                httpPost.setHeader(Constants.API_KEY,Constants.API_VALUE);
               
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader(Constants.API_KEY,Constants.API_VALUE);
             // add the JSON as a StringEntity
                //httpGet.setEntity(new StringEntity("language=en"));
 
                httpResponse = httpClient.execute(httpGet);
 
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
 
    }
    public String getWebDataPostRequestOnline(String keywrd) 
	{
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try 
		{
			StringBuilder sb = new StringBuilder(Constants.BIBLE_ONLINE_URL + Constants.BIBLE_DBT_KEY+"&dam_id=ENGESVO2ET");
			sb.append("&query=" +keywrd+"&v=2");
			System.out.println("url "+sb.toString());
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(
					conn.getInputStream());
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) 
			{
				jsonResults.append(buff, 0, read);
			}
		}
		catch (MalformedURLException e)
		{

		}
		catch (IOException e)
		{

		} 
		finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
		return jsonResults.toString();
	}
    public String getWebDataPostRequestURL(String dam_id,String book_id,String ch_id,String v_id) 
	{
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try 
		{
			StringBuilder sb = new StringBuilder(Constants.BIBLE_VERSE_URL + Constants.BIBLE_DBT_KEY+"&dam_id="+dam_id);
			sb.append("&book_id=" +book_id);
			sb.append("&chapter_id=" +ch_id);
			sb.append("&verse_start="+v_id+"&v=2");
			System.out.println("url "+sb.toString());
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000); 
			InputStreamReader in = new InputStreamReader(
					conn.getInputStream());
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) 
			{
				jsonResults.append(buff, 0, read);
			}
		}
		catch (MalformedURLException e)
		{

		}
		catch (IOException e)
		{

		} 
		finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
		return jsonResults.toString();
	}

}
