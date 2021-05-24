<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><c:out value="${year += '年' += month += '月' += day += '日' += 'のスケジュール'}" /></title>
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.18.1/build/cssreset/cssreset-min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=M+PLUS+Rounded+1c">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/dayCalendar.css">
</head>
<body>
	<h1><c:out value="${year += '年' += month += '月' += day += '日のスケジュール'}" /></h1>
	<c:if test="${msg != null}">
		<p class="msg"><c:out value="${msg}" /></p>
	</c:if>
	<c:if test="${errmsg != null}">
		<p class="errmsg"><c:out value="${errmsg}" /></p>
	</c:if>
	<div id="container">
		<%-- スケジュール部分 --%>
		<div class="calendar">
		<table>
			<tr class="header">
				<th>時間</th>
				<th>予定</th>
			</tr>
			<c:forEach begin="1" end="48" step="1" var="i">
				<tr>
				<c:choose>
					<c:when test="${i%2 == 1}">
						<td class="time">
							<fmt:parseDate value="${i/2}" pattern="H" var="parseTime" />
							<fmt:formatDate value="${parseTime}" pattern="H:mm"/>
						</td>
					</c:when>
					<c:otherwise>
						<td class="timeb">
						</td>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${widthArray[i] != 0}">
						<c:choose>
							<c:when test="${widthArray[i] != -1}">
								<td class="ex" rowspan=<c:out value="${widthArray[i]}"/>>
									<c:out value="${scheduleArray[i]}"/>
								</td>
							</c:when>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${i%2 == 1}">
								<td class="contents">
								</td>
							</c:when>
							<c:otherwise>
								<td class="contentsb">
								</td>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>

		</table>
		</div>

		<%-- フォーム部分 --%>
		<div class="form">
			<%-- dayCalendarSevletに送る --%>
			<form action="/calendar/dayCalendar" method="post" name="add_schedule">
				<input type="hidden" name="year" value=<c:out value="${year}" />>
				<input type="hidden" name="month" value=<c:out value="${month}" />>
				<input type="hidden" name="day" value=<c:out value="${day}" />>
				時刻：
				<select name="shour">
					<c:forEach begin="0" end="23" step="1" var="i">
						<option value="<c:out value="${i}"/>"><c:out value="${i += '時'}"/></option>
					</c:forEach>
				</select>
				<select name="sminute">
					<option value="0">00分</option>
					<option value="30">30分</option>
				</select>
				～
				<select name="ehour">
					<c:forEach begin="0" end="23" step="1" var="i">
						<option value="<c:out value="${i}"/>"><c:out value="${i += '時'}"/></option>
					</c:forEach>
				</select>
				<select name="eminute">
					<option value="0">00分</option>
					<option value="30">30分</option>
				</select>
				<br>
				予定：<input type="text" name="yotei">
				<br>
				メモ：<br>
				<textarea name="memo" placeholder="メモを入力"></textarea>
				<br>
				<div class="submit">
					<input type="submit" value="スケジュール登録" id="submit">
				</div>
			</form>
		</div>

	</div>
</body>
</html>