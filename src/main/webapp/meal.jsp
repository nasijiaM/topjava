<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="POST" action='meals'>
                 <input type="hidden" name="mealId" value="${meal.id}">
    DateTime:    <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <br><br>
    Description: <input type="text" name="description" value="${meal.description}">
    <br><br>
    Calories:    <input type="number" name="calories" value="${meal.calories}">
    <br><br>
    <input type="submit" value="Save" />
</form>
</body>
</html>
