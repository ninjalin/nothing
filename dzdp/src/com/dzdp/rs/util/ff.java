package com.dzdp.rs.util;


import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ff {
	public static void main(String[] args) {
		String sl="E://nio//1.tmp";
		System.out.println(new File(sl).exists());
		
		/*File file = new File("E:\\nio"); //目录路径
		if(file.isDirectory()){ //判断file是否为目录
			String[] fileNames = file.list();
			Arrays.sort(fileNames,new StringComparator());
			for(int i=0;i<fileNames.length;i++){
				System.out.println(fileNames[i]);
 		}
		}*/
	}
}
	
//此类实现Comparable接口
class StringComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		if(StringComparator.returnDouble(s1)<StringComparator.returnDouble(s2))
			return -1;
		else if(StringComparator.returnDouble(s1)>StringComparator.returnDouble(s2))
			return 1;
		else
			return 0;
		
	}
	
	public static double returnDouble(String str){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<str.length();i++){
			if(Character.isDigit(str.charAt(i)))
				sb.append(str.charAt(i));
			else if(str.charAt(i)=='.'&&i<str.length()-1&&Character.isDigit(str.charAt(i+1)))
				sb.append(str.charAt(i));
			else break;
		}
		if(sb.toString().isEmpty())
			return 0;
		else
			return Double.parseDouble(sb.toString());
	}

	

}
