<%@page import="uros.visekruna.model.ShoppingCart"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="uros.visekruna.model.*" %>    
<jsp:useBean type="uros.visekruna.model.ShoppingCart" id = "ShoppingCart" scope="session" /> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proizvodi u korpi</title>
</head>
<body>

		<table><tr bgcolor="lightgrey"><th>Naziv</th><th>Jedinicna cena</th><th>Komada</th><th>Ukupna cena</th></tr>
		
        <% double total = 0; %>
        
        

		<c:forEach items="${ShoppingCart.items}" var="item" >
   			 ${item.product.name}
   			 
   			<tr>
			<td>  ${item.product.name}   </td>
			<td>  ${item.product.price}   </td>
			<td>  ${item.count}   </td>
			<td>
			<c:out value=" ${item.count *item.product.price} "></c:out>
			</td>
			</tr>
			
   			 
		</c:forEach>
		
		
</table>		
        
 	<table><tr bgcolor="lightgrey"><th>Naziv</th><th>Jedinicna cena</th><th>Komada</th><th>Ukupna cena</th></tr>
		
        <% double total1 = 0; %>  
   
		<% for ( ShoppingCartItem i : ShoppingCart.getItems() ) { %>
		
			<tr>
			<td>  <%= i.getProduct().getName() %>  </td>
			<td align="center">  <%= i.getProduct().getPrice() %>  </td>
			<td align="center">  <%= i.getCount() %> </td>
			<% double price = i.getProduct().getPrice() * i.getCount(); %>
			<td> <%= price  %> </td>
			</tr>
			<% total1 += price; %>
		<% } %>
		</table>

		<p>
		Ukupno:  <%= total1  %>  dinara
		</p>

		
		
		<a href= WebShop > Povratak</a>
				
	



</body>
</html>