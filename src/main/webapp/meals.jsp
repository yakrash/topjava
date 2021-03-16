<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse;
            color: #686461;
        }

        th {
            border-bottom: 3px solid #B9B29F;
            padding: 10px;
            text-align: left;
        }

        td {
            padding: 10px;
        }

        tr:nth-child(odd) {
            background: white;
        }

        tr:nth-child(even) {
            background: #E8E6D1;
        }
    </style>
</head>

<body>
<h3><a href="index.html">Main menu</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr style="background-color: #AFCDE7">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach var="meals" items="${mealList}">
        <tr style="color: ${meals.excess ? "red" : "green"}">
            <javatime:format value="${meals.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate"/>
            <td>${parsedDate}</td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
