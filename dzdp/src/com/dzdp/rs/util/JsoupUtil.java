package com.dzdp.rs.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dzdp.rs.conf.ConfigManager;
import com.dzdp.rs.model.Member;

public class JsoupUtil {
    private static Map<String, String> cookie = new HashMap<String, String>();
	public static Member readHtml(Member member) throws Exception {
		member.setPortal(ConfigManager.getInstance().getText(
				"/Mobile/http/index")
				+ "/member/" + member.getId());
//		Document document = Jsoup.connect(
//						ConfigManager.getInstance().getText(
//								"/Mobile/http/index")
//								+ "/member/" + member.getId()).timeout(5000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36").get();
		Connection.Response response;
		
		if(cookie.size()>0){
			response = Jsoup.connect(
					ConfigManager.getInstance().getText(
							"/Mobile/http/index")
							+ "/member/" + member.getId()).timeout(5000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36").cookies(cookie).execute();
		}else{
			 response = Jsoup.connect(
					ConfigManager.getInstance().getText(
							"/Mobile/http/index")
							+ "/member/" + member.getId()).timeout(5000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36").execute();
		}
		Document document = response.parse();
		cookie =  response.cookies();
		Elements elements = document.select("div.nav>ul>li>a[href=/member/"
				+ member.getId() + "/reviews]");
		String comment = null;
		Element element = null;
		if (elements.size() > 0) {
			element = elements.get(0);
		}
		if (element != null) {
			comment = element.ownText().substring(
					element.ownText().indexOf("(") + 1,
					element.ownText().indexOf(")"));
			member.setCommentnum(StringValueUtils.getInt(comment));
		} else {
			member.setCommentnum(0);
		}
		element = null;
		elements = document.select("div.tit>h2.name");
		if (elements.size() > 0) {
			element = elements.get(0);
		}
		if (element != null) {
			member.setName(element.ownText());
		} else {
			member.setName("未知名称");
		}
		element = null;
		elements = document.select("div.user-time>p");
		if (elements.size() > 0) {
			element = elements.get(1);
		}
		if (element != null) {
			member.setLevel(element.ownText());
		} else {
			member.setLevel("未知等级");
		}
		element = null;
		elements = document.select("div.J_rptlist>div.info>span.col-exp");
		if (elements.size() > 0) {
			element = elements.get(0);
		}
		if (element != null) {
			String date = element.ownText();
			Date rightDate = new Date();
			if (date.indexOf("前天") > -1) {
				rightDate = DateTimeUtils.relativeDate(new Date(), -2);
				date = date.replace("前天", "");
				date = DateTimeUtils.dateFormat(rightDate, "MM-dd") + " "
						+ date;
			} else if (date.indexOf("昨天") > -1) {
				rightDate = DateTimeUtils.relativeDate(new Date(), -1);
				date = date.replace("昨天", "");
				date = DateTimeUtils.dateFormat(rightDate, "MM-dd") + " "
						+ date;
			} else if (date.indexOf("今天") > -1) {
				date = date.replace("今天", "");
				rightDate = new Date();
				date = DateTimeUtils.dateFormat(rightDate, "MM-dd") + " "
						+ date;
			}
			member.setLastDate(date);
		} else {
			member.setLastDate("未知时间");
		}
		return member;
	}
	
	
	public static String re(){
		try {
			Connection.Response response = Jsoup.connect(
					ConfigManager.getInstance().getText(
							"/Mobile/http/index")
							+ "/member/" + 600576).timeout(5000).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36").execute();
	    	Map map = 	response.cookies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
