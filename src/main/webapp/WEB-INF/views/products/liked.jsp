<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

	$("#liked").click(function(){
	
		
		
		
	})
	
	
	
	
})





</script>


<style type="text/css">

table{
	width: 1000px;
	margin: 0 auto;
}
 table,th,td {
 	border:1px solid black;
 	border-collapse: collapse;
	
 }

h1{
	text-align: center;
}

</style>

<body>

<div id="producListArea">
	<h1>ProductList</h1>
	
	
		<div id="tableArea">
		<table>
			<thead>
				<tr>
					<th>SKU</th>
					<th>상품명</th>
					<th>가격</th>
					<th>조회수</th>
					<th>좋아요</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${productList}" var="productList">
					<tr>
						<td>${productList.sku }</td>
						<td>${productList.name }</td>
						<td>${productList.price }</td>
						<td></td>
						<td><input type="checkbox" name="liked" value="${productList.id}" id="liked"></td>
					</tr>		
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		<div id="btnArea">
		
			<button type="button"></button>
		</div>
</div>

</body>
</html>