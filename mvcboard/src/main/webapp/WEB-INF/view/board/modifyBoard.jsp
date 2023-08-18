<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>수정하기</h1>
	<form action="/board/modifyBoard" method="post">
	<div>
		<input type="hidden" name="boardNo" value="${boardNo}">
		<input type="hidden" name="memberId" value="${memberId}">
	</div>
		<table>
			<tr>
				<td>local_name</td>
				<td>
					<input type="text" name="localName">
				</td>
			</tr>
			<tr>
				<td>board_title</td>
				<td>
					<input type="text" name="boardTitle">
				</td>
			</tr>
			<tr>
				<td>board_content</td>
				<td>
					<textarea name="boardContent" rows="5" cols="50"></textarea>
				</td>
			</tr>
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>