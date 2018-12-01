<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="jquery-1.11.0.js">
</script>
<script>
$(document).ready(function(){
    $("button#b_1").click(function(){
        $("div#d_1").load("${pageContext.request.contextPath}/GetHTML");
    });   
});
</script>
</head>

<body>
<h1>AJAX load</h1>
<div id="d_1">    
</div>
<button id="b_1">Učitaj</button>
<br />
<a href="index.html">Nazad</a>

</body>
</html>
