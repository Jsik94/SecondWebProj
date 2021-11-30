<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//확인용
	System.out.println("<--Top Confirm-->");
	System.out.println("View Name : Top.jsp");
	System.out.println("URL From : "+request.getHeader("referer"));
	System.out.println("Session Info  : "+session.getAttribute("user"));
	
	System.out.println("Session Login Listener : "+ session.getAttribute("user") != null ? "connected account" : "disconnected!");
	
	

	//System.out.println("Content확인용  : "+((MemBoardDTO)request.getAttribute("record")).getContent() );
	
%>



<!DOCTYPE HTML>
<!--
	Phantom by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>2nd Project</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<!-- 부트 링크 -->
		<!--
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		-->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
		
		<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/main.css" />	
		
	</head>
	
	<body class="is-preload">	
