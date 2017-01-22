package com.dzdp.rs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * 验证手机号、邮箱是否合法的工具类
 */
public class ValidateUtil {
	private static String mobileStr = "^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}$";
	private static String emailStr = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	
	/*
	 * 验证手机号码是否符合要求
	 * @param mobile
	 * @return true or false
	 */
	public static boolean isMobileNO(String mobile){       
		Pattern p = Pattern.compile(mobileStr);
		Matcher m = p.matcher(mobile);       
        return m.matches();       
    }   
     
	/*
	 * 验证邮箱是否符合要求
	 * @param mobile
	 * @return true or false
	 */
    public static boolean isEmail(String email){       
        Pattern p = Pattern.compile(emailStr);       
        Matcher m = p.matcher(email);       
        return m.matches();       
    }   
}
