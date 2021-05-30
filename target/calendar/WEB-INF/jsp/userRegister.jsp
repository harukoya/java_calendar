<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ユーザ登録</title>
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.18.1/build/cssreset/cssreset-min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=M+PLUS+Rounded+1c">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/userRegister.css">
</head>
<body>
	<h1>ユーザ登録画面</h1>
	<br>
	<p>追加するユーザの情報を入力してください。</p>
	<br>
	<p>なお、本機能は管理者権限を持つユーザのみの機能となっております。</p>
	<br>
	<c:if test="${msg != null}">
		<p class="msg"><c:out value="${msg}" /></p>
	</c:if>
	<c:if test="${errmsg != null}">
		<p class="errmsg"><c:out value="${errmsg}" /></p>
	</c:if>
	<form action="/calendar/userRegister" method="post">
		<table>
			<tr>
				<td>ログインID：</td>
				<td><input type="text" name="loginId"></td>
			</tr>
			<tr>
				<td>名前</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>パスワード：</td>
				<td><input type="password" name="password">
			</tr>
			<tr>
				<td>名前</td>
				<td>
					<label><input type="radio" name="role" value="1">管理者ユーザ</label>
					<label><input type="radio" name="role" value="0">一般ユーザ</label>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="登録"></td>
			</tr>
		</table>
	</form>

</body>
</html>