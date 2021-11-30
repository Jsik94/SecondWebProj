<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/template/Top.jsp"/>
<jsp:include page="/template/Top_nav.jsp"/>



				<!-- Main -->
					<div id="main">
						<div class="inner">
							<header>
								<h1>Kosmo 2차 프로젝트에 오신것을 환영합니다.</h1>
								<p>어서오세요 메인페이지 입니다.</p>
							</header>

						</div>
						<div class ="container">
							<section class="tiles">
									<article class="style1">
										<span class="image">
											<img src="<%=request.getContextPath() %>/images/pic01.jpg" alt="" />
										</span>
										<a href="generic.html">
											<h2>구현 내용</h2>
											<div class="content">
												<p>제작자가 공부한 것들 </p>
											</div>
										</a>
									</article>
									<article class="style2">
										<span class="image">
											<img src="<%=request.getContextPath() %>/images/pic02.jpg" alt="" />
										</span>
										<a href="generic.html">
											<h1>Login</h1>
											<div class="content">
												<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor veroeros et feugiat.</p>
											</div>
										</a>
									</article>
									
									<article class="style3">
										<span class="image">
											<img src="<%=request.getContextPath() %>/images/pic03.jpg" alt="" />
										</span>
										<a href="generic.html">
											<h1>SignUp</h1>
											<div class="content">
												<p>Sed nisl arcu euismod sit amet nisi lorem etiam dolor veroeros et feugiat.</p>
											</div>
										</a>
									</article>
									
								</section>
						</div>
						
					</div>

				<!-- Footer -->

<jsp:include page="/template/Footer.jsp"/>