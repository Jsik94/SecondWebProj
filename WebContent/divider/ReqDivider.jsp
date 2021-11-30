<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!-- 어느 컨트롤러에서 왔는지 (포워드 되었는지)에 따라 분기 -->

<!-- 나중에하자 -->

<c:choose>

	<c:when test="${requestScope.WHERE =='login' }">
		<c:set var="result" value="${requestScope.login }"/>
		<c:set var="successMsg" value="입력성공" />
		<c:set var="failMsg" value="입력실패" />
		<c:set var="successUrl" value="/dataroom/List.kosmo" />
	</c:when>
	<c:when test="${requestScope.WHERE =='EDT' }">
		<c:set var="successMsg" value="수정성공" />
		<c:set var="failMsg" value="수정실패" />
		<c:set var="successUrl"
			value="/dataroom/View.kosmo?no=${requestScope.no }" />
	</c:when>
	<c:otherwise>
		<c:set var="successMsg" value="삭제 성공했어요" />
		<c:set var="failMsg" value="삭제 실패했어요" />
		<c:set var="successUrl" value="/dataroom/List.kosmo" />
	</c:otherwise>


</c:choose>

<script>
	<c:choose>
	
		<c:when test="${SUCCFAIL ==1 }">
		alert("${successMsg}");
		location.replace("<c:url value="${successUrl }"/>");
		</c:when>
	
		<c:when test="${SUCCFAIL ==0 }">
		alert("${failMsg}");
		history.back();
		</c:when>
		
		<c:otherwise>
		alert('파일크기가 초과하였습니다.');
		history.back();
	
		</c:otherwise>

	</c:choose>
</script>  
   