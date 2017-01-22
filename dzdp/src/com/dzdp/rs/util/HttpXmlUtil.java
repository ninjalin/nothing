package com.dzdp.rs.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dzdp.rs.conf.ConfigManager;



public class HttpXmlUtil {	
	public static String userNameTrade = ""; // 行业接口账号
	public static String passwordTrade = ""; // 行业密码	
	public static String userNameMarket = ""; //营销接口账号
	public static String passwordMarket = ""; //营销 密码	
    public static String url=""; //三网通使用地址
   
    static{   	
    	try
    	{
	    	userNameTrade = ConfigManager.getInstance().getText("/Mobile/http/userNameTrade");
	    	passwordTrade = ConfigManager.getInstance().getText("/Mobile/http/passwordTrade");
	    	userNameMarket = ConfigManager.getInstance().getText("/Mobile/http/userNameMarket");
	    	passwordMarket = ConfigManager.getInstance().getText("/Mobile/http/passwordMarket");
	    	url = ConfigManager.getInstance().getText("/Mobile/http/url");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
     // 发送短信行业类
	/**
	 * 发送短信方法使用document 方法封装XML字符串
	 */
	public static String sendTrade(String msgid,String phone,String content,String sign,String subcode,String sendtime) {
		String posturl = url+"/http/sms/Submit";
		Map<String, String> params = new LinkedHashMap<String, String>();
		HttpXmlUtil docXml = new HttpXmlUtil();
		String message = docXml.DocXml(userNameTrade, MD5Encode(passwordTrade), msgid, phone, content, sign, subcode, sendtime);
		params.put("message", message);
		String resp = doPost(posturl, params);
		return resp;
	}
	// 发送短信营销类
	/**
	 * 发送短信方法使用document 方法封装XML字符串
	 */
	public static String sendMarket(String msgid,String phone,String content,String sign,String subcode,String sendtime) {
		String posturl = url+"/http/sms/Submit";
		Map<String, String> params = new LinkedHashMap<String, String>();
		HttpXmlUtil docXml = new HttpXmlUtil();
		String message=docXml.DocXml(userNameMarket, MD5Encode(passwordMarket), msgid, phone, content, sign, subcode, sendtime);
		params.put("message", message);
		String resp = doPost(posturl, params);
		return resp;
	}
    
	 

	// 获取上行
	public static String getSms() {
		String posturl = url+"/http/sms/Deliver";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
				+ userNameTrade
				+ "</account>"
				+ "<password>"
				+ MD5Encode(passwordTrade)
				+ "</password></message>";
		params.put("message", message);
		String resp = doPost(posturl, params);
		return resp;
	}

	// 状态报告行业
	public static String getReportTrade() {
		String posturl = url+"/http/sms/Report";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userNameTrade + "</account>" + "<password>"
				+ MD5Encode(passwordTrade) + "</password>"
				+ "<msgid></msgid><phone></phone></message>";
		params.put("message", message);
		String resp = doPost(posturl, params);
		return resp;
	}
	// 状态报告营销
	public static String getReportMarket() {
		String posturl = url+"/http/sms/Report";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userNameMarket + "</account>" + "<password>"
				+ MD5Encode(passwordMarket) + "</password>"
				+ "<msgid></msgid><phone></phone></message>";
		params.put("message", message);
		String resp = doPost(posturl, params);
		return resp;
	}
	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	private static String doPost(String url, Map<String, String> params) {
		String response = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 设置Post数据
		if (!params.isEmpty()) {
			int i = 0;
			NameValuePair[] data = new NameValuePair[params.size()];
			for (Entry<String, String> entry : params.entrySet()) {
				data[i] = new NameValuePair(entry.getKey(), entry.getValue());
				i++;
			}
			postMethod.setRequestBody(data);
		}
		try {
			client.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return response;
	}

	/**
	 * 使用document 对象封装XML
	 */
	 private  String DocXml(String userName,String pwd,String msgid,String  phone,String contents,String sign,String  subcode,String sendtime) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element message = doc.addElement("message");
		Element account = message.addElement("account");
		account.setText(userName);
		Element password = message.addElement("password");
		password.setText(pwd);
		Element msgid1 = message.addElement("msgid");
		msgid1.setText(msgid);
		Element phones = message.addElement("phones");
		phones.setText(phone);
		Element content = message.addElement("content");
		content.setText(contents);
		Element sign1 = message.addElement("sign");
		sign1.setText(sign);
		Element subcode1 = message.addElement("subcode");
		subcode1.setText(subcode);
		Element sendtime1 = message.addElement("sendtime");
		sendtime1.setText(sendtime);
		return message.asXML();
		}
    // MD5加密函数
	private static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	private static final String byte2hexString(byte[] bytes) {
		StringBuffer bf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				bf.append("0");
			}
			bf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return bf.toString();
	}



	
	public static void main(String[] args) throws Exception {
		System.out.println(sendTrade("","18612598460","奥淳公众服务中心是您身边的服务管家，感谢您咨询968111。","","",""));
		//		
//		// 发送短信
//		System.out.println("*************发送行业短信*************");
//	//	System.out.println(sendTrade("1113","15910389164","奥淳公众服务中心是您身边的服务管家，感谢您咨询968111。","","",""));
//	//	System.out.println(sendTrade("1114","15910389164","奥淳公众服务中心是您身边的服务管家，感谢您咨询968111。","","",""));
//		
//		System.out.println("*************发送营销短信*************");
//		//sendMarket("1112","15910389164","感谢您拨打968111，家有急事，难事，麻烦事请拨打968111。【奥淳公众服务中心】","","","");
//		// 获取状态报告
//		System.out.println("*************状态报告行业*************");
//	//	System.out.println(getReportTrade());
//		// 获取状态报告
//		System.out.println("*************状态报告营销*************");
//		//System.out.println(getReportMarket());
//		// 获取上行
//		System.out.println("*************获取上行*************");
//		//System.out.println(getSms());
//		
//		String xml = "<?xml version='1.0' encoding='UTF-8'?><response><result>0</result><desc>成功</desc></response>";
//		Document doc = DocumentHelper.parseText(xml);
//		Element rootElt = doc.getRootElement();
//		System.out.println(rootElt.element("result").getText());
//		Iterator iter = rootElt.elementIterator("report");
//		  while (iter.hasNext()) {
//			  Element recordEle = (Element) iter.next();
//			  System.out.println(recordEle.elementTextTrim("msgid")); // 拿到head节点下的子节点title值
//		  }
		
	}



	
	
	

}
