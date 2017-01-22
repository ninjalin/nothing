<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/webuploader/webuploader.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/webuploader/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/webuploader/bootstrap-theme.min.css">
		<script type="text/javascript" src="${ctx}/webuploader/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/webuploader/webuploader.js"></script>
		
		<script>
			$(function(){
			});
		</script>
	</head>
	<body>
		<input id="pro" value="${ctx}" type="hidden">
		<div id="uploader" class="wu-example">
		    <!--用来存放文件信息-->
		    <form enctype ="multipart/form-data">
				<div id="thelist" class="uploader-list"></div>
		    </form>
		    <div class="btns">
		        <div id="picker">选择文件</div>
		        <button id="ctlBtn" class="btn btn-default">开始上传</button>
		        <button id="retryBtn" class="btn btn-default">重试</button>
		    </div>
		</div>
	 	<!-- <div style="margin:10px;border:1px solid #99BBE8;height: 60px;">
	 			<span style="line-height:60px;font-family: '微软雅黑';font-size: 16px;"><marquee scrollamount="5">Produce By Ninjalin for My Dear Ding , Good Luck ! </marquee></span>
	  	</div> -->
	  	<!-- <div id="uploader-demo">
		    用来存放item
		    <div id="fileList" class="uploader-list"></div>
		    <div id="filePicker">选择图片</div>
		</div> -->
	  <%-- 	<div style="margin:10px;border:1px solid #99BBE8;height: 100px;">
		  	<form style="padding-top:40px;" action="${ctx}/info" method="post" enctype ="multipart/form-data">
		  		<input name="upload" id="file" type="file" accept=".xlsx" >
		  		<input  type="submit" value="选择好了文件？开整！" >
		  	</form>
	  	</div> --%>
	</body>
	<script type="text/javascript" src="${ctx}/webuploader/getting-started.js"></script>
</html>
