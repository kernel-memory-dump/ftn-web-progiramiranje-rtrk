<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean type="models.ShoppingCart" id="ShoppingCart" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Proizvodi u korpi:
	<table>
		<tr bgcolor="lightgrey">
			<th>Naziv</th>
			<th>Jedinicna cena</th>
			<th>Komada</th>
			<th>Ukupna cena</th>
		</tr>

		<% double total = 0; %>
		<c:forEach var="i" items="${ShoppingCart.items}">

		<tr>
			<td>${ i.product.name }</td>
			<td>${ i.product.price }</td>
			<td>${ i.count }</td>
			<c:out value="${i.product.price * i.count} }"></c:out>
			<td>${ price }</td>
		</tr>
		
		</c:forEach>
	</table>

	<p>
		Ukupno:
		dinara.
	</p>

	<p>
		<a href="WebShop">Povratak</a>
	</p>

</body>
</html>