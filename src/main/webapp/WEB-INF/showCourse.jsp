<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${course.id}</title>
<style>
	#button-style {
		display: inline-block;
	}
</style>
</head>
<body>

<div class="jumbotron">
	<h1 class="display-4">Course Information</h1>
	<hr class="my-4">
	<h1>${course.courseName}</h3>
	
	<h4>Instructor: ${course.instructor}</h4>
	<h4>Enrolled: <strong>${totalEnrolled}</strong> / ${course.capacity} (${course.capacity - totalEnrolled} seats left)</h4>
</div>
<ul>
	<c:forEach items="${students}" var="student">
		<li>${student.name}</li>
	</c:forEach>
</ul>
<div id="button-style">
	<a href="/courses/${course.id}/edit" class="btn btn-primary" id="button-style">Edit</a>
	<form action="/courses/${course.id}/delete" method="post" id="button-style">
		<input type="hidden" name="_method" value="delete">
		<input type="submit" value="Delete" class="btn btn-danger">
	</form>
	<a href="/courses" class="btn btn-secondary" role="button" id="button-style">Back to Dashboard</a>
</div>
</body>
</html>