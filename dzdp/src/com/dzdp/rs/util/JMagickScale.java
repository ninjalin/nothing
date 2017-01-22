package com.dzdp.rs.util;
import java.io.File;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import com.dzdp.rs.conf.ConfigManager;

public class JMagickScale {

	/**
	 * ImageMagick的路径
	 */
	public static String imageMagickPath = null;
	static {
		/**
		 * 
		 * 获取ImageMagick的路径
		 */
		//linux下不要设置此值，不然会报错
		try {
		//	imageMagickPath  = "D:/ImageMagick-6.3.8-Q16";
			imageMagickPath = ConfigManager.getInstance().getText("/Mobile/tools/imageScale");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 根据坐标裁剪图片
	 * 
	 * @param srcPath   要裁剪图片的路径
	 * @param newPath   裁剪图片后的路径
	 * @param x         起始横坐标
	 * @param y         起始纵坐标
	 * @param x1        结束横坐标
	 * @param y1        结束纵坐标
	 */

	public static void cutImage(String srcPath, String newPath, int x, int y, int x1,	int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		/**
		 * width：  裁剪的宽度
		 * height： 裁剪的高度
		 * x：       裁剪的横坐标
		 * y：       裁剪的挫坐标
		 */
		op.crop(width, height, x, y);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();

		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);

		convert.run(op);
	}

	/**
	 * 
	 * 根据尺寸缩放图片
	 * @param width             缩放后的图片宽度
	 * @param height            缩放后的图片高度
	 * @param srcPath           源图片路径
	 * @param newPath           缩放后图片的路径
	 */
	public static void cutImage(int width, int height, String srcPath,String directoryPath, String ImgName) throws Exception {
		IMOperation op = null;
		try {
			File file  = new File(directoryPath);
			if(!file.exists()){
				file.mkdirs();
			}
			op = new IMOperation();
			op.addImage(srcPath);
			op.resize(width, height);
			op.addImage(directoryPath+ImgName);
			ConvertCmd convert = new ConvertCmd();
			// linux下不要设置此值，不然会报错
			convert.setSearchPath(imageMagickPath);
			convert.run(op);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			op.closeOperation();
		}

	}

	/**
	 * 根据宽度缩放图片
	 * 
	 * @param width            缩放后的图片宽度
	 * @param srcPath          源图片路径
	 * @param newPath          缩放后图片的路径
	 */
	public static void cutImage(int width, String srcPath, String newPath)	throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(width, null);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 给图片加水印
	 * @param srcPath            源图片路径
	 */
	public static void addImgText(String srcPath) throws Exception {
		IMOperation op = new IMOperation();
		op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
				.draw("text 5,5 juziku.com");
		op.addImage();
		op.addImage();
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);
		convert.run(op, srcPath, srcPath);
	}

	public static void main(String[] args) throws Exception {
		//cutImage("D:\\test.jpg", "D:\\new.jpg", 98, 48, 370,320);
		 cutImage(50,67, "f:/my.jpg", "f:/mn/ps1/","1.jpg");
//		addImgText("f://my.jpg");
		System.out.println(111);
	}
}