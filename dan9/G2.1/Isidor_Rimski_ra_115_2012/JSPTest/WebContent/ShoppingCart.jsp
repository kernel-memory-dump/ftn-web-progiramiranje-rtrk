<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="isidor.rimski.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Proizvodi u korpi:</h1>
	<table>
	<tr bgcolor="lightgrey">
		<th>Naziv</th>
		<th>Jedinicna cena</th>
		<th>Komada</th>
		<th>Ukupna cena</th>
	</tr>
	<%double total = 0;%>
	
	 <%ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart"); %>
	
	<c:forEach items="${ShoppingCart.items }" var="item">
    	${item.product.name}
    	<tr>
			<tr>
			<td>${item.product.name}</td>
			<td>${item.product.price}</td>
			<td>${item.count}</td>
			<td>
				<c:out value = "${item.product.price * item.count}" />
			</td>
			</tr>
	</c:forEach>
		
		<%for (ShoppingCartItem i : cart.getItems()) { %>
			<tr>
			<td> <%=i.getProduct().getName()%></td>
			<td><%=i.getProduct().getPrice()%></td>
			<td><%=i.getCount()%></td>
			<%double price = i.getProduct().getPrice() * i.getCount(); %>
			<td><%=price%></td>
			</tr>
			<%total += price; %>
		<% }%>
	</table>
	
	<p>Ukupno: <%=total %> dinara</p>
	<p>
		<a href="WebShop">Povratak</a>
	</p>
	
</body>
</html>

		
