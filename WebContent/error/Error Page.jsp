<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<!--
	Phantom by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>Error Page</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="<c:url value="/resource/"/>css/main.css" />
</head>


<body class="is-preload">
	<!-- Wrapper -->
	<div id="wrapper">

		<div class="row" style="min-height: 300px;"></div>

		<div class="col-12" style="min-height: 500px; text-align: center;">

			<h1>
				불편을 드려 죄송합니다.<br />페이지 오류로 빠르게 조치하도록하겠습니다.
			</h1>

		</div>

		<!-- Footer -->
		<footer id="footer">
			<div class="inner">
				<section>
					<h2>Get in touch</h2>
					<form method="post" action="#">
						<div class="fields">
							<div class="field half">
								<input type="text" name="name" id="name" placeholder="Name" />
							</div>
							<div class="field half">
								<input type="email" name="email" id="email" placeholder="Email" />
							</div>
							<div class="field">
								<textarea name="message" id="message" placeholder="Message"></textarea>
							</div>
						</div>
						<ul class="actions">
							<li><input type="submit" value="Send" class="primary" /></li>
						</ul>
					</form>
				</section>
				<section>
					<h2>Follow</h2>
					<ul class="icons">
						<li><a href="#" class="icon brands style2 fa-twitter"><span
								class="label">Twitter</span></a></li>
						<li><a href="https://www.facebook.com/yunsik.jeong.56"
							class="icon brands style2 fa-facebook-f"><span class="label">Facebook</span></a></li>
						<li><a href="https://www.instagram.com/kr_preyia/"
							class="icon brands style2 fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="https://j-sik.tistory.com/"
							class="icon brands style2 fa-dribbble"><span class="label">Dribbble</span></a></li>
						<li><a href="https://github.com/ychic"
							class="icon brands style2 fa-github"><span class="label">GitHub</span></a></li>
					</ul>
				</section>
				<ul class="copyright">
					<li>&copy; Untitled. All rights reserved</li>
					<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
				</ul>
			</div>
		</footer>

	</div>

	<!-- Scripts -->
	<script src="<c:url value="/resource/"/>js/jquery.min.js"></script>
	<script src="<c:url value="/resource/"/>js/browser.min.js"></script>
	<script src="<c:url value="/resource/"/>js/breakpoints.min.js"></script>
	<script src="<c:url value="/resource/"/>js/util.js"></script>
	<script src="<c:url value="/resource/"/>js/main.js"></script>

</body>
</html>