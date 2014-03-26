<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- 		<link href="<c:url value="/resources/register.css"/>" rel="stylesheet" type="text/css" /> --%>
		<title>Register</title>
	</head>

	<body>	
		<f:form action="register" modelAttribute="User" method="POST">
			
			<f:errors/>
		
			<table>
				<tr>
					<td><f:label path="name">User name:</f:label></td>
					<td><f:input path="name" /></td>
					<td><f:errors path="name"/></td>
				</tr>
				
				<tr><td>
					<f:label path="password">Password:</f:label></td>
					<td><f:password path="password" /></td>
					<td><f:errors path="password"/>
				</td></tr>
				
				<tr><td>
					<f:label path="confirmPassword">Confirm password:</f:label></td>
					<td><f:password path="confirmPassword" /></td>
					<td><f:errors path="confirmPassword"/>
				</td></tr>
			</table>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Register"> 
		</f:form>		
	</body>
</html>