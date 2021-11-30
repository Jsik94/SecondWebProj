<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<script src="https://kit.fontawesome.com/b082773a43.js" crossorigin="anonymous"></script>
	<i class="fas fa-caret-up"></i>
	<i id="like" class="far fa-heart" onclick="fnClickToggle()"></i>
	<i class="fas fa-heart"></i>
	
	<script>
		function fnClickToggle() {
			var icon = document.getElementById('like');
			if(icon.getAttribute('class')=='far fa-heart'){
				icon.setAttribute('class','fas fa-heart');
			}else{
				icon.setAttribute('class','far fa-heart');
			}
		}
	
	</script>
	
</body>
</html>