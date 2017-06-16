<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<style>
table {
	text-align: center;
}

th {
	font-weight: bold;
}
</style>


</head>

<body>




<c:if test="${empty vinaZaStranicu}">
<h1> NEMA TAKVIH VRSTA:: <c:out value="${izabranaVrsta}"></c:out></h1>
</c:if>


<!--  ISCRTAJ SVA JEBENA SRANJA AKO IMA NECEG U LISTI '
poaskdfjis'dsofjidsof -->
<c:if test="${not empty vinaZaStranicu}">



	<table border="1">
		<tr>
			<th>Naziv</th>
			<th>Vrsta</th>
			<th>Proizvodjac</th>
			<th>Region</th>
			<th>Berba</th>
			<th>Cena</th>
		</tr>
		<c:forEach items="${vinaZaStranicu}" var="vino">
			<tr>
				<td><c:out value="${vino.naziv}" /></td>
				<td><c:out value="${vino.vrsta}" /></td>
				<td><c:out value="${vino.proizvodjac}" /></td>
				<td><c:out value="${vino.region}" /></td>
				<td><c:out value="${vino.berba}" /></td>
				<td><c:out value="${vino.vrsta}" /></td>
			</tr>
		</c:forEach>
	</table>

<form action="filtriraj-vino" method="POST">
<select name="vrsta">
						
						<option value="0">Rosé</option>
						<option value="1">Cabernet sauvignon</option>
						<option value="2">Sauvignon Blanc</option>
						<option value="3">Porto</option>
						<option value="4" selected>SVE</option>
				</select>
				<input type="submit" value="FILTRIRAJ~!!!!!!!!!">
</form>
</c:if>

</body>
</html>