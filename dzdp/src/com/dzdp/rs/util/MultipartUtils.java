package com.dzdp.rs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MultipartUtils {
	 public static boolean combineFiles(String src,int chunks,String saveFileName) throws IOException{ 
	        FileChannel dirFileChannel = null;
	        FileChannel inFileChannel = null;
			try {
				File dirFile = new File(saveFileName); 
				if(!dirFile.getParentFile().exists()){
					dirFile.getParentFile().mkdirs();
				}
				if(!dirFile.exists()){ 
					dirFile.createNewFile(); 
				}
				dirFileChannel = new FileOutputStream(dirFile).getChannel();
				File inputFile = null;
				for(int i=0;i<=chunks;i++){
					inputFile = new File(src + "/" + i + ".tmp");
					inFileChannel = new FileInputStream(inputFile).getChannel(); 
				    inFileChannel.transferTo(0, inFileChannel.size(), dirFileChannel); 
				    inFileChannel.close(); 
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally{
				if(dirFileChannel!=null){
					dirFileChannel.close(); 
				}
				if(inFileChannel!=null){
					inFileChannel.close(); 
				}
			}
	  } 
}
