<%@page import="model.MyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:include page="../template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />
<!-- 메인 컨텐츠 부분 -->

<section>
	<form id="myform" class="form-horizontal row" method="post"
		enctype="multipart/form-data" <%-- onsubmit="return fnCheck()"--%>
		action="<c:url value="/memboard/Edit.jsik?no=${record.no }"/>">

		<div class="form-group row" style="margin-bottom: 10px">
			<div class="col"></div>
			<label class="col-2 control-label" style="padding-left: 40px">Title</label>
			<div class="col-7">
				<input type="text" class="form-control" name="title"
					placeholder="제목을 입력하세요?" value="${record.title }">
			</div>
			<div class="col"></div>
		</div>




		<div class="form-group row" style="margin-bottom: 10px">
			<div class="col"></div>
			<label class="col-2 control-label"
				style="text-align: left; padding-left: 40px">파일 업로드</label>

			<div class="col-7">
				<input type="file" id="attachFile"name="attachFile" onchange="fileUpload(this)">
				<c:if test="${empty record.attachFile }" var="result">
					<p class="help-block" id="ptag">파일을 첨부하세요.</p>
				</c:if>
				<c:if test="${!result }">
					<div>
						<p class="help-block" id="ptag" onclick="fileDelete()">이전 파일 : ${record.attachFile }</p>
					</div>
				</c:if>
			</div>
			<div class="col"></div>
		</div>
		<div class="row">
			<div class="col"></div>
			<label class="col-2 control-label" style="padding-left: 40px">
				작성자 </label> <label class="col-7 control-label"
				style="padding-left: 40px; color: #585858;">
				${record.nickname } </label>
			<div class="col"></div>
		</div>

		<!-- 중첩 컬럼 사용 -->
		<div class="row">
			<div class="col"></div>
			<label class="col-2 control-label" style="padding-left: 40px">내
				용</label>
			<div class="col-7">
				<textarea rows="5" class="form-control" name="content"
					placeholder="내용을 입력하세요">${record.content }</textarea>
			</div>
			<div class="col"></div>
		</div>


		<div class="form-group row" style="margin: 30px 0; text-align: center">

			<div class="col">
				<input type="submit" class="primary" value="수정"> <input
					type="button" class="primary" onclick="fnCancel()" value="취소">
			</div>
		</div>
		<input type="hidden" name="nowPage" value="${nowPage }">
		<input type="hidden" name="query" value="${query}">
		<input type="hidden" id="prevFile" name="prevFile" value=""> <input
			type="hidden" id="tempFile" value="${record.attachFile }"
			disabled="disabled">
	</form>
</section>

<script>
	var ptag = document.getElementById('ptag');
	var prevTag = document.getElementById('prevFile');
	var tempTag = document.getElementById('tempFile');

	var once = true;
	function fnCancel() {
		history.back();
	}

	function fileUpload(fis) {

		if (fis.files && fis.files[0]) {

			var maxSize = 500 * 1024;
			var fileSize = fis.files[0].size;

			//실패시
			if (fileSize > maxSize) {
				alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.");
				$(fis).val('');
				//파일등록이 실패할경우 일단 이전파일을 유지하므로 prevFile을 null로바꾸자
				if (once) {
					ptag.innerHTML = tempTag.value
					prevTag.value =tempTag.value
				}
				return false;
			}
			//파일 업로드 성공시
			ptag.innerHTML = ''
			
			prevTag.value = tempTag.value;

		}
	}

	function fileDelete() {
		once = false;
		ptag.innerHTML='';
		prevTag.value = tempTag.value;

	}
	
	<%--	
		//attach file 파라미터 확인용 유지 보수할때 쓰시오
	
	function fnCheck() {
		
		var attach = document.getElementById('attachFile');
		console.log(attach);
		console.log(attach.value);
		
		if(confirm('attechFile : '+attach.value+', preFile : '+prevTag.value)){
			return true;
		}else{
			return false;
		}
		
	}
	--%>
</script>



<jsp:include page="../template/Footer.jsp" />
