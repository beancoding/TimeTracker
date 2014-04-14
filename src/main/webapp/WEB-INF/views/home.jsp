<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Redirecting...  
</h1>

	<% response.sendRedirect(request.getContextPath() + "/userhome.dhtml"); %>

</body>
</html>
