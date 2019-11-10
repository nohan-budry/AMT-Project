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