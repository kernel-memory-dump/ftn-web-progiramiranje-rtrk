<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="marko.majkic.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean type="marko.majkic.model.ShoppingCart" id="ShoppingCart" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body>
<h1> Proizvodi u korpi: </h1>

<table>
	<tr bgcolor="lightgray">
		<th> Naziv </th>
		<th> Jedinicna cena </th>
		<th> Komada </th>
		<th> Ukupna cena </th>
	</tr>
	<% double total = 0; %>
	
	<c:forEach items="${ShoppingCart.items }" var="item">
    ${idem.product.name}
    <tr>
		<td> <%= i.getProduct().getName() %> </td>
		<td> <%= i.getProduct().getPrice() %> </td>
		<td> <%= i.getCount() %> </td>
		<% double price = i.getProduct().getPrice() * i.getCount(); %>
		<td> <%= price %> </td>
	</tr>
	<% total += price; %>
    	
    
    
	</c:forEach>
	
</table>
		
<p>
	Ukupno <%=total %> dinara.
</p>
		
</body>
</html>