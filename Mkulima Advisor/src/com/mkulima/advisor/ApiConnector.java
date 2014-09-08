package com.mkulima.advisor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;


public class ApiConnector 
{
	public final static int GET = 1;
	public final static int POST = 2;

	private HttpClient client;
	private HttpPost postRequest;
	private HttpGet getRequest;
	public HttpResponse response;
	private String accessTokens;
	private Context context;
	private int code;
	
	public ApiConnector(Context contexts)
	{
		this.context = contexts;
	}
	
	public String GetAllCustomers(String url, int method, List<NameValuePair> list) 
	{
		HttpEntity entity = null;
		String data = null;
		
		try
		{
			client = new DefaultHttpClient(); 
			
			if(method == POST)
			{
				postRequest = new HttpPost(url);
				
				if(list != null)
				{
					postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
					postRequest.setEntity(new UrlEncodedFormEntity(list));
				}
				else
				{
					postRequest.setHeader("Content-Type", "application/json");

				}
				
				response = client.execute(postRequest);
				
			}
			else if (method == GET)
			{
				getRequest = new HttpGet(url);
				getRequest.setHeader("Content-Type", "application/json");
		
				response = client.execute(getRequest);
			}
			obtainErrorCode(response);
			
			entity = response.getEntity();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		// Convert HttpEntity into JSON Array
		
		if (entity != null) 
		{
			try 
			{
				data = EntityUtils.toString(entity);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		return data;
	}
	
	public void sendJSONObjectToServer(String url, JSONObject jsonServerPost) 
	{
	
		try
		{
			client = new DefaultHttpClient();
			
			postRequest = new HttpPost(url);
			postRequest.setEntity(new ByteArrayEntity(jsonServerPost.toString().getBytes("UTF8")));
			postRequest.setHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) 
			{
				//Toast.makeText(this, "Successfully uploaded to the server", Toast.LENGTH_SHORT).show();
			}
			
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
		}
	}
	
	private void obtainErrorCode(HttpResponse rspnse) 
	{
		int errCode = rspnse.getStatusLine().getStatusCode();
		
		setErrorCodes(errCode);
	}

	public void setErrorCodes(int errorCode)
	{
		code = errorCode;
	}
	public int getErrorCodes()
	{
		return code;
	}
}