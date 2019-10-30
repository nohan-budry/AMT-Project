<%--
  Created by IntelliJ IDEA.
  User: andresmoreno
  Date: 30.10.19
  Time: 00:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Hello farm!</h2>

<ul>
    <c:forEach items="${farmers}" var="farmer">
        <li>${farmer.firstName} </li>
    </c:forEach>
    <p>potato</p>
</ul>

</body>
</html>
