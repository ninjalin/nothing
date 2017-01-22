/**
 * <p>
 * FileName:ConfigManager.java
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013-4-15
 * </p>
 * <p>
 * Company: 
 * </p>
 * 
 * @author 辛洪伟
 * @version 1.0
 */
package com.dzdp.rs.conf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.dzdp.rs.util.StringValueUtils;


public class ConfigManager {
	
	private static Log log = LogFactory.getLog(ConfigManager.class);
	
	/** 缓存配置信息 */
	private static Map<String, Object> propertyHash = new HashMap<String, Object>();

	private static ConfigManager instance;
	
	private static Document document=null;
	private Integer subsysid;
	public ConfigManager() throws DocumentException{
		init();
	}
   
	/** 单例模式 */
	public static ConfigManager getInstance(){
		if (instance == null) {
			try {
				instance = new ConfigManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**初始化配置文件*/
	public void init() throws DocumentException{
		InputStream is=null;
		InputStream ism=null;
		try{
			System.out.println("===========================================");
			//指定目录获配置为空时，再从classpath中获取
			is = getClass().getClassLoader().getResourceAsStream("Mobile.xml");
		    SAXReader reader = new SAXReader();
			document = reader.read(is);
		}catch (DocumentException ex)
        {
            log.fatal("读取配置文件出错 请检查配置文件是否在classpath中指定的目录", ex);
            throw ex;
        }finally{
        	if(is!=null){
        		try{
        		    //is.close();
        		}catch(Exception e){}
        	}
        }
	}

	public Integer getSubsysid() {
		try {
			if (this.subsysid == null) {
				this.subsysid = StringValueUtils.getInt(this
						.getProperty("/Mobile/subsysid"));
			}
		} catch (Exception e) {
			log.error("获取子系统ID时出错", e);
		}
		return subsysid;
	}
	
	/**
	 * 向外公开获取系统配置方法，传入配置路径
	 * 
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public String getProperty(String xPath) throws Exception {
		String result = null;
		try {
			if (document == null) {
				init();
			}
			Element ele = (Element) document.selectSingleNode(xPath);
			if (ele != null) {
				result = ele.getText();
			}
		} catch (Exception e) {
			log.error("获取指定配置时出错", e);
			throw e;
		}
		return result;
	}
	/**
	 * 获取单个配置项配置
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getText(String xPath){
		String result=null;

		if(propertyHash.containsKey(xPath)){
			return (String)propertyHash.get(xPath);
		}
		Node node = document.selectSingleNode(xPath);
		if(node!=null){
			result=node.getText();
			propertyHash.put(xPath, result);
		}
		return result;
	}
	
	/**
	 * 将单个配置项信息转化为整形对象
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public Integer getInteger(String xPath) throws Exception{
		Integer result=null;
		String tmp=getText(xPath);
		if(tmp!=null){
			result=Integer.valueOf(tmp);
		}
		return result;
	}
	
	/**
	 * 获取一个配置项对应多个信息
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public synchronized List<Node> getList(String xPath) throws Exception{
		List<Node> result=null;
		if(propertyHash.containsKey(xPath)){
			return (List<Node>)propertyHash.get(xPath);
		}
		
		result=document.selectNodes(xPath);
		if(result!=null&&result.size()>0){
			propertyHash.put(xPath, result);
		}
		return result;
	}
	
	/**
	 * 重新获取配置信息
	 */
	public void refresh() throws DocumentException{
		instance=new ConfigManager();
		propertyHash.clear();
	}
	
	
	/**
	 * 发布缓存是否打开
	 * @return
	 */
	public boolean isOpenMemCache(){
		boolean result=false;
		try{
		    String isOpen=ConfigManager.getInstance().getText("/Mobile/memcached/open");
		    if("1".equals(isOpen)){
		    	result=true;
		    }
		}catch(Exception e){
			log.error("", e);
		}
		return result;
	}

	public static void main(String[] args) throws Exception{
		ConfigManager manager=ConfigManager.getInstance();
		String url = manager.getText("/Mobile/Parameter[@id=3]/value");
//		List<Node> list=manager.getList("/Mobile/siteid");
//		for(Node node:list){
//   	        System.out.println(node.getText());
//		}
		System.out.println(url);
	}
}
