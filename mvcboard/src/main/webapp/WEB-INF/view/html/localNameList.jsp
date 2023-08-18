<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<script>
	$(document).ready(function(){	
		// 차트에 x축과 y축에 값을 넣기위한 빈배열 선언
		const x = [];
		const y = [];
		// const barColors = ["red", "green","blue","orange","brown"];
		
		// 동기호출로 x,y값을 호출
		$.ajax({
			async : false, // true(비동기:기본값), false(동기)
			url : '/rest/localNameList',
			type : 'get',
			success : function(model) {
				// Backend Model -> Frontend Model 변경
				
				/* model -> {'model':[{localName:'부산', cnt:10},
								{localName:'서울', cnt:22}, ....]}
				*/
				model.forEach(function(item, index){
					$('#target').append('<tr>');
					$('#target').append('<td>'+item.localName+'</td>');
					$('#target').append('<td>'+item.cnt+'</td>');
					$('#target').append('</tr>');
					
					// chart모델 생성
					x.push(item.localName);
					y.push(item.cnt);
				});
			}
			
		});
		
		new Chart("targetChart", {
			  type: "bar",
			  data: {
			    labels: x,
			    datasets: [{
			      // backgroundColor: barColors,
			      data: y
			    }]
			  },
			// options: {...}
		});
	});
</script>
</head>
<body>
	<h1>Ajax API사용으로 localNameList 가져오기</h1>
	<table border="1">
		<thead>
			<tr>
				<th>지역이름</th>
				<th>게시글수</th>
			</tr>
		</thead>
		<tbody id="target">
		</tbody>
	</table>
	
	<canvas id="targetChart" style="width:100%;max-width:700px"></canvas>
</body>
</html>