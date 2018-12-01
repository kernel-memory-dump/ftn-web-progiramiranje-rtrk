<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="milan.novakovic.*" %>
<jsp:useBean type="milan.novakovic.ShoppingCart" id="ShoppingCart" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Proizvodi u korpi:</h1>
		<table><tr bgcolor="lightgrey"><th>Naziv</th><th>Jedinicna cena</th><th>Komada</th><th>Ukupna cena</th></tr>
		<%! double total = 0; %>

		

		<c:forEach items="${ShoppingCart.items}" var="item">
			<tr>
			<td>${item.product.name}</td>
			<td>${item.product.price}</td>
			<td>${item.count}</td>
			<td><c:out value="${item.product.price*item.count}"></c:out></td>
			</tr>
		</c:forEach>
		</table>

		<p>
		</p>

		<p>
		<a href="WebShop">Povratak</a>
		</p>
</body>
</html>