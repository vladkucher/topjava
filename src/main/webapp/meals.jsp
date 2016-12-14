<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${list}">
        <c:choose>
            <c:when test="${meal.exceed}">
                <tr bgcolor="red">
                    <td>${meal.dateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.exceed}</td>
                    <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr bgcolor="green">
                    <td>${meal.dateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.exceed}</td>
                    <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>

<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
