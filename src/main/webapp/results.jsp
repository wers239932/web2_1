<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="history" class="beans.History" scope="session"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>История запросов</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<h4>
    <table class="history" id="history">
        <tr class="thead">
            <th>Время</th>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
        </tr>
        <tbody>
        <c:forEach var="res" items="${history.history}">
            <tr>
                <td>${res.getTime()}</td>
                <td>${Math.round(res.point().x*100)/100}</td>
                <td>${Math.round(res.point().y*100)/100}</td>
                <td>${Math.round(res.point().r*100)/100}</td>
                <td>${res.result()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</h4>
<a href="index.jsp">вернись обратно</a>
</body>
</html>
