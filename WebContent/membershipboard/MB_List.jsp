<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/template/Top.jsp" />
<jsp:include page="/template/Top_nav.jsp" />

<%
	//확인용
	System.out.println("<--List Confirm-->");
	System.out.println("View Name : List.jsp");
	System.out.println("URL From : "+request.getHeader("referer"));
	// \${empty sessionScope.user.pid }
	
	
	System.out.println("Session Info  : "+session.getAttribute("user"));
%>


<div class="container">

	<section>
		<h1>Membership Board</h1>
		<div class="table-wrapper">

			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<!-- 나중에 관리자 전용으로 체크박스 보이게 해놓자 여러개 삭제할때 쓰기 위해  -->
							<th class="col-xs-1" style="text-align: center;">번호</th>
							<th style="text-align: center; width: 50%;">제목</th>
							<th class="col-xs">작성자</th>
							<th class="col-xs" style="text-align: center">등록일</th>
							<th class="col-xs" style="text-align: center">조회수</th>
							<th class="col-xs" style="text-align: center">좋아요</th>
						</tr>
					</thead>
					<tbody>

						<c:if test="${empty requestScope.records}" var="result">
							<tr>

								<td colspan="6" style="text-align: center">등록된 게시물이 없습니다</td>

							</tr>
						</c:if>

						<c:if test="${!result }">
							<c:forEach var="item" items="${records}" varStatus="loop">

								<tr>
									<td class="col-xs-1" style="text-align: center">${records.size() - loop.index}</td>

									<td class="col-xs"><a
										href="<c:url value="/memboard/View.jsik?no=${item.no }" />&${query}nowPage=${nowPage}">${item.title}${empty item.attachFile ? '':'📋' }</a></td>
									<td class="col-xs-1"><c:if
											test="${ sessionScope.user.auth_code >= 2 }" var="tag_result">
											<a
												href="<c:url value="/custom/MyPage.jsik"/>?others=${item.pid}&request_code=104">
												${item.nickname } </a>
										</c:if> <c:if test="${! tag_result }" var="tag_result">
								 	
								 	${item.nickname }
								 </c:if></td>
									<td class="col-xs-1" style="text-align: center">${item.postDate }</td>
									<td class="col-xs-1" style="text-align: center">${item.viewCount }</td>
									<td class="col-xs-1" style="text-align: center">${item.like }</td>
								</tr>


							</c:forEach>


						</c:if>



					</tbody>
				</table>

			</div>
			<div class="container">
				<div class="row">
					<div class="col-4" style="text-align: left">

						<button onclick="fnWrite()">글쓰기</button>

					</div>
					<div class="col" style="text-align: right">
						<!-- 제목/글 종류 선택자  ,text area-->
						<form action="#">
							<select name="searchKind"
								style="display: inline; width: auto; text-align: center;">
								<option value="title" selected="selected">제목</option>
								<option value="content">내용</option>
								<option value="title_content">제목+내용</option>
							</select> <input type="text" name="searchKeyword" placeholder="입력하세요"
								style="display: inline; width: auto;" />
							<button type="submit">검색</button>
						</form>

					</div>
				</div>
				<div class="row">
					<div class="col" style="text-align: center">${paging }</div>
				</div>
			</div>
		</div>

	</section>
</div>


<script>
	function fnWrite() {
		console.log('여기당!');
		location.href='<c:url value="/memboard/Write.jsik"/>';
	}

</script>



<!-- Footer -->
<jsp:include page="/template/Footer.jsp" />