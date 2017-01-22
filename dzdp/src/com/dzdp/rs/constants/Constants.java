package com.dzdp.rs.constants;

import com.dzdp.rs.conf.ConfigManager;

public class Constants {
	/**
	 * 系统主键编码长度
	 * */
	public static final int SYS_ID_LENGTH = 100000000;
	
	public static final String CACHE_MOBILE_CLASS_CONTENT = "CACHE_MOBILE_CLASS_CONTENT_";

	public final static String CACHE_MOBILE_CLASS_CONTENT_PUT_TIME = "CACHE_MOBILE_CLASS_CONTENT_PUT_TIME_";
	
	public final static String CACHE_MOBILE_CLASS_CONTENT_GET_TIME = "CACHE_MOBILE_CLASS_CONTENT_GET_TIME_";
	
	/**
	 * 当前系统的subsysid
	 * */
	private static Long currentSysID=Long.parseLong(String.valueOf(ConfigManager.getInstance().getSubsysid()));
	
	public static final Long getCurrentSysID() {

		    return currentSysID;
	}

}
