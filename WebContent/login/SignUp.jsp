<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />



<style>
<!--
스크롤바 숨김
-->#myForm {
	-ms-overflow-style: none;
}

#myForm::-webkit-scrollbar {
	display: none;
}
</style>

<section>
	<div class="row">
		<div class="col" style="text-align: center">
			<h1>회원 가입</h1>
			<small style="font-size: 20px;">Join with us</small>
		</div>

	</div>
	<br /> <br />


	<div class="form-signin row">
		<div class="col"></div>
		<div class="col-6">

			<form action="<c:url value="/custom/SignUp.jsik"/>" method="post"
				id="myForm" onsubmit="return fnValidation()">

				<div class="form-floating" style="margin-bottom: 15px">
					<input type="text" class="form-control" id="floatingInput"
						placeholder="id를 입력해주세요." name="id"> <label
						for="floatingInput">ID</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="floatingPassword"
						placeholder="Password" name="pw"> <label
						for="floatingPassword">Password</label>
				</div>
				<div class="form-floating" style="margin-bottom: 15px">
					<input type="password" class="form-control"
						id="floatingPasswordConfirm" placeholder="Password"
						onkeyup="checkDiffPw(this)"> <label for="floatingPassword"
						id="pwConfirmLabel">Password Confirm</label>
				</div>

				<div class="form-floating" style="margin-bottom: 15px">
					<input type="text" class="form-control" id="nickname"
						placeholder="Password" name="nickname"> <label
						for="nickname">Nickname</label>
				</div>



				<!-- 성별 -->
				<h2 style="margin: 40px 0px 10px 0px;">성별</h2>
				<div class="row">
					<div class="col-4 col-12-small">
						<input type="radio" id="demo-priority-low" name="gender" value="man"> 
						<label for="demo-priority-low">남자</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="radio" id="demo-priority-normal" name="gender" value="woman">
						<label for="demo-priority-normal">여자</label>
					</div>
				</div>
				<!-- 관심사  -->

				<h2 style="margin: 40px 0px 10px 0px;">관심사</h2>
				<div class="row">
					<div class="col-4 col-12-small">
						<input type="checkbox" id="pol" name="inter" value="pol" /> <label
							for="pol">정치</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="eco" name="inter" value="eco" /> <label
							for="eco">경제</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="ent" name="inter" value="ent" /> <label
							for="ent">연예</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="spo" name="inter" value="spo" /> <label
							for="spo">스포츠</label>
					</div>
				</div>

				<br />
				<!-- 학력 -->
				<div class="row">
					<h2 style="margin: 20px 0px;">
						<b>학력</b>
					</h2>
					<div class="col-4 col-12-sm">
						<select class="form-select form-select-sm"
							aria-label=".form-select-sm example" id="grade" name="grade">
							<option value="" selected>학력을 선택해주세요</option>
							<option value="ele">초등학교</option>
							<option value="mid">중학교</option>
							<option value="high">고등학교</option>
							<option value="uni">대학교</option>
						</select>
					</div>
				</div>


				<br />
				<!-- 자기 소개  이거 bootstrap이 길이 자꾸 자동으로 계산함 알아내서 바꿔야함-->
				<div class="row">
					<h2 style="margin: 20px 0px;">
						<b>자기소개</b>
					</h2>
					<div class="field col">
						<div class="textarea-wrapper" style="height: 180px;">
							<textarea name="comment" id="message" placeholder="Write here"
								style="overflow: hidden; resize: none; height: 180px;"></textarea>
							<div style="margin-top: 35px">
								<input type="submit" class="primary" value="등록" /> <input
									type="button" class="primary" onclick="fnCancel()" value="취소" />

							</div>
						</div>

					</div>
				</div>
				<!-- 
					<div class="form-floating">
					  <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 200px"></textarea>
					  <label for="floatingTextarea2">자기소개</label>
					</div>
					
					 -->
				<!-- buttons -->


			</form>
		</div>
		<div class="col"></div>
	</div>


</section>

<script>


	var id = document.getElementsByName('id')[0];
	var pw = document.getElementById('floatingPassword');
	var pw_confirm = document.getElementById('floatingPasswordConfirm');
	var grade = document.getElementById('grade')
	var elements = document.getElementsByName('inter');
	var comments = document.getElementsByName('comment')[0];
	var gender = document.getElementsByName('gender');
	var nickname = document.getElementsByName('nickname')[0];
	var radioChecker = false;
	
	
	function fnValidation() {
		if(id.value.length==0){
			alert('아이디를 입력하세요');
			return false;
		}

		if(pw.value.length==0 || pw_confirm.value ==0){
			alert('비밀번호를 입력하세요.');
			return false;
		}else if (pw.value != pw_confirm.value){

			alert('두 비밀번호가 일치하지 않습니다!');
			return false;
		}
		
		if(nickname.value.length==0){
			alert('닉네임을 입력하세요');
			return false;
		}
		
		gender.forEach(ele =>{
			if(ele.checked == true){
				radioChecker = true;
			}
		})
		
		if(!radioChecker){
			alert('성별을 선택하세요');
			return false;
		}
		
		
		if(grade.value.length==0){
			alert('학력을 선택하세요');
			return false;
		}

		if(comments.value.length==0){
			alert('자기소개를 작성하세요');
			return false;
		}
		
		
		return true;
	}
	
	
	function checkDiffPw(e){
		var pw =  document.getElementById('floatingPassword');
		
		var pw_con_label = document.getElementById('pwConfirmLabel');
		if(pw.value == e.value){
			e.style.color='green';
			pw_con_label.style.color= '#585858';
			pw_con_label.innerHTML='Password Confirm'
			
		}else{
			e.style.color='red';
			pw_con_label.style.color='red';
			pw_con_label.innerHTML='비밀번호가 일치하지 않습니다.'
		}
		
	}
	
	function fnCancel(){
		history.back();
	}
	
	
	
</script>

<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />