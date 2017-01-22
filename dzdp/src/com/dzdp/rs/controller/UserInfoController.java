package com.dzdp.rs.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dzdp.rs.model.Member;
import com.dzdp.rs.util.DateTimeUtils;
import com.dzdp.rs.util.ExcelUtil;

@Controller
@RequestMapping("/")
public class UserInfoController {
	@RequestMapping(value = "/info", produces = "application/json;charset=UTF-8")
	public String getMemberInfo(HttpServletRequest request,
			HttpServletResponse response,MultipartFile upload) throws Exception{
		String rootPath = System.getProperty("root");
		String prefix = "data/excel" + DateTimeUtils.getNowTimeString();
		String filetype = "";
		int index = upload.getOriginalFilename().lastIndexOf(".");
		if (index > -1) {
			filetype = upload.getOriginalFilename().substring(index + 1);
		}
		String filename = UUID.randomUUID().toString() + "." + filetype;
		File copyFile = new File(rootPath + prefix +filename);
		if(!copyFile.getParentFile().exists()){
			copyFile.getParentFile().mkdirs();
		}
		upload.transferTo(copyFile);
		List<Member> members = ExcelUtil.readExcel(copyFile);
		if(members.size()>0){
			ExcelUtil.exportExcel(members,response);
		}
		return null;
	}
	
	@RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String upload(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("file") MultipartFile[] upload) throws Exception{
	        JSONObject result = new JSONObject();
		try {
			String rootPath = System.getProperty("root");
			String chunk = request.getParameter("chunk");
			String chunks = request.getParameter("chunks");
			String guid = request.getParameter("guid");
			String size = request.getParameter("size");
			String prefix = "data/file" + DateTimeUtils.getNowTimeString() + guid + "/";
			for(MultipartFile each:upload){
				String filename = chunk + ".tmp";
				File copyFile = new File(rootPath + prefix +filename);
				if(!copyFile.exists()){
					if(!copyFile.getParentFile().exists()){
						copyFile.getParentFile().mkdirs();
					}
					each.transferTo(copyFile);
					System.out.println("chunks-->" + chunks +",chunk-->" + chunk + ",guid-->" + guid + ",size-->" + size);
				}else{
					System.out.println("chunks-->" + chunks +",chunk-->" + chunk + ",guid-->" + guid + ",size-->" + size +",已传过");
				}
				
			}
			result.put("hasError", false);
			result.put("chunked", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("hasError", true);
			result.put("chunked", true);
		}
		return JSON.toJSONString(result);
	}
	
	
}