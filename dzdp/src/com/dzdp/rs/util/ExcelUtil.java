package com.dzdp.rs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dzdp.rs.conf.ConfigManager;
import com.dzdp.rs.model.Member;

public class ExcelUtil {
	public static List<Member> readExcel(File file) {
		List<Member> members = new ArrayList<Member>();
		Member member = null;
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				for (int rowNum = 0; rowNum < xssfSheet.getLastRowNum() + 1; rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					int value = (int) xssfRow.getCell(0).getNumericCellValue();
					member = new Member();
					member.setId(value);
					Thread.sleep(StringValueUtils.getLong(ConfigManager.getInstance().getText("/Mobile/http/time")));
					member = JsoupUtil.readHtml(member);
					members.add(member);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return members;
	}

	public static void main(String[] args) throws Exception {
		// readExcel(new File("E:/1.xlsx"));
		String s = HttpUtils.invokeGetMethod("http://www.dianping.com/member/600576");
		  System.out.println(s);
		 
//		Document document = Jsoup.parse(new File("E:/1.html"), "UTF-8");
	//	Document document = Jsoup.connect("http://www.dianping.com/member/600576").userAgent("Mozilla").timeout(5000).get();
//		Elements element = document.select("div.tit>h2.name");
	//	Elements element = document.select("div.user-time>p");
	//	for (Element each : element) {
	//		System.out.println(each.ownText());
	//	}*/
//		Member member = new Member();
//		member.setId(600576);
//		System.out.println(JSON.toJSONString(JsoupUtil.readHtml(member)));
		//System.out.println(DateTimeUtils.dateFormat(new Date(), "MM-dd"));
	}

	public static void exportExcel(List<Member> members,
			HttpServletResponse response) {
		WritableWorkbook wwb = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			String excelName = UUID.randomUUID().toString();
			// 不能用用中文设置 filename，会出错
			response.setHeader("Content-disposition",
					"attachment; filename="+excelName+"-member.xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("已使用书号图书列表", 10); // 创建一个工作表
			// 设置单元格的文字格式
			WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat titleFontFormat = new WritableCellFormat(
					titleFont);
			titleFontFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			titleFontFormat.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);
			// 填充标题
			ws.addCell(new Label(0, 0, "ID", titleFontFormat));
			ws.addCell(new Label(1, 0, "昵称", titleFontFormat));
			ws.addCell(new Label(2, 0, "会员等级", titleFontFormat));
			ws.addCell(new Label(3, 0, "点评数", titleFontFormat));
			ws.addCell(new Label(4, 0, "最后回帖时间", titleFontFormat));
			ws.addCell(new Label(5, 0, "个人主页", titleFontFormat));

			// 调整宽度
			ws.setColumnView(0, 12);
			ws.setColumnView(1, 15);
			ws.setColumnView(2, 15);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 40);
			ws.setColumnView(5, 50);
			Member member = null;
			if (members != null && members.size() > 0) {
				for (int i = 0; i < members.size(); i++) {
					member = members.get(i);
					Number number  = new Number(0, i + 1, member.getId());
					ws.addCell(number);
					ws.addCell(new Label(1, i + 1, member.getName()));
					ws.addCell(new Label(2, i + 1, member.getLevel()));
					ws.addCell(new Label(3, i + 1, member.getCommentnum()+""));
					ws.addCell(new Label(4, i + 1, member.getLastDate()));
					WritableHyperlink link = new WritableHyperlink(5, i+1, new URL(member.getPortal()));
					ws.addHyperlink(link);
				}
			}
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			try {
				if (wwb != null) {
					wwb.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
