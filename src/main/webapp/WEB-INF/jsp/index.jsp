<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>スケジュール管理へようこそ</title>
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.18.1/build/cssreset/cssreset-min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=M+PLUS+Rounded+1c">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/index.css">
</head>
<body>
	<h1>スケジュール管理へようこそ</h1>
	<br>
	<p>スケジュール管理をご利用いただくには、ログインが必要です。</p>
	<br>
	<p>ログインIDとパスワードを入力し、ログインボタンを押してください。</p>
	<br>
	<c:if test="${errmsg != null}">
		<p class="errmsg"><c:out value="${errmsg}" /></p>
	</c:if>
	<form action="/calendar/login" method="post">
		<table>
			<tr>
				<td>ログインID：</td>
				<td><input type="text" name="loginId"></td>
			</tr>
			<tr>
				<td>パスワード：</td>
				<td><input type="password" name="password">
			</tr>
			<tr>
				<td><input type="submit" value="ログイン"></td>
			</tr>
		</table>
	</form>
</body>
</html>