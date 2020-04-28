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
<title>Edit Course</title>
</head>
<body>

<div class="jumbotron">
	<h1 class="display-4">Edit course</h1>
	<hr class="my-4">
	
	<form:form action="/courses/${course.id}/updating" method="post" modelAttribute="course">
		<input type="hidden" name="_method" value="put">
		<p>
			<form:label path="courseName">Name:</form:label>
			<form:input path="courseName" />
			<form:errors path="courseName" />
		</p>
		<p>
			<form:label path="instructor">Instructor:</form:label>
			<form:input path="instructor" />
			<form:errors path="instructor" />
		</p>
		<p>
			<form:label path="capacity">Capacity:</form:label>
			<form:input path="capacity" />
			<form:errors path="capacity" />
		</p>
		<input class="btn btn-primary" type="submit" value="Update" />
	</form:form>
	
	<form action="/courses/${course.id}/delete" method="post">
		<input type="hidden" name="_method" value="delete">
		<input type="submit" value="Delete" class="btn btn-danger">
	</form>
	<a href="/courses" class="btn btn-secondary" role="button">Back to Dashboard</a>
	
</div>

</body>
</html>