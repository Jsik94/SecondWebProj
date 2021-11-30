<%@page import="model.MemBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />

<%
	request.setCharacterEncoding("UTF-8");
	//확인용
	System.out.println("<--List Confirm-->");
	System.out.println("View Name : List.jsp");
	System.out.println("URL From : " + request.getHeader("referer"));
	// \${empty sessionScope.user.pid }

	System.out.println("Session Info  : " + session.getAttribute("user"));

	//System.out.println("Content확인용  : "+((MemBoardDTO)request.getAttribute("record")).getContent() );
%>

<!-- Main -->
<div id="main">
	<div class="inner">
		<header>
			<h1>${record.title }</h1>
			<p style="margin-bottom: 3px">${record.nickname }</p>
			<p style="display: inline-block;">${record.postDate }</p>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<p style="display: inline-block;">
				조회수 <span id="viewcount"> ${record.viewCount }</span>
			</p>
			<div class="clearfix"></div>

			<c:if test="${empty record.attachFile }" var="result">

			</c:if>
			<c:if test="${!result }">
				<section>
					<b>첨부 파일 </b> : <a id="downfile"
						href="<c:url value="/memboard/Download.jsik?no=${record.no}&attachFile=${record.attachFile }"/>">${record.attachFile}</a>
					<br /> <b>다운로드 수 </b> : <span id="downcount">${record.downCount }</span>
				</section>




			</c:if>
		</header>

	</div>
	<br /> <br />
	<!-- 게시글 -->
	<div class="inner">
		<!-- style="min-height: 300px; border: 0.5px solid #BDBDBD; border-radius: 10px; padding: 20px; margin: 0 30px 10px 30px ;" -->
		<section>
			<div
				style="min-height: 300px; border: 0.5px solid #BDBDBD; border-radius: 10px; padding: 20px; margin: 0 10px 30px 10px;">
				<p>${record.content }</p>
			</div>
		</section>
		<!-- 목록 수정 윗글 아랫글 -->
		<section>
			<div class="row" style="margin-right: 10px">
				<div class="col-6">
					<ul class="actions small">
						<%-- 관리자 권한이거나 본인 인경우에 한해서 삭제 --%>
						<c:if
							test="${(record.pid == sessionScope.user.pid) or (sessionScope.user.auth_code ==3) }">
							<li><input type="button" class="primary small" value="삭제"
								id="delete"></li>

						</c:if>
						<c:if test="${record.pid == sessionScope.user.pid }">
							<li><a
								href="<c:url value="/memboard/Edit.jsik" />?no=${record.no}&${query }nowPage=${nowPage}"
								class="button primary small">수정하기</a></li>
						</c:if>


					</ul>
				</div>
				<div class="col-6">
					<script src="https://kit.fontawesome.com/b082773a43.js"
						crossorigin="anonymous"></script>
					<ul class="actions small" style="justify-content: right;">
						<li>좋아요 &nbsp;&nbsp; <i id="like"
							class="${like =='true' ? 'fas fa-heart':'far fa-heart' }"
							onclick="fnClickToggle()"></i>
						</li>

						<li><a
							href="<c:url value ="/memboard/View.jsik"/>?no=${PrevNext.Prev}&${query }nowPage=${nowPage }"
							class="button primary small">윗글</a></li>
						<li><a
							href="<c:url value ="/memboard/View.jsik"/>?no=${PrevNext.Next}&${query }nowPage=${nowPage }"
							class="button primary small">아랫글</a></li>
						<li><a
							href="<c:url value="/memboard/List.jsik" />?no=${record.no}&${query }nowPage=${nowPage}"
							class="button primary small">목록</a></li>

					</ul>
				</div>
			</div>
		</section>

		<!-- 댓글 -->
		<section>
			<div class="inner">
				<h2>댓글</h2>
				<hr />
				<ul style="list-style: none">
					<li>
						<!-- 넘겨줘야할것  cm_no면됨 --> <c:if test="${empty record_cm }"
							var="result_cm">
							<p>댓글이 아직 없습니다.</p>

						</c:if> <c:if test="${!result_cm }">
							<c:forEach var="cm" items="${record_cm}">
								<form
									action="<c:url value ="/memboard/View.jsik"/>?no=${record.no}&${query }nowPage=${nowPage }"
									id="cm_content_delete" method="post">
									<div class="row" style="margin-right: 10px">
										<div class="col-1">
											<!-- 세션 pid와 동일할 경우에만 뿌려주자  -->

											<c:if test="${sessionScope.user.pid == cm.cus_pid }">
												<i id="cm_delete" class="far fa-trash-alt"
													onclick='fnDelete()'></i>

											</c:if>

										</div>
										<div class="col-7">${cm.com_content }</div>
										<div class="col-2" style="text-align: right">${cm.cus_nn }</div>
										<div class="col-2" style="text-align: right">${cm.postdate }</div>
									</div>
									<input type="hidden" name="nowPage"
										value="${sessionScope.user.pid }"> <input
										type="hidden" name="cm_no" value="${cm.no }">
										<input type="hidden" name="cm_command"
										value="delete">
								</form>

							</c:forEach>


						</c:if>


					</li>
				</ul>

				<!-- 넘겨줘야할것 no ,  -->
				<form
					action="<c:url value ="/memboard/View.jsik"/>?no=${record.no}&${query }nowPage=${nowPage }"
					method="post" id="cm_content_add">
					<div class="row" style="padding: 20px; margin: 0 10px 30px 10px;">


						<div class="col-10">
							<div class="textarea-wrapper ">
								<textarea name="cm_content" id="com_board"
									placeholder="댓글을 달아주세요." rows="1"
									style="overflow: hidden; resize: none; height: 59px;"></textarea>
							</div>
						</div>

						<div class="col-2">
							<ul class="actions">
								<li><input type="submit" value="쓰기" class="primary"></li>
							</ul>

						</div>


					</div>
					<input type="hidden" name="nowPage"
						value="${sessionScope.user.pid }"> <input type="hidden"
						name="no" value="${record.no }">
						<input type="hidden" name="cm_command"
										value="insert">
				</form>
				<%-- 이디자인 마음에 들어애껴둔다 
				<div
					style="border: 0.5px solid #BDBDBD; border-radius: 10px; padding: 20px; margin: 0 10px 30px 10px;">
					<div class="textarea-wrapper">
						<textarea name="demo-message" id="demo-message"
							placeholder="댓글을 달아주세요." rows="1"
							style="overflow: hidden; resize: none; height: 59px;"></textarea>
					</div>
					<p>댓글을 입력해주세요</p>
				</div>
--%>
			</div>
		</section>

	</div>


</div>


<!-- 모달용 -->
<div class="modal  fade" id="passwordModal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">비밀번호 입력창</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline" id="passwordForm" method="post"
					action="<c:url value="/memboard/Delete.jsik"/>">
					<!-- 키값 -->
					<input type="hidden" name="no" value="${record.no }" />
					<!-- 본인/관리자 판단용 -->
					<input type="hidden" name="who"
						value="${(record.pid == sessionScope.user.pid) ? 'custom' : 'admin' }" />
					<input type="hidden" name="id"
						value="${(record.pid == sessionScope.user.pid) ? record.pid : sessionScope.user.pid }" />

					<!-- 업로드된 파일명:삭제메뉴 클릭시 테이블 데이타 삭제후 업로드된 기존 파일 삭제하기 위함 -->
					<input type="hidden" name="prevFile" value="${record.attachFile}" />
					<!-- 현재 페이지번호 -->

					<input type="hidden" name="nowPage" value="${nowPage}" /> <input
						type="hidden" name="query" value="${query }" />
					<div class="form-group">
						<input type="password" class="form-control" name="pw"
							id="modal_pw" placeholder="비밀번호를 입력하세요" onkeyup="onCheckPW()" />
						<p id="modal_warn"></p>
					</div>
					<div class="form-group" style="margin-top: 20px;">
						<ul class="actions">
							<li><input id="modal_submit" type="submit" class="btn small"
								value="확인" disabled="disabled" /></li>
						</ul>


					</div>
				</form>
			</div>
		</div>


	</div>
</div>


<script>
<c:if test="${not empty record.attachFile }">
document.getElementById('downfile').onclick = function() {
	var downcount = parseInt($('#downcount').html()) + 1;
	$('#downcount').html(downcount);
};
</c:if>

<c:if test="${(record.pid == sessionScope.user.pid) or (sessionScope.user.auth_code ==3) }">
	document.getElementById('delete').onclick = function() {
			console.log($(this).html());
	
			var text = $(this).html().trim();
	
			if (confirm('정말로 삭제하시겠습니까?')) {
	
				$('.modal-title').html("삭제를 위해 비밀번호를 입력하세요.");
	
				$('#passwordModal').modal('show');
			}
		}
	
		function onCheckPW() {
			var modal_pw = document.getElementById('modal_pw');
			var modal_warn = document.getElementById('modal_warn');
			var modal_submit = document.getElementById('modal_submit');
			console.log("%O", modal_pw);
			console.log("val" + modal_pw.value);
			console.log("%O", modal_warn);
			console.log("%O", modal_submit);
			if (modal_pw.value.length == 0) {
				modal_warn.innerHTML = '비밀번호가 입력되지 않았습니다.';
				modal_warn.style.color = "red";
				modal_submit.disabled = true;
			} else {
				modal_submit.disabled = false;
			}
		}
</c:if>
function fnClickToggle() {
	var icon = document.getElementById('like');
	if(icon.getAttribute('class')=='far fa-heart'){
		
		location.href='<c:url value="/memboard/View.jsik" />?no=${record.no}&${query }nowPage=${nowPage}&like=true';
		icon.setAttribute('class','fas fa-heart');
	}else{

		location.href='<c:url value="/memboard/View.jsik" />?no=${record.no}&${query }nowPage=${nowPage}&like=false';
		icon.setAttribute('class','far fa-heart');
	}
}

function fnDelete() {
	
	alert('이게먼저일까?')
	var form = document.getElementById('cm_content_delete');
	form.submit();
}



<%--
var viewcnt = document.getElementById('viewcount');
viewcnt.innerHTML = parseInt(viewcnt.innerHTML)+1;
	--%>
</script>

<!-- Footer -->

<jsp:include page="/template/Footer.jsp" />