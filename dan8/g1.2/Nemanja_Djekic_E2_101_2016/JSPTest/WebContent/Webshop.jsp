<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "nemanja.djekic.model.*" %>  
<%@ page import ="java.util.*" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% Products products = (Products)application.getAttribute("products"); %>

<p> Raspolozivi proizvodi </p>

<table border="1"><tr bgcolor="lightgrey"><th>Naziv</th><th>Cena</th><th>&nbsp;</th></tr>
<% for ( Product p : products.values() ) {  %>
	<tr>		
		<td>   <%=p.getName() %> </td>
		<td>  <%=p.getPrice() %>  </td>
		<td>
			<form method="get" action="ShoppingCartServlet" >
			<input type="text" size="3" name="itemCount">
			<input type="hidden" name="itemId" value="<%=p.getId() %>" >
			<input type="submit" value="Dodaj">
			</form>
		</td>
	</tr>
<% } %>
</table>

</body>
</html>