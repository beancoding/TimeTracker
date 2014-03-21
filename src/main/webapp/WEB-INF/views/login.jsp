<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>login</title>
</head>

<body>
	<c:if test="${not empty error}">
		<div class="errorblock"> 
			*${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}. The user name or password is incorrect
		</div>
	</c:if>
	
	<form name='f' action="<c:url value='jsecuritycheck' />" method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value=''>
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit" value="login" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>

</html>