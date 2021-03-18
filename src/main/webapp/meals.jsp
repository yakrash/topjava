<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Main menu</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="edit-create-meal.jsp?text=Add">Add meal</a></h3>
<table>
    <tr style="background-color: #AFCDE7">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meals" items="${mealList}">
        <tr style="color: ${meals.excess ? "red" : "green"}">
            <javatime:format value="${meals.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate"/>
            <td>${parsedDate}</td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
            <td><a href="?action=update&id=${meals.id}">Update</a></td>
            <td><a href="?action=delete&id=${meals.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
