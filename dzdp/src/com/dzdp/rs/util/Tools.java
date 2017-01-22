package com.dzdp.rs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class Tools {
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	 public static void combineFiles(List<File> files,String saveFileName) throws IOException{ 
	        File mFile = new File(saveFileName); 
	        if(!mFile.exists()){ 
	            mFile.createNewFile(); 
	        } 
	        FileChannel mFileChannel = new FileOutputStream(mFile).getChannel(); 
	        FileChannel inFileChannel; 
	        for(File file:files){ 
	            inFileChannel = new FileInputStream(file).getChannel(); 
	            inFileChannel.transferTo(0, inFileChannel.size(), mFileChannel); 
	            inFileChannel.close(); 
	        }
	        mFileChannel.close(); 
	  } 
	 
	 public static void combineFiles(String src,int chunks,String saveFileName) throws IOException{ 
	        File dirFile = new File(saveFileName); 
	        if(!dirFile.getParentFile().exists()){
	        	dirFile.getParentFile().mkdirs();
	        }
	        if(!dirFile.exists()){ 
	        	dirFile.createNewFile(); 
	        }
	        FileChannel inFileChannel = null;
	        FileChannel dirFileChannel = new FileOutputStream(dirFile).getChannel();
	        File inputFile = null;
	        for(int i=0;i<=chunks;i++){
	        	inputFile = new File(src + "/" + i + ".tmp");
	        	inFileChannel = new FileInputStream(inputFile).getChannel(); 
	            inFileChannel.transferTo(0, inFileChannel.size(), dirFileChannel); 
	            inFileChannel.close(); 
	        }
	        dirFileChannel.close(); 
	  } 
	 
	 
	 public static void main(String[] args) throws IOException {
		 String src = "E:/nio";
		 combineFiles(src, 11, "E:/rfs/ao.zip");
		 System.out.println("done");
		 //System.out.println(s1.compareTo(s2));
	}
	 
}
