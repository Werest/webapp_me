
<%@ page import="org.opencv.core.Core" %>
<%@ page import="com.main.zara.API" %>
<%@ page import="com.main.zara.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
<head>
    <title>Detect rectangles</title>
</head>
<body>
<h1>Хочешь найти прямоугольньники на рисунке?</h1>

<%

    int width = request.getParameter("x") == null ? 30 : Integer.parseInt(request.getParameter("x"));
    int height = request.getParameter("y") == null ? 40 : Integer.parseInt(request.getParameter("y"));

%>

<form method="post">
    <input type="number" name="x" min="0" max="2147483647" value="<%=width%>" placeholder="Введите ширину">
    <input type="number" name="y" min="0" max="2147483647" value="<%=height%>" placeholder="Введите высоту">
    <input type="submit" value="Хочу проверить тебя!">
</form>



<br>
<br>
    Ширина искомая: <%=width%> <br>
    Высота искомая: <%=height%>
<br>

<%=new API().testSegments(width,height)%>

<br>

<img src="resourses/img/nomer1.png">


<hr>
Версия opencv: <b><%=Core.VERSION %></b>

</body>
</html>
