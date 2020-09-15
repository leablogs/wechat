package com.leablogs.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpClientUtil {
	private static final String ENCODING = "UTF-8";
	private static final int CONNECT_TIMEOUT = 6000;
	private static final int SOCKET_TIMEOUT = 6000;

	public static HttpClientResultUtil doGet(String url) throws IOException {
		return doGet(url, null, null);
	}

	public static HttpClientResultUtil doGet(String url, Map<String, String> params) throws IOException {
		return doGet(url, null, params);
	}

	private static HttpClientResultUtil doGet(String url, Map<String, String> headers, Map<String, String> params)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if (params != null) {
				Set<Entry<String, String>> entries = params.entrySet();
				for (Entry<String, String> entry : entries) {
					uriBuilder.setParameter(entry.getKey(), entry.getValue());
				}
			}
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
					.setSocketTimeout(SOCKET_TIMEOUT).build();
			httpGet.setConfig(requestConfig);

			packageHeader(headers, httpGet);
			CloseableHttpResponse httpResponse = null;
			try {
				return getHttpClientResult(httpResponse, httpClient, httpGet);
			} finally {
				release(httpResponse, httpClient);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static HttpClientResultUtil doPost(String url) throws Exception {
		return doPost(url, null, null);
	}

	public static HttpClientResultUtil doPost(String url, Map<String, String> params) throws IOException {
		return doPost(url, null, params);
	}

	public static HttpClientResultUtil doPost(String url, Map<String, String> headers, Map<String, String> params)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		packageHeader(headers, httpPost);
		packageParam(params, httpPost);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			release(httpResponse, httpClient);
		}
		return null;
	}

	public static HttpClientResultUtil doPut(String url) {
		return doPut(url);
	}

	public static HttpClientResultUtil doPut(String url, Map<String, String> params)
			throws UnsupportedEncodingException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);
		packageParam(params, httpPut);
		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static HttpClientResultUtil doDelete(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelete.setConfig(requestConfig);
		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpDelete);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	public static HttpClientResultUtil doDelete(String url, Map<String, String> params) throws IOException {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		params.put("_method", "delete");
		return doPost(url, params);
	}

	public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
		}
	}

	private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	private static HttpClientResultUtil getHttpClientResult(CloseableHttpResponse httpResponse,
			CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws ClientProtocolException, IOException {
		httpResponse = httpClient.execute(httpMethod);
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			JsonObject jsonParser = null;
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
				jsonParser = JsonParser.parseString(content).getAsJsonObject();
			}

			return new HttpClientResultUtil(httpResponse.getStatusLine().getStatusCode(), "请求成功", jsonParser);
		}
		return new HttpClientResultUtil(httpResponse.getStatusLine().getStatusCode(), "请求失败 ",
				HttpStatus.SC_INTERNAL_SERVER_ERROR);

	}

	private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMehod) {
		if (headers != null) {
			Set<Entry<String, String>> entrySet = headers.entrySet();
			for (Entry<String, String> entry : entrySet) {
				httpMehod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}
}
