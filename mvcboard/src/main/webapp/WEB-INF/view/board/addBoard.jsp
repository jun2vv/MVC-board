<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 입력</h1>
	<form action="/board/addBoard" method="post" enctype="multipart/form-data">
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
			<tr>
				<td>member_id</td>
				<td>
					<input type="text" name="memberId">
				</td>
			</tr>
			<tr>
				<td>
					<input type="file" name="multipartFile" multiple="multiple">
				</td>
			</tr>
		</table>
		<button type="submit">추가</button>
	</form>
</body>
</html>