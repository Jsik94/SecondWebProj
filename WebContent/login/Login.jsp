<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />


<%
	System.out.println("<--View Confirm-->");
	System.out.println("View Name : Login.jsp");
	System.out.println("URL From : "+request.getHeader("referer"));

%>

<div class="container my-5" id="main-content">
	<div class="row">
		<div class="col"></div>
		<div class="col-4" style="text-align: center">
			<h1>Login</h1>
			<small style="font-size: 20px;">Join with us</small>
		</div>

		<div class="col"></div>
	</div>
	<br /> <br />
	<div class="row">
		<div class="col"></div>
		<div class="col-6">
			<div class="form-signin">
				<form action="<c:url value="/custom/Login.jsik"/>" method="post">
					<div class="form-floating">
						<input type="text" class="form-control" id="float-id" name="id"
							placeholder="name@example.com"> <label for="float-id">ID</label>
					</div>
					<div class="form-floating">
						<input type="password" class="form-control" id="float-pw"
							name="pw" placeholder="Password"> <label for="float-pw">Password</label>
					</div>
					<div style="margin: 7px 10px;">
						<input type="checkbox" id="autosave" autocomplete="off" name="autosave" value ="on"
							style="padding-right: 2px; margin-right: 3px" onclick="fnToggle(this)"> <label
							class="btn" for="autosave"
							style="padding-right: 1.5em; padding-left: 0.5em"></label> <small>저장하기</small>
					</div>
					<div style="margin-left: 10px; margin-top: 30px">
					
						<ul class="actions">
							<li>
								<button type="submit">로그인</button>
							</li>
							<li>
								<a href="<c:url value="/custom/SignUp.jsik"/>" class="button">가입하기</a>
							</li>
						</ul>
						
					</div>
				</form>



			</div>
		</div>
		<div class="col"></div>
	</div>





</div>

<script>
	
	var cookies = document.cookie.split('=')[1];
	var check = document.getElementById("autosave");
	console.log('%O',cookies)
	if(cookies !=null){
		check.checked=true;
	 	document.getElementById('float-id').value = cookies;
	}
	
	

</script>



<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />