<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />



<section>
	<div class="container" style="padding-right: 60px;">
		<form id="myform" class="form-horizontal" method="post"
			enctype="multipart/form-data" action="<c:url value="/memboard/Write.jsik" />">

			<div class="form-group row" style="margin-bottom: 10px">
				<label class="col-sm-2 col-md-3 control-label"
					style="padding-left: 40px">Title</label>
				<div class="col-sm-4 col-md-7">
					<input type="text" class="form-control" name="title"
						placeholder="제목을 입력하세요?">
				</div>
			</div>




			<div class="form-group row" style="margin-bottom: 10px">
				<label class="col-sm-2 col-md-3 control-label"
					style="text-align: left; padding-left: 40px">파일 업로드</label>

				<div class="col-sm-4 col-md-7">
					<input type="file" name="attachFile" onchange="fileUpload(this)">
					<p class="help-block" id="ptag">파일을 첨부하세요</p>
				</div>
			</div>


			<!-- 중첩 컬럼 사용 -->
			<div class="row">
				<label class="col-sm-2 col-md-3 control-label"
					style="text-align: left; padding-left: 40px">내 용</label>
				<div class="col-8">
					<textarea rows="5" class="form-control" name="content"
						placeholder="내용을 입력하세요"></textarea>
				</div>
			</div>


				<div class="form-group row" style="margin-top: 30px;text-align: center">

						<div class="col">
						<input type="submit" value="등록" class="primary">
						<input type="button" value="뒤로가기" class="primary" onclick="fnBack()">
						</div>
				</div>


		</form>
	</div>
</section>



<script>
function fileUpload(fis) {

	if (fis.files && fis.files[0]) {

		var maxSize = 500 * 1024;
		var fileSize = fis.files[0].size;
		var ptag =document.getElementById('ptag');
		//실패시
		if (fileSize > maxSize) {
			alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.");
			$(fis).val('');
			ptag.innerHTML = '파일을 첨부하세요.';
			return false;
		}
		ptag.innerHTML = '';
	
	}
}

function fnBack() {
	history.back();
}
</script>

<!-- Footer -->
<jsp:include page="/template/Footer.jsp" />