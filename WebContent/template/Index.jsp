<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />


<%
	//확인용
	System.out.println("<--Index Confirm-->");
	System.out.println("View Name : Index.jsp");
	System.out.println("URL From : "+request.getHeader("referer"));
	// \${empty sessionScope.user.pid }
	
	
	System.out.println("Session Info  : "+session.getAttribute("user"));
%>

<c:set var="loginChecker" value="${sessionScope.user.pid}" />

<!-- Main -->
<div id="main">
	<div class="inner">
		<header>
			<h1>Kosmo 2차 프로젝트에 오신것을 환영합니다.</h1>
			<c:if test="${empty loginChecker}" var="result_login">
				<p>어서오세요 메인페이지 입니다.</p>
			</c:if>
			<c:if test="${!result_login }">
				<p>${sessionScope.user.nn }님!환영합니다.
			</c:if>




		</header>

	</div>
	<div class="container">
		<section class="tiles">


			<c:if test="${sessionScope.user.auth_code == 3}">
				<article class="style1">
					<span class="image"> <img
						src="<%=request.getContextPath() %>/images/pic01.jpg" alt="" />
					</span> <a href="#">
						<h2>Admin</h2>
						<div class="content">
							<p>미구현 상태입니다</p>
						</div>
					</a>
				</article>
			</c:if>


			<c:if test="${!result_login}">
				<article class="style4">
					<span class="image"> <img
						src="<%=request.getContextPath() %>/images/pic04.jpg" alt="" />
					</span> <a href="<c:url value="/memboard/List.jsik"/>">
						<h1>Board</h1>
						<div class="content">
							<p> 회원제 게시판을 이용해보세요.</p>
						</div>
					</a>
				</article>
				<article class="style5">
					<span class="image"> <img
						src="<%=request.getContextPath() %>/images/pic05.jpg" alt="" />
					</span> <a href="<c:url value="/custom/MyPage.jsik"/>">
						<h1>Mypage</h1>
						<div class="content">
							<p>나의 정보를 확인하세요.</p>
						</div>
					</a>
				</article>
			</c:if>
			<c:if test="${result_login }">

				<article class="style2">
					<span class="image"> <img
						src="<%=request.getContextPath() %>/images/pic02.jpg" alt="" />
					</span> <a href="<c:url value ="/custom/Login.jsik"/>">
						<h1>Login</h1>
						<div class="content">
							<p>로그인을 하여 회원제 게시판에 접속해보세요.</p>
						</div>
					</a>
				</article>

				<article class="style3">
					<span class="image"> <img
						src="<%=request.getContextPath() %>/images/pic03.jpg" alt="" />
					</span> <a href="<c:url value ="/custom/SignUp.jsik"/>">
						<h1>SignUp</h1>
						<div class="content">
							<p>회원가입을 통해 게시판을 이용해보세요.</p>
						</div>
					</a>
				</article>
			</c:if>




		</section>
	</div>

</div>

<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />