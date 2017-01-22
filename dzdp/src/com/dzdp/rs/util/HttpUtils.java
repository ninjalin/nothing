package com.dzdp.rs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtils {

	/**
	 * POST METHOD
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String invokePostMethod(String url, Map<String, String> map) throws IOException{
		InputStream inputStream = null;
		BufferedReader br = null;
		StringBuffer stringBuffer = null;
		HttpClient httpClient = null;
		PostMethod methodPost = null;
		try {
			methodPost = new PostMethod(url);
			Set<String> key = map.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				methodPost.addParameter(s, map.get(s));
			}
			httpClient = new HttpClient();
			// 设置连接超时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			// 设置获取数据时间
			methodPost.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			methodPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
					10000);
			DefaultHttpParams.getDefaultParams().setParameter(
					"http.protocol.cookie-policy",
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(methodPost);
			inputStream = methodPost.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			stringBuffer = new StringBuffer();
			String responsePost = "";
			while ((responsePost = br.readLine()) != null) {
				stringBuffer.append(responsePost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (br != null) {
				br.close();
			}
			if (methodPost != null) {
				methodPost.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * GET METHOD
	 * 
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String invokeGetMethod(String url) throws HttpException,
			IOException {
		String responseGet = "";
		InputStream inputStream = null;
		GetMethod getMethod = null;
		HttpClient httpClient = null;
		BufferedReader br = null;
		StringBuffer stringBuffer = null;
		try {
			getMethod = new GetMethod(url);
			httpClient = new HttpClient();
			// 设置连接超时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			// 设置获取数据时间
			getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
					10000);
			
			DefaultHttpParams.getDefaultParams().setParameter(
					"http.protocol.cookie-policy",
					CookiePolicy.BROWSER_COMPATIBILITY);
			getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
			httpClient.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			stringBuffer = new StringBuffer();
			while ((responseGet = br.readLine()) != null) {
				stringBuffer.append(responseGet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (br != null) {
				br.close();
			}
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
		return stringBuffer.toString();
	}

}
