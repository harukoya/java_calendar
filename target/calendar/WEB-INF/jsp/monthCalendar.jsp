<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.MyCalendar" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title><c:out value="${mc.year}" />年<c:out value="${mc.month}" />月カレンダー</title>
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.18.1/build/cssreset/cssreset-min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=M+PLUS+Rounded+1c">
	<link rel="stylesheet" href="css/monthCalendar.css">
	<link rel="stylesheet" href="css/common.css">
</head>
<body>
	<div id="container">
		<h1><c:out value="${mc.year}" />年<c:out value="${mc.month}" />月のカレンダー</h1>
		<c:if test="${user.role == 1}">
			<a class="userRegister" href="
				<c:url value="/userRegister">

				</c:url>
			">ユーザ追加はこちらから</a>
		</c:if>
		<p>
			<a href="
				<c:url value="/main">
					<c:param name="year" value="${mc.year}" />
					<c:param name="month" value="${mc.month - 1}" />
				</c:url>
			" >前月</a>
			<a href="
				<c:url value="/main">
					<c:param name="year" value="${mc.year}" />
					<c:param name="month" value="${mc.month + 1}" />
				</c:url>
			">翌月</a>
		</p>
		<table>
			<tr>
				<th class="sun">日</th>
				<th>月</th>
				<th>火</th>
				<th>水</th>
				<th>木</th>
				<th>金</th>
				<th class="sat">土</th>
			</tr>
			<c:set var="showServlet" value="dayCalendar" />
			<c:forEach var="row" items="${mc.data}">
			<tr>
				<c:set var="count" value="0" />
				<c:forEach var="col" items="${row}">
					<c:choose>
						<c:when test="${count == 0}">
							<c:choose>
								<c:when test="${fn:startsWith(col, '*')}">
									<td class="today sun">
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${fn:substringAfter(col, '*')}" /></a>
										<br>
									</td>
								</c:when>
								<c:otherwise>
									<td class="sun">
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${col}" /></a>
										<br>
									</td>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${count == 6}">
							<c:choose>
								<c:when test="${fn:startsWith(col, '*')}">
									<td class="today sat">
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${fn:substringAfter(col, '*')}" /></a>
										<br>
									</td>
								</c:when>
								<c:otherwise>
									<td class="sat">
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${col}" /></a>
										<br>
									</td>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${fn:startsWith(col, '*')}">
									<td class="today">
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${fn:substringAfter(col, '*')}" /></a>
										<br>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<a href="
											<c:url value="${showServlet}">
												<c:param name="year" value="${mc.year}"/>
												<c:param name="month" value="${mc.month}" />
												<c:param name="day" value="${col}" />
											</c:url>
										" ><c:out value="${col}" /></a>
										<br>
									</td>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${count == 6}">
							<c:set var="count" value="0" />
						</c:when>
						<c:otherwise>
							<c:set var="count" value="${count + 1}" />
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>