/*
s * All rights reserved.
 */
package com.dzdp.rs.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.text.ParseException;

import org.springframework.util.StringUtils;


/**
 * 处理日期时间相关的工具类
 *
 * @since 1.0
 *
 * @author Liudong
 * @version 2.0
 * Date:2003-11-2
 */
public class DateTimeUtils
{

	public final static String DEFAULTFORMAT="yyyy-MM-dd HH:mm:ss";
	
	public final static String SIMPLEFORMAT="yyyy-MM-dd";
	
    /**
     * 将时间转换为数字字符串(毫秒级)
     *
     * @param date 要进行转换的时间
     * @return 转换后的字符串
     */
    public static final String dateToMillis(Date date)
    {
        return dateFormat(date, "yyyyMMddHHmmssSSS");
    }

    /**
     * 按指定的格式对日期进行格式化
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormat(Date date, String format)
    {
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }
    
    /**
     * 按指定的格式对日期进行格式化yyyy-MM-dd
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormatToYMD(Date date)
    {
    	if(date==null){
    		return "";
    	}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    /**
     * 按指定的格式对日期进行格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormatToYMDHMS(Date date)
    {
    	if(date==null){
    		return "";
    	}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 按指定的格式对日期进行格式化
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormat(long date, String format)
    {
        return dateFormat(new Date(date), format);
    }

    /**
     * 取得当前时间字符串
     *
     * @return 当前时间字符串
     */
    public static final String getNowString()
    {
        Date now = new Date();
        SimpleDateFormat formatter = null;
        formatter =
            new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.getDefault());
        return formatter.format(now);
    }

    /**
     * 将当前的时间转换为数字字符串(毫秒级)
     *
     * @return 转换后的字符串
     */
    public static final String dateToMillis()
    {
        Date date = new Date();
        return dateToMillis(date);
    }

    /**
     * 将年月日字符串转换成Calendar.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"或"1970/1/1"
     * @param flag 年月日字符串的分割符，如在上面的例子中为"-"或"/"
     * @return Calendar实例
     * @see Calendar
     */
    public static final Calendar convert2Calendar(String ymd, String flag)
    {
        if (ymd == null || flag == null || "".equals(ymd) || "".equals(flag))
        {
            return Calendar.getInstance(Locale.getDefault());
        }
        String[] dates = StringUtils.split(ymd, flag);
        int year = 1970;
        int month = 1;
        int date = 1;
        if (dates != null && dates.length == 3)
        {
            try
            {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]) - 1;
                if (month < 0 || month > 11)
                {
                    throw new Exception();
                }
                date = Integer.parseInt(dates[2]);
                if (date < 1 || date > 31)
                {
                    throw new Exception();
                }
            }
            catch (Exception ex)
            {
                year = 1970;
                month = 1;
                date = 1;
            }
        }
        Calendar ret = Calendar.getInstance(Locale.getDefault());
        ret.set(year, month, date, 23, 59, 59);
        ret.set(Calendar.MILLISECOND, 999);
        return ret;
    }

    /**
     * 将年月日字符串转换成Calendar.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"
     * @return Calendar实例
     * @see Calendar
     */
    public static final Calendar convert2Calendar(String ymd)
    {
        return convert2Calendar(ymd, "-");
    }

    /**
     * 将年月日字符串转换成Date.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"或"1970/1/1"
     * @param flag 年月日字符串的分割符，如在上面的例子中为"-"或"/"
     * @return Date实例
     * @see Date
     */
    public static final Date convert2Date(String ymd, String flag)
    {
        Calendar cal = convert2Calendar(ymd, flag);
        return cal.getTime();
    }

    /**
     * 将年月日字符串转换成Date.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"
     * @return Date实例
     * @see Date
     */
    public static final Date convert2Date(String ymd)
    {
        return convert2Date(ymd, "-");
    }

    public static final Calendar convert2TimeCalendar(String hms)
    {
        if (hms == null || "".equals(hms))
        {
            return null;
        }
        String[] tmp = StringUtils.split(hms, ":");
        if (tmp == null || tmp.length != 3)
        {
            return null;
        }
        int h = 0;
        int m = 0;
        int s = 0;
        try
        {
            h = Integer.parseInt(tmp[0]);
            m = Integer.parseInt(tmp[1]);
            s = Integer.parseInt(tmp[2]);
        }
        catch (Exception ex)
        {
            return null;
        }
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DATE),
            h,
            m,
            s);
        return cal;
    }

    /**
     * 将指定的时间字符串转换成Date格式
     *
     * @param hms 时间字符串 时:分:秒
     * @return Date格式的时间
     */
    public static final Date convert2Time(String hms)
    {
        Calendar cal = convert2TimeCalendar(hms);
        if (cal == null)
        {
            return null;
        }
        else
        {
            return cal.getTime();
        }
    }

    /**
     * 将日期时间字符串转换为Date类型数据
     * 默认分隔符为","
     *
     * @param datetime 保护日期时间信息的字符串
     * @return Date类型数据
     */
    public static final Date convert2DateTime(String datetime)
    {
        if (datetime == null || "".equals(datetime))
        {
            return null;
        }
        String[] tmp = StringUtils.split(datetime, ",");
        if (tmp == null || tmp.length != 6)
        {
            return null;
        }
        int year = 0;
        int month = 0;
        int date = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        try
        {
            year = Integer.parseInt(tmp[0]);
            month = Integer.parseInt(tmp[1]);
            date = Integer.parseInt(tmp[2]);
            hour = Integer.parseInt(tmp[3]);
            minute = Integer.parseInt(tmp[4]);
            second = Integer.parseInt(tmp[5]);
        }
        catch (Exception ex)
        {
            return null;
        }
        Calendar ret = Calendar.getInstance();
        ret.set(year, month - 1, date, hour, minute, second);
        return ret.getTime();
    }

    /**
     * 从指定的日期中获取年份信息
     * @param date 日期
     *
     * @return 年份
     */
    public static int getYear(Date date)
    {
        return getField(date, Calendar.YEAR);
    }

    public static int getYear(long date)
    {
        return getYear(new Date(date));
    }

    /**
     * 获取系统当前年份
     *
     * @return 当前年份
     */
    public static int getYear()
    {
        return getYear(System.currentTimeMillis());
    }

    /**
     * 从指定的日期中获取月份信息
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date)
    {
        return getField(date, Calendar.MONTH) + 1;
    }

    public static int getMonth(long date)
    {
        return getMonth(new Date(date));
    }

    /**
     * 获取系统当前月份
     *
     * @return 当前月份
     */
    public static int getMonth()
    {
        return getMonth(System.currentTimeMillis());
    }

    /**
     * 从指定的日期中获取日信息
     *
     * @param date 日期
     * @return 日
     */
    public static int getDate(Date date)
    {
        return getField(date, Calendar.DATE);
    }

    public static int getDate(long date)
    {
        return getDate(new Date(date));
    }

    /**
     * 获取系统当前的日
     *
     * @return 日
     */
    public static int getDate()
    {
        return getDate(System.currentTimeMillis());
    }

    /**
     * 从字符串中获取日期时间信息
     * 字符串格式："年,月,日,时,分,秒"
     *
     * @param date 日期字符串
     * @return 代表指定日期时间的长整型值
     */
    public static long getTimeInMillis(String date)
    {
        if (date == null || "".equals(date))
        {
            return 0L;
        }
        String[] tmp = null;
        tmp = StringUtils.split(date, ",");
        if (tmp == null)
        {
            return 0L;
        }
        int count = tmp.length;
        if (count != 6)
        {
            return 0L;
        }
        int tmpYear = 0;
        int tmpMonth = 0;
        int tmpDate = 0;
        int tmpHour = 0;
        int tmpMinute = 0;
        int tmpSecond = 0;
        try
        {
            tmpYear = Integer.parseInt(tmp[0]);
            tmpMonth = Integer.parseInt(tmp[1]);
            tmpDate = Integer.parseInt(tmp[2]);
            tmpHour = Integer.parseInt(tmp[3]);
            tmpMinute = Integer.parseInt(tmp[4]);
            tmpSecond = Integer.parseInt(tmp[5]);
        }
        catch (Exception ex)
        {
            return 0L;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(tmpYear, tmpMonth - 1, tmpDate, tmpHour, tmpMinute, tmpSecond);
        return cal.getTime().getTime();
    }

    /**
     * 从指定的字符串中获取日期时间信息
     * 字符串格式："年,月,日,时,分,秒"
     *
     * @param date 日期时间字符串
     * @return 日期
     */
    public static Date getTime(String date)
    {
        return new Date(getTimeInMillis(date));
    }

    /**
     * 从指定的日期中获取相应的信息
     *
     * @param date 日期
     * @param field 信息类型(同Calendar中的定义)
     * @return 日期中指定类型的值
     */
    public static int getField(Date date, int field)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }
    
    /**
     * 获取date所在月的最后一天，时间默认设置为"23:59:59"
     * @param date
     * @return
     */
	public static String getMonthEndDate(String date) {
		String result = null ;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
		    Date temp = sdf2.parse(date);
		    cal.setTime(temp);
		 // 月的最后一天
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
			Date endTime = cal.getTime();
			result = sdf1.format(endTime) + " 23:59:59" ;
			//ret = sdf2.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取date所在月的第一天，时间默认设置为"00:00:00"
	 * @param date
	 * @return
	 */
	public static String getMonthBeginDate(String date) {
		String result = null ;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try {
		    Date temp = sdf2.parse(date);
		    cal.setTime(temp);
		    // 月第一天
			cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
			Date beginTime = cal.getTime();
			result = sdf1.format(beginTime) + " 00:00:00" ;
			//ret = sdf2.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    /**
     * 将日期时间格式化成"xxxx年xx月xx日 时:分:秒"的格式
     *
     * @param date 要进行转换的日期时间
     * @return 表示日期时间的字符串，格式：xxxx年xx月xx日 时:分:秒
     */
    public static String formatDateTime(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateTimeUtils.dateFormat(date, "yyyy年MM月dd日 HH:mm:ss");
    }
    
    /**
     * 将日期格式化成"xxxx年xx月xx日"的格式
     *
     * @param date 要进行转换的日期
     * @return 表示日期的字符串，格式：xxxx年xx月xx日
     */
    public static String formatDate(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateTimeUtils.dateFormat(date, "yyyy年MM月dd日");
    }

    /**
     * 将时间格式化成"时:分:秒"的格式
     *
     * @param date 要进行转换的时间
     * @return 表示时间的字符串，格式：时:分:秒
     */
    public static String formatTime(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateTimeUtils.dateFormat(date, "HH:mm:ss");
    }

    /**
     * 根据给定的当前日期计算前一天，后一天
     * @param currentDate  给定的当前日期，如果为null，则取系统的当前时间,格式为
     *                     "yyyy-mm-dd",由调用者保证
     * @return "yyyy-mm-dd"格式的当前日期，前一天，后一天的日期值,数组的第一个值
     *         是前一天的值，第二个值是当前的值，最后一个是后一天的值
     */
    public static String[] getNearDate(String currentDate)
    {
        String[] retVal = new String[3];
        Calendar calendar = Calendar.getInstance();
        if (currentDate != null)
        {
            int year = Integer.parseInt(currentDate.substring(0, 4));
            int month = Integer.parseInt(currentDate.substring(5, currentDate.lastIndexOf("-")));
            int day = 1;
            int timepos = currentDate.lastIndexOf(" ");
            if(timepos>0)
              day = Integer.parseInt(currentDate.substring(currentDate.lastIndexOf("-") + 1,timepos));
            else  day = Integer.parseInt(currentDate.substring(currentDate.lastIndexOf("-") + 1));
            calendar.set(year, month - 1, day);   //月的下标从0开始，所以减1
        }
        retVal[1] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        retVal[0] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        retVal[2] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        return retVal;
    }
    
    /**
	 * 将String 转换成java.util.date 时间格式为(yyyy-mm-dd HH:mm:ss)
	 * 
	 * @param s
	 *            转换的字符串
	 * @param return
	 *            java.util.Date
	 */
	public static java.util.Date toDateTime(String date) {
		java.util.Date result = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    result = sdf1.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static java.util.Date toDateTime(String date,String format) {
		java.util.Date result = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		    result = sdf1.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将String 转换成java.util.date 时间格式为(yyyy-mm-dd HH:mm:ss)
	 * 
	 * @param s
	 *            转换的字符串
	 * @param return
	 *            java.util.Date
	 */
	public static String formatDateTime2(Date date) {
		String result = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    result = sdf1.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	  /**
	   * 获得当前时间，并用/隔开，用于创建时间文件夹
	   * @return
	   */
	  public static String getNowTimeString(){
	      //ftp地址分隔符
	      String separator = "/";
	      String strDate = new Timestamp(System.currentTimeMillis()).toString().substring(0, 10);
	      String strYear = strDate.substring(0,4);
	      String strMonth = strDate.substring(5,7);
	      String strDay = strDate.substring(8,10);
	      String rusult = separator + strYear +separator +strMonth +separator+ strDay + separator;
	      return rusult;
	  }
	  
	  /**
	   * 获取当前时间的SQLDATE类型,不带时分秒
	   * @return
	   */
	  public static java.sql.Date getSqlDate(){
		  Date date=Calendar.getInstance().getTime();   
		  java.sql.Date sqldate = new java.sql.Date(date.getTime());
		  return sqldate;
	  }
	  
	  /**
	   * 获取当前时间的SQLDATE类型,带时分秒
	   * @return
	   */
	  public static java.sql.Date getSqlDateTime(){
		  java.sql.Date dt=new java.sql.Date (System.currentTimeMillis());
		  return dt;
	  }
	  
	  /**
		 * 获取2个Float值的中间值。如果参数为空或者参数相等，则抛出异常。
		 * @param f1
		 * @param f2
		 * @return
		 * @throws Exception 
		 */
	  public static Float getBetween(Float f1, Float f2) throws Exception {
		 
		 if(f1 != null && f2 != null && f1.floatValue()!=f2.floatValue()){
			 if(f1 > f2)
				 return new Float((f1 - f2)/2+f2);
			 else
				 return new Float((f2 - f1)/2+f1);
		 }else{
			 throw new Exception("getBetween value error : param1 = "+f1+" ,param2="+f2);
		 }
	 }
	  
	  /**
		 * 获取上一个Float值。不允许输入空值。
		 * @param f1
		 * @throws Exception 
		 */
	  public static Float getPreviousFloat(Float f1) throws Exception {
		  // TODO Auto-generated method stub
		  if(f1 != null){
			  int i1 = Math.round(f1);
			  i1 --;
			  return new Float(i1);
		  }else{
			  throw new Exception("getPreviousFloat value error : param1 = "+f1);
		  }		
	  }
	  
	  /**
		 * 获取下一个Float值。不允许输入空值。
		 * @param f1
		 * @throws Exception 
		 */
	  public static Float getNextFloat(Float f1) throws Exception {
			// TODO Auto-generated method stub
		  if(f1 != null){
			  int i1 = Math.round(f1);
			  i1 ++;
			  return new Float(i1);
		  }else{
			  throw new Exception("getNextFloat value error : param1 = "+f1);
		  }		
	  }
	  
	  /**
	   * 获取N天后的日期
	   * @param date
	   * @param days
	   * @return
	   */
	  public static java.util.Date relativeDate(java.util.Date date, int days) {
			if (date == null)
				return null;
			Calendar calendar = Calendar.getInstance();
			calendar.setLenient(false);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}
	  
	  /**
	   * 将时间毫秒数转换成字母
	   * @param str
	   * @return
	   */
	  public static String getcurrentTimeMillisToWord(String str){
		  StringBuffer sb = new StringBuffer("");
		  
		  char[] charArray = str.toCharArray();
		  for(int i=0;i<charArray.length;i++){
			  int temp = 97+Integer.parseInt(charArray[i]+"");
			  sb.append((char)temp);
		  }
		  
		  return sb.toString();
	  }
	  
	/**
	 * 计算两时间间隔多少天
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int countDays(Date begin, Date end) {
		int days = 0;
		Calendar c_b = Calendar.getInstance();
		Calendar c_e = Calendar.getInstance();
		try {
			c_b.setTime(begin);
			c_e.setTime(end);
			while (c_b.before(c_e)) {
				days++;
				c_b.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (Exception pe) {
			System.out.println("日期格式必须为：yyyy-MM-dd.");
		}

		return days;
	}
	
	public static int countDays(String begin, String end) {
		Date startDate=DateTimeUtils.convert2Date(begin);
		Date endDate=DateTimeUtils.convert2Date(end);
		return countDays(startDate,endDate);
	}
	
	/**
	 * 在某日期加上多少天
	 * @param baseDate
	 * @param diffDay
	 * @return
	 */
	public static Date addDay(Date baseDate,int diffDay){
		Date result=null;
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(baseDate);
			cal.add(Calendar.DAY_OF_YEAR, diffDay);
			result=cal.getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date addDay(String baseDate,int diffDay){
		Date date=DateTimeUtils.convert3Date(baseDate);
		return addDay(date,diffDay);
	}
	
	/**
	 * 将日期字符串转成日期对象
	 * @param dateStr
	 * @return
	 */
	public static Date convert3Date(String dateStr){
		Date result = null;
		try {
			SimpleDateFormat formatter = null;
			formatter = new SimpleDateFormat(SIMPLEFORMAT);
			result = formatter.parse(dateStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取当天所属周的周一
	 * @param currDate
	 * @return
	 */
	public static Date getFirstDayOfWeekByCurrDate(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			dayOfWeek=8;
		}
		cal.add(Calendar.DAY_OF_YEAR, -dayOfWeek+2);
		return cal.getTime();
	}
	
	/**
	 * 获取当天所属周的周日
	 * @param currDate
	 * @return
	 */
	public static Date getLastDayOfWeekByCurrDate(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			dayOfWeek=8;
		}
		int diffDay=7-dayOfWeek+1;
		cal.add(Calendar.DAY_OF_YEAR, diffDay);
		return cal.getTime();
	}
	
	/**
	 * 返回当前日期是周几,周一：1   周二：2 周三：3......周日：7
	 * @param currDate
	 * @return
	 */
	public static int getDayOfWeek(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			dayOfWeek=8;
		}
		int diffDay=dayOfWeek-1;
        return diffDay;		
	}
	
	/**
	 * 获取当前时间属于当年第几周
	 * @param currDate
	 * @return
	 */
	public static int getWeekOfYear(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		int weekOfYear=cal.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}
	
	public static int getWeekOfYear(String currDate){
		Date tmp=convert3Date(currDate);
		return getWeekOfYear(tmp);
	}
	
	/**
	 * 获上一周当天时间
	 * @param currDate
	 * @return
	 */
	public static Date getDayOfPreviousWeek(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_YEAR,-7);
		return cal.getTime();
	}
	
	/**
	 * 获下一周当天时间
	 * @param currDate
	 * @return
	 */
	public static Date getDayOfNextWeek(Date currDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_YEAR,7);
		return cal.getTime();
	}
	
	/**
	 * 获取中文周几名称
	 * @param weekId
	 * @return
	 */
	public static String getChineseNameOfWeek(int weekId){
		String result="星期";
		switch(weekId){
		    case 1:
		        result+="一";
		        break;
		    case 2:
			    result+="二";
			    break;
		    case 3:
			    result+="三";
			    break;
		    case 4:
			    result+="四";
			    break;
		    case 5:
			    result+="五";
			    break;
		    case 6:
			    result+="六";
			    break;
		    case 7:
			    result+="日";
			    break;
		}
		return result;
	}
	
	public static void main(String[] args){
		/*Date start=DateTimeUtils.convert2Date("2012-10-01");
		Date end=DateTimeUtils.convert2Date("2012-10-11");
		Date baseDate=DateTimeUtils.convert2Date("2012-10-01");
		System.out.println(countDays(start,end));
		System.out.println(addDay(baseDate,92));*/
		System.out.println(DateTimeUtils.getWeekOfYear("2012-01-14"));
	}
}
