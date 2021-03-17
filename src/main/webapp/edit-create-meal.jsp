<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><%= request.getParameter("text") %> meal</title>
</head>
<body>
<h2><%= request.getParameter("text") %> meal</h2>
<h3><a href="meals">Back</a></h3>
<form method="post" action="meals?id=${meal.id}">
    <p>
        <label for="localdate">Date and time </label>
        <input type="datetime-local" autofocus id="localdate" name="dateTime"
               value="<c:out value="${meal.dateTime}"/>"/>
    </p>
    <p>
        <label for="description">Description: </label>
        <input type="text" id="description" name="description" value="<c:out value="${meal.description}" />"/>
    </p>
    <p>
        <label for="calories">Calories: </label>
        <input type="number" id="calories" name="calories" value="<c:out value="${meal.calories}" />"/>
    </p>
    <p>
        <button type="submit">Submit</button>
        <button type="reset">Reset</button>
    </p>
</form>
</body>
</html>
