<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!-- top navi -->
		<!-- Wrapper -->
<%
	System.out.println("<!--Top_nav.jsp-->");

%>
<c:set var="loginChecker" value="${sessionScope.user.pid}"/>

			<div id="wrapper">

				<!-- Header -->
					<header id="header">
						<div class="inner">

							<!-- Logo -->
								<a href="<%=request.getContextPath() %>/Index.jsik" class="logo">
									<span class="symbol"><img src="<%=request.getContextPath() %>/images/logo.svg" alt="" /></span><span class="title">KOSMO</span>
								</a>

							<!-- Nav -->
								<nav>
									<ul>
										<li><a href="#menu">Menu</a></li>
									</ul>
								</nav>

						</div>
					</header>

				<!-- Menu -->
					<nav id="menu">
						<h2>Menu</h2>
						<ul>
							<li><a href="<%=request.getContextPath() %>/Index.jsik">Home</a></li>
							<c:if test="${sessionScope.user.auth_code == 3}">
								<li><a href="#">Admin</a></li>
							</c:if>
						<!-- 비회원은 로그인과 가입하기 -->
						<!-- 회원은 로그아웃 내정보 게시판이 보여야함 -->
							<c:if test="${empty loginChecker }" var ="result_login">
								
								<li><a href="<%=request.getContextPath() %>/custom/Login.jsik">로그인</a></li>
								<li><a href="<%=request.getContextPath() %>/custom/SignUp.jsik">회원가입</a></li>
							</c:if>
							<c:if test="${!result_login }">				
								<li><a href="<%=request.getContextPath() %>/custom/Logout.jsik">로그아웃</a></li>
								<li><a href="<%=request.getContextPath() %>/custom/MyPage.jsik">내정보</a></li>	
								<li><a href="<%=request.getContextPath() %>/memboard/List.jsik">게시판</a></li>	
							</c:if>
						</ul>
					</nav>


