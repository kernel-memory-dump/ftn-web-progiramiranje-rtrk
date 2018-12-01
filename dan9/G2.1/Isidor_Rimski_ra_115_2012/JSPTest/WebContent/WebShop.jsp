<%@page import="java.util.HashMap"%>
<%@page import="javax.naming.ldap.HasControls"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %>
<%@ page import="isidor.rimski.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<p> Raspolozivi proizvodi: </p>

 <% Products products = (Products) application.getAttribute("products"); %>


<%-- <%Map<Integer, Product> products = new HashMap<Integer, Product>();%> --%>
<%-- <%products.put(1, new Product("1", "TestIME", 10));%> --%>
<table border="1">
	<tr bgcolor="lightgrey">
		<th>Naziv</th>
		<th>Cena</th>
		<th>&nbsp;</th>
	</tr>
<%for( Product p : products.values() ) { %>
	<tr>
		<td> <%=p.getName()%> </td>
		<td> <%=p.getPrice()%> </td>
		<td>
			<form method="get" action="ShoppingCart">
				<input type="text" size="3" name="itemCount"/>
				<input type="hidden" name="itemId" value= p.getId() />
				<input type="submit" value="Dodaj"/>
			</form>
		</td>
	</tr>
<% } %>
</table>
</body>
</html>