<%@page import="david.ujhazi.model.ShoppingCart"%>
<%@page import="david.ujhazi.model.ShoppingCartItem"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean type="david.ujhazi.model.ShoppingCart" id="ShoppingCart"
	scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- 		if ( request.getParameter("itemId") != null ) { -->
	<!-- 			// ako smo pozvali ovaj servlet sa parametrima za dodavanje proizvoda u korpu -->
	<!-- 			try { -->
	<!-- 				Products products = (Products) getServletContext().getAttribute("products"); -->
	<!-- 				// probamo da ga dodamo -->
	<!-- 				sc.addItem(products.getProduct(request.getParameter("itemId")), -->
	<!-- 						Integer.parseInt(request.getParameter("itemCount"))); -->
	<!-- 			} catch (Exception ex) { -->
	<!-- 				ex.printStackTrace(); -->
	<!-- 			} -->
	<!-- 		} -->

	Proizvodi u korpi:
	<table>
		<tr bgcolor="lightgrey">
			<th>Naziv</th>
			<th>Jedinicna cena</th>
			<th>Komada</th>
			<th>Ukupna cena</th>
		</tr>
		<%
			double total = 0;
		%>
		<c:forEach items="${ShoppingCart.items }" var="item">
    	${item.product.name}
    	<tr>
				<td>${item.product.name}</td>
				<td>${item.product.price}</td>
				<td>${item.count}</td>
				<td><c:out value=" ${item.product.price * item.count}" /></td>

		</tr>
<!-- 			total += price; -->
		</c:forEach>
	</table>

	<p>
		Ukupno: total dinara.
	</p>

	<p>
		<a href="WebShop">Povratak</a>
	</p>


</body>
</html>