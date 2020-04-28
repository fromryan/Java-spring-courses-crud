<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/../../resources/static/css/style.css">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>
<body>

<div class="jumbotron">
	<h1 class="display-4">Welcome, ${thisUser.name} !</h1>
	<hr class="my-4">
	<h3 class="display-6">Courses</h3>
</div>

<table class="table table-hover">
	<thead>
    	<tr>
			<th scope="col">Course</th>
			<th scope="col">Instructor</th>
			<th scope="col">Capacity</th>
			<th scope="col">Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${courses}" var="course">
			<tr>
				<td><a href="/courses/${course.id}">${course.courseName}</a></td>
				<td>${course.instructor}</td>
				<td>${course.users.size()} / ${course.capacity}</td>
				<td>
					<c:choose>
						<c:when test="${course.users.contains(thisUser) == false}" >
							<form action="/courses/join" method="post">
								<input type="hidden" name="courseId" value="${course.id}" />
								<input type="hidden" name="userId" value="${thisUser.id}" />
								<input type="submit" value="Join" class="btn btn-primary" />
							</form>
			 			</c:when>
			 			<c:when test="${course.users.contains(thisUser) == true}" >
							<form action="/courses/drop" method="post">
								<input type="hidden" name="courseId" value="${course.id}" />
								<input type="hidden" name="userId" value="${thisUser.id}" />
								<input type="submit" value="Drop" class="btn btn-secondary" />
							</form>
			 			</c:when>
					</c:choose>		 					 				
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a class="btn btn-primary" href="/courses/new" role="button">Add a course</a>
<a href="/logout">Sign Out</a>


</body>
</html>