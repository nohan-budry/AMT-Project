<%--
  Created by IntelliJ IDEA.
  User: andresmoreno
  Date: 07.11.19
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="header">
        <title>Fields</title>
    </jsp:attribute>
    <jsp:body>
        <c:if test="${not empty success}">
            <p class="alert alert-success">${success}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="alert alert-danger">${error}</p>
        </c:if>
        <div class="container margin-top">
            <form action="fields?page=${page}" method="post">
                <label for="newSize">Create a new field:</label>
                <input id="newSize" type="number" min="0" name="size" placeholder="Size"/>
                <button type="submit" class="btn btn-default" name="create">
                    create
                </button>
            </form>
        </div>
        <div class="container margin-top">
            <table id="example" class="table text-center" style="width:100%">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Size</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty fields}">
                        <c:forEach items="${fields}" var="field">
                            <tr>
                                <form action="fields?page=${page}" method="post">
                                    <input type="hidden" name="id" value="${field.idField}"/>
                                    <td>${field.idField}</td>
                                    <td>
                                        <input type="number" min="0" name="size" value="${field.size}"/>
                                    </td>
                                    <td>
                                        <button type="submit" class="btn btn-default" name="update">
                                            update
                                        </button>
                                        <button type="submit" class="btn btn-danger" name="delete">
                                            delete
                                        </button>
                                    </td>
                                </form>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan=3>No Data available</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div class="row margin-top">
                <div class="col-xs-4">
                    <c:if test="${page > 1}">
                        <a href="fields?page=${page - 1}&amount=${amount}">Previous page</a>
                    </c:if>
                </div>
                <div class="col-xs-4 text-center">${page}</div>
                <div class="col-xs-4 text-right">
                    <c:if test="${not empty fields && fields.size() >= amount}">
                        <a href="fields?page=${page + 1}&amount=${amount}">Next page</a>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>
