<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="header">
        <title>Farmer Profile</title>
    </jsp:attribute>
    <jsp:body>
        <div class="wrapper">
            <div class="profile-content section-nude">
                <div class="container">
                    <div class="row owner">
                        <div class="col-md-2 col-md-offset-5 col-sm-4 col-sm-offset-4 col-xs-6 col-xs-offset-3 text-center">
                            <div class="avatar">
                                <img src="./assets/paper_img/potatoAvatar.jpg" alt="Circle Image"
                                     class="img-circle img-no-padding img-responsive">
                            </div>
                            <div class="name">
                                <h4>
                                    <span>${farmer.username}</span>
                                    <br>
                                    <small>${farmer.firstName} ${farmer.lastName}</small>
                                </h4>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3 text-center">
                            <p>Email: ${farmer.email}</p>
                            <c:if test="${not empty farmer.address}">
                                <p>Address: ${farmer.address}</p>
                            </c:if>
                            <btn class="btn"><i class="fa fa-cog"></i> Settings</btn>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container margin-top">
            <table id="example" class="table text-center">
                <thead>
                <tr>
                    <th>Field ID</th>
                    <th>Field Size</th>
                    <th>Issue Date</th>
                    <th>Duration</th>
                    <th>Monthly Fee</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty rights}">
                        <c:forEach items="${rights}" var="right">
                            <tr>
                                <td>${right.field.idField}</td>
                                <td>${right.field.size}</td>
                                <td>${right.issueDate}</td>
                                <td>${right.duration}</td>
                                <td>${right.monthlyFee}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">No Data available</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div class="row margin-top">
                <div class="col-xs-4">
                    <c:if test="${page > 1}">
                        <a href="profile?page=${page - 1}&amount=${amount}">Previous page</a>
                    </c:if>
                </div>
                <div class="col-xs-4 text-center">${page}</div>
                <div class="col-xs-4 text-right">
                    <c:if test="${not empty rights && rights.size() >= amount}">
                        <a href="profile?page=${page + 1}&amount=${amount}">Next page</a>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>

<%--<script src="./assets/js/jquery-1.10.2.js" type="text/javascript"></script>--%>
<%--<script src="./assets/js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>--%>

<%--<script src="./bootstrap3/js/bootstrap.js" type="text/javascript"></script>--%>

<%--<!--  Plugins -->--%>
<%--<script src="./assets/js/ct-paper-checkbox.js"></script>--%>
<%--<script src="./assets/js/ct-paper-radio.js"></script>--%>
<%--<script src="./assets/js/bootstrap-select.js"></script>--%>
<%--<script src="./assets/js/bootstrap-datepicker.js"></script>--%>

<%--<script src="./assets/js/ct-paper.js"></script>--%>