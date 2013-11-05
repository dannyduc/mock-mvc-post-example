<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 11/4/13
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Todo</title>
    <link rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<form action="/todo/add" method="post" enctype="application/x-www-form-urlencoded">
    <label >
        Title: <input name="title" value="${todo.title}">
    </label>
    <label>
        Description: <input name="description" value="${todo.description}">
    </label>
    <input type="submit" value="add"/>
</form>
</body>
</html>