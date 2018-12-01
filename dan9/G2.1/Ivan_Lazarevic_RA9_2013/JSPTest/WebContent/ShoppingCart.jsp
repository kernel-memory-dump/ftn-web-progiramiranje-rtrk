<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ivan.lazarevic.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean type="ivan.lazarevic.model.ShoppingCart" id="ShoppingCart" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>

	<h>Proizvodi u korpi:</h>
	<table>
		<tr bgcolor="lightgrey">
			<th>Naziv</th>
			<th>Jedinicna cena</th>
			<th>Komada</th>
			<th>Ukupna cena</th>
		</tr>
		<%double total = 0; %>
		
	<c:forEach items="${ShoppingCart.items}"  var="item">
	
			<tr>
			<td> ${item.product.name}  </td>
			<td> ${item.product.price} </td>
			<td> ${item.count}"</td>
			<td>
				<c:out value="${item.product.price*item.count} "/>
			</td>

			</tr>
				
	</c:forEach>
	
	</table>
		<table>
		<tr bgcolor="lightgrey">
			<th>Naziv</th>
			<th>Jedinicna cena</th>
			<th>Komada</th>
			<th>Ukupna cena</th>
		</tr>
	
		
		<%for ( ShoppingCartItem i : ShoppingCart.getItems() ) { %>
			<tr>
			<td> <%=i.getProduct().getName()%>  </td>
			<td> <%=i.getProduct().getPrice()%> </td>
			<td> <%=i.getCount() %> "</td>
			<% double price = i.getProduct().getPrice() * i.getCount(); %>
			<td> <%=price %>  </td>
			</tr>
			<% total += price;  %>
		<% } %>	
	</table>	
	
	<p> "Ukupno: " <%=total %>  dinara." </p>
	<a href="WebShop">Povratak</a>
	

</body>
</html>