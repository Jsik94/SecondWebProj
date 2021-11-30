<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!-- 어느 컨트롤러에서 왔는지 (포워드 되었는지)에 따라 분기 -->

<!-- 나중에하자 -->

<%
	//확인용
	System.out.println("<--Index Confirm-->");
	System.out.println("View Name : Index.jsp");
	System.out.println("URL From : "+request.getHeader("referer"));
	// \${empty sessionScope.user.pid }
	
	
	System.out.println("Session Info  : "+session.getAttribute("user"));
%>

<c:set var="succ" value="${requestScope.result_code mod 10 }"/>


<c:choose>
	<c:when test="${requestScope.WHERE =='Login' }">	
		<c:set var="successMsg" value="SKIP" />
		<c:set var="failMsg" value="일치하지 않습니다." />
		<c:set var="successUrl" value="/Index.jsik" />
	</c:when>

	<c:when test="${requestScope.WHERE =='SignUp' }">
		<c:set var="successMsg" value="가입이 성공적으로 완료되었습니다!" />
		<c:set var="failMsg" value="아이디가 중복됩니다." />
		<c:set var="successUrl" value="/custom/Login.jsik" />
	</c:when>
	
	<c:when test="${requestScope.WHERE =='Edit' }">
		<c:set var="successMsg" value="수정이 성공적으로 완료되었습니다!" />
		<c:set var="failMsg" value="수정에 실패하였습니다." />
		<c:set var="successUrl" value="/custom/MyPage.jsik" />
	</c:when>
	<c:otherwise>
		<c:set var="successMsg" value="ErrorWay" />
		<c:set var="failMsg" value="ErrorWay" />
		<c:set var="successUrl" value="/Index.jsik" />
	</c:otherwise>
</c:choose>



<script>
	<c:choose>
	
		<c:when test="${succ ==1 }">
		<c:if test="${not successMsg =='SKIP' }">

			alert("${successMsg}");
		</c:if>
		location.replace("<c:url value="${successUrl }"/>");
		</c:when>
	
		<c:when test="${succ ==2 }">
		alert("${failMsg}");
		history.back();
		</c:when>
		
		<c:otherwise>
		alert('세션이 만료되었습니다. 다시 로그인하여 주십시오.');
		location.href = '<c:url value="/Index.jsik"/>';
	
		</c:otherwise>

	</c:choose>
</script>  
   