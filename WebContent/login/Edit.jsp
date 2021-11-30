<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/myTag.tld" %>
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
			<h1>내 정보 수정</h1>
			<small style="font-size: 20px;">Join with us</small>
		</div>

	</div>
	<br /> <br />


	<div class="form-signin row">
		<div class="col"></div>
		<div class="col-6">

			<form action="<c:url value="/custom/Edit.jsik"/>" method="post"
				id="myForm" onsubmit="return fnValidation()">

				<div class="form-floating" style="margin-bottom: 15px">
					<input type="text" class="form-control" id="floatingInput"
						placeholder="id를 입력해주세요." name="id" value="${record.uid }"
						readonly="readonly"> <label for="floatingInput">ID</label>
				</div>
				
				
				<input type="checkbox" id="pwChange" style="padding-right: 2px; margin-right: 3px" onchange="fnChanged(this)">
				<label for="pwChange" id="lbl_pwChange"><b>비밀번호 변경</b><span></span></label>
				<div id="forToggle">
				

				</div>


				<div class="form-floating" style="margin-bottom: 15px">
					<input type="text" class="form-control" id="nickname"
						placeholder="Password" name="nickname" value="${record.nn }"> <label
						for="nickname">Nickname</label>
				</div>
				<!-- 성별 -->
				<h2 style="margin: 40px 0px 10px 0px;">성별</h2>
				<div class="row">
					<div class="col-4 col-12-small">
						<input type="radio" id="demo-priority-low" name="gender" value = "man"  ${my:isContain(record.gender,'man') ? 'checked' : '' }>
						<label for="demo-priority-low">남자</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="radio" id="demo-priority-normal" name="gender" value = "woman" ${my:isContain(record.gender,'woman') ? 'checked' : '' }>
						<label for="demo-priority-normal">여자</label>
					</div>
				</div>
				<!-- 관심사  -->

				<h2 style="margin: 40px 0px 10px 0px;">관심사</h2>
				<div class="row">
					<div class="col-4 col-12-small">
						<input type="checkbox" id="pol" name="inter" value="pol" ${my:isContain(record.habit,'pol') ? 'checked' : '' } /> <label
							for="pol">정치</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="eco" name="inter" value="eco" ${my:isContain(record.habit,'eco') ? 'checked' : '' }/> <label
							for="eco">경제</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="ent" name="inter" value="ent" ${my:isContain(record.habit,'ent') ? 'checked' : '' }/> <label
							for="ent">연예</label>
					</div>
					<div class="col-4 col-12-small">
						<input type="checkbox" id="spo" name="inter" value="spo" ${my:isContain(record.habit,'spo') ? 'checked' : '' }/> <label
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
							<option value="" ${my:isContain(record.grade,'') ? 'selected' : '' }>학력을 선택해주세요</option>
							<option value="ele" ${my:isContain(record.grade,'ele') ? 'selected' : '' }>초등학교</option>
							<option value="mid" ${my:isContain(record.grade,'mid') ? 'selected' : '' }>중학교</option>
							<option value="high" ${my:isContain(record.grade,'high') ? 'selected' : '' }>고등학교</option>
							<option value="uni" ${my:isContain(record.grade,'uni') ? 'selected' : '' }>대학교</option>
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
								style="overflow: hidden; resize: none; height: 180px;">${record.comment }</textarea>
							<div style="margin-top: 35px">
								<input type="submit" class="primary" value="등록" /> <input
									type="button" class="primary" onclick="fnCancel()" value="취소" />

							</div>
						</div>

					</div>
				</div>

			</form>
		</div>
		<div class="col"></div>
	</div>


</section>

<script>

	var pwOn = false;
	console.log('하이');
	console.log(document.getElementsByName('nickname')[0].value);
	console.log('${record.nn}');
	var compare = '${record.nn}';
	console.log(compare);
	console.log(document.getElementsByName('nickname')[0].value =='${record.nn}')
	console.log( document.getElementsByName('nickname')[0].value ==compare)
	function fnValidation() {
		
		var id = document.getElementsByName('id')[0];
		var grade = document.getElementById('grade')
		var elements = document.getElementsByName('inter');
		var comments = document.getElementsByName('comment')[0];
		var gender = document.getElementsByName('gender');
		var nickname = document.getElementsByName('nickname')[0];
		var radioChecker = false;
		
		if(id.value.length==0){
			alert('아이디를 입력하세요');
			return false;
		}
		
		if(pwOn){

			var pw = document.getElementById('floatingPassword');
			var pw_confirm = document.getElementById('floatingPasswordConfirm');
			
			if(pw.value.length==0 || pw_confirm.value ==0){
				alert('비밀번호를 입력하세요.');
				return false;
			}else if (pw.value != pw_confirm.value){

				alert('두 비밀번호가 일치하지 않습니다!');
				return false;
			}
			
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
			pw_con_label.style.color= 'green'; // origin '#585858'
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
	
	function fnChanged(e) {
		console.log("%O",e);
		console.log(e.checked);
		var forToggle = document.getElementById('forToggle');
		pwOn = true;
		if(e.checked){

			fnSetPw(forToggle);
			e.disabled='true'
			document.getElementById('lbl_pwChange').setAttribute("style", 'color:#a498f3');

		}
	}
	
	
	function fnSetPw(e){
		
		var mkDivPw = document.createElement("div");
		mkDivPw.setAttribute("class", "form-floating")
		var mkInputPw = document.createElement("input");
		mkInputPw.setAttribute("type", "password");
		mkInputPw.setAttribute("class", "form-control");
		mkInputPw.setAttribute("id","floatingPassword");
		mkInputPw.setAttribute("placeholder", "Password");
		mkInputPw.setAttribute("name", "pw");
		var mkLabelPw = document.createElement("label");
		mkLabelPw.setAttribute("for", "floatingPassword");
		mkLabelPw.innerHTML = 'Password';
		
		mkDivPw.appendChild(mkLabelPw);
		mkDivPw.insertBefore(mkInputPw, mkLabelPw)
	
		
		var mkDivPwCon = document.createElement("div");
		mkDivPwCon.setAttribute("class", "form-floating")
		mkDivPw.setAttribute("style", "margin-bottom: 15px")
		var mkInputPwCon = document.createElement("input");
		mkInputPwCon.setAttribute("type", "password");
		mkInputPwCon.setAttribute("class", "form-control");
		mkInputPwCon.setAttribute("id","floatingPasswordConfirm");
		mkInputPwCon.setAttribute("placeholder", "Password");
		mkInputPwCon.setAttribute("onkeyup", "checkDiffPw(this)")
		var mkLabelPwCon = document.createElement("label");
		mkLabelPwCon.setAttribute("for", "floatingPasswordConfirm");
		mkLabelPwCon.setAttribute("id","pwConfirmLabel");
		mkLabelPwCon.innerHTML = 'Password Confirm';
		
		mkDivPwCon.appendChild(mkLabelPwCon);
		mkDivPwCon.insertBefore(mkInputPwCon, mkLabelPwCon)
		
		
		
		e.appendChild(mkDivPw);
		e.appendChild(mkDivPwCon);
		
	}
	
	
	
	
</script>

<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />