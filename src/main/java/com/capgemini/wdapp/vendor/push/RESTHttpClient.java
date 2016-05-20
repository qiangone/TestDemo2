package com.capgemini.wdapp.vendor.push;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.util.JsonUtil;
import com.google.gson.Gson;


public class RESTHttpClient{
	private static Logger logger = LoggerFactory.getLogger(RESTHttpClient.class);
	
	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	protected HttpClient httpClient;
	
	private String baseUrl;
	
	private String contentType;
	
	//private Gson gson;
	
	private String tokenFieldName;
	
	private String token;

	public RESTHttpClient(String baseUrl) {
		this(baseUrl, DEFAULT_CONTENT_TYPE);
	}	
	
	public RESTHttpClient(String baseUrl, String contentType) {
		this.baseUrl = baseUrl;
		this.contentType = contentType;
		
		//gson = new Gson();
		connect();
	}
	
	public void setToken(String tokenFieldName, String token) {
		this.tokenFieldName = tokenFieldName;
		this.token = token;
	}

	public void finalizAfterClass() throws Exception {
		disConnect();
	}
	
	public void connect() {
		if(null == httpClient) {
			httpClient = new DefaultHttpClient();
		}
	}
	
	public void disConnect() {
		try {
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WDResponse doPost(String subPath, InputStream inputStream) {
		String url = getRequestUrl(subPath);
		System.out.println(url);
		logger.debug("Request URL:" + url);
		HttpPost post = new HttpPost(url);
		InputStreamEntity entity = new InputStreamEntity(inputStream, -1);
		entity.setContentType(contentType + "; charset=" + HTTP.UTF_8);
		post.setEntity(entity);

		return executePost(post);
	}

	public WDResponse doPost(String subPath, Map<String, String> params)
			throws IOException {
		String url = getRequestUrl(subPath);

		HttpPost post = new HttpPost(url);

		List<NameValuePair> attrs = new ArrayList<NameValuePair>();

		for (String key : params.keySet()) {
			String value = (String) params.get(key);
			attrs.add(new BasicNameValuePair(key, value));
		}
		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(attrs, HTTP.UTF_8);
		entity.setContentType(contentType + "; charset=" + HTTP.UTF_8);
		post.setEntity(entity);


		return executePost(post);
	}

	//Create Folder /Create Space
	public WDResponse doPost(String subPath, String postBody) throws IOException {
		String url = getRequestUrl(subPath);
		System.out.println("POST body: \n" + postBody);
		HttpPost post = new HttpPost(url);
		if(postBody != null && postBody.length() > 0) {
			StringEntity entity = new StringEntity(postBody,"UTF-8");
			post.setEntity(entity);
		}
		post.setHeader("content-type", "application/json");

		return executePost(post);
	}
	
	public String doCommonPost(String url, String postBody) throws IOException {
		System.out.println("POST body: \n" + postBody);
		HttpPost post = new HttpPost(url);
		if(postBody != null && postBody.length() > 0) {
			StringEntity entity = new StringEntity(postBody,"UTF-8");
			post.setEntity(entity);
		}
		post.setHeader("content-type", "application/json");

		HttpResponse response = null;
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return response.getParams().toString();

		//return response.toString();
	}
	
	public WDResponse doPost(String subPath, Object postBody) throws IOException {
		String url = getRequestUrl(subPath);
		System.out.println("POST body: \n" + postBody);
		HttpPost post = new HttpPost(url);
		if(postBody != null) {
			String a = JsonUtil.toJson(postBody);
			StringEntity entity = new StringEntity(JsonUtil.toJson(postBody),"utf-8");
			post.setEntity(entity);
		}
		post.setHeader("Content-Type", "application/json;charset=utf-8");

		return executePost(post);
	}
	
	//Create Folder /Create Space
	public WDResponse doPost(String subPath) throws IOException {
		return doPost(subPath, "");
	}
	
	public WDResponse doDelete(String subPath) throws IOException {
		String url = getRequestUrl(subPath);

		HttpDelete delete = new HttpDelete(url);
		return executeDelete(delete);

	}

	public WDResponse doPut(String subPath, String putBody) throws IOException {
		String url = getRequestUrl(subPath);
		System.out.println("PUT body: \n" + putBody);

		HttpPut put = new HttpPut(url);
		if(putBody != null && putBody.length() > 0) {
			StringEntity entity = new StringEntity(putBody,"UTF-8");
			put.setEntity(entity);
		}
		put.setHeader("content-type", "application/json");
		// put.setDoAuthentication(true);
		return executePut(put);
	}
	
	public WDResponse doPut(String subPath) throws IOException {
		String url = getRequestUrl(subPath);
		
		HttpPut put = new HttpPut(url);
		// put.setDoAuthentication(true);
		return executePut(put);
	}

	public WDResponse doGet(String subPath)  {
		String url = getRequestUrl(subPath);

		HttpGet get = new HttpGet(url);
		return executeGet(get);
	}
	
	private WDResponse processHTTPResponse(HttpResponse response) {
		WDResponse result = null;
		if (response != null) {
			try {
				logger.debug(
						" return code is: "
								+ response.getStatusLine().getStatusCode());

				String respBody = getResponseBody(response);
				logger.debug(" Response Body: " + respBody);

				result = ResponseUtil.apiSuccess(respBody, "aaaa");
			} catch (IllegalStateException e) {
				logger.error(e.getMessage());
			} 
		}

		return result;
	}

	private WDResponse executePost(HttpPost post) {
		HttpResponse response = null;
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return processHTTPResponse(response);
	}

	private WDResponse executeGet(HttpGet get) {
		HttpResponse response = null;
		try {
			response = httpClient.execute(get);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return processHTTPResponse(response);

	}

	private WDResponse executeDelete(HttpDelete delete) {
		HttpResponse response = null;
		try {
			response = httpClient.execute(delete);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return processHTTPResponse(response);
	}

	private WDResponse executePut(HttpPut put) {
		HttpResponse response = null;
		try {
			response = httpClient.execute(put);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return processHTTPResponse(response);
	}

	public HttpClient setupClient(String username, String password) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope("somehost", AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(username, password));

		return httpClient;
	}


	private String getResponseBody(HttpResponse response) {
		String respBody = null;
		InputStream responseStream;
		try {
			responseStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(responseStream));
			String responseLine = "";
			StringBuffer buf = new StringBuffer();
			while ((responseLine = reader.readLine()) != null) {
				buf.append(responseLine);
			}
			respBody = buf.toString();
			
			logger.debug("Response : \n" + respBody);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return respBody;
	}
	
	private String getRequestUrl(String subUrl) {
		StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(subUrl);
		if(null != token) {
			//sb.append("?").append(tokenFieldName).append("=").append(token);
		}
		
		String url = sb.toString();
		System.out.println("Requesting URL:" + url);
		logger.info("Requesting URL:" + url);
		
		return url;
	}

}