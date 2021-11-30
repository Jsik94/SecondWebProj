<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="j" uri="/WEB-INF/tld/myTag.tld" %>
<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />

<style>
button:hover {
	background-color: white;
}
</style>

<div class="container my-5" id="main-content" style="justify-content: center; text-align: center;">
	<div class="row">
		<div class="col"></div>
		<div class="col-4" style="text-align: center">
		
			<c:if test="${empty others }" var="result">
				<h1>내 정보 조회</h1>
			</c:if>
			<c:if test="${!result }">
				<h1> ${others }  조회</h1>
			</c:if>
			
			<small style="font-size: 20px;">
			${record.nn }님에 대한 정보입니다.</small>
		</div>

		<div class="col"></div>
	</div>
	<br/>
	<br/>
	<div class="row">
		<div class="col"></div>
		<div class="col-3" style="text-align: center;">
			<c:choose>
				<c:when test="${record.auth_code ==1 }">
					<p style="display: inline;"><b>일반회원</b></p>
				</c:when>
				<c:when test="${record.auth_code ==2 }">
					<p style="font-size: 15px; color: yellowgreen; display: inline;"><b>플래티넘회원</b></p>
				</c:when>
				<c:otherwise>
					<p style="font-size: 25px; color: #23dddd; display: inline;"><b>관리자 계정</b></p>
				</c:otherwise>
			</c:choose>
			

		</div>
		<div class="col"></div>
	</div>
	<div class="row">
		<div class="col"></div>
		<div class="col-3" style="text-align: center;">
			아이디		

		</div>
		<div class="col-3">
			${record.uid }

		</div>
		<div class="col"></div>
	</div>

	<div class="row">
		<div class="col"></div>
		<div class="col-3" style="text-align: center;">
			닉네임		

		</div>
		<div class="col-3">
			${record.nn }

		</div>
		<div class="col"></div>
	</div>
	

	<div class="row">
		<div class="col"></div>
		<div class="col-3" style="text-align: center;">
			관심사		

		</div>
		<div class="col-3">
			${j:convertHabits(record.habit)}

		</div>
		<div class="col"></div>
	</div>
	

	<div class="row">
		<div class="col"></div>
		<div class="col-3"style="text-align: center;" >
			학력		

		</div>
		<div class="col-3">
			${j:convertGrade(record.grade)}
		</div>
		<div class="col"></div>
	</div>


	<div class="row">
		<div class="col"></div>
		<div class="col-3" style="text-align: center;">
			자기소개		

		</div>
		<div class="col-3">
	
			${record.comment }
		</div>
		<div class="col"></div>
	</div>
	
	<br/><br/><br/>
	<div class="row">
		<div class ="col"  >
			<ul class="actions" style="justify-content: center;">
			<li>
				<a href="<c:url value="/Index.jsik"/>" class="button primary">메인으로</a>
			</li>
			<c:if test="${result }">
				<li>
				<a href="<c:url value="/custom/Edit.jsik"/>" class="button primary">수정하기</a>
				</li>
			</c:if>
			
			
			<c:if test="${!result }">
				<li>
				<button type="button" class="button primary" onclick="fnBack()">뒤로가기</button>
				<li>
			</c:if>
			</ul>
			
		</div>
	</div>




</div>


		
				
				
		
		
<script>
	<c:if test="${!result }">
		function fnBack() {
			history.back();
		}
	</c:if>
</script>		


<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />