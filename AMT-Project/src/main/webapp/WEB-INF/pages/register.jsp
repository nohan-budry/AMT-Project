<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="icon" type="image/png" href="./assets/paper_img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title>Register</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>

    <base href="${pageContext.request.contextPath}/"/>

    <link href="./bootstrap3/css/bootstrap.css" rel="stylesheet"/>
    <link href="./assets/css/ct-paper.css" rel="stylesheet"/>
    <link href="./assets/css/demo.css" rel="stylesheet"/>
    <link href="./assets/css/examples.css" rel="stylesheet"/>

    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>

</head>
<body>
<div class="wrapper">
    <div class="register-background">
        <div class="filter-black"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1 ">
                    <div class="register-card">
                        <h3 class="title">Farmer Registration</h3>
                        <form action="registration" method="post" class="register-form">
                            <label>Username</label>
                            <input type="text" class="form-control" name="username"
                                   placeholder="Username" value="${param.username}">
                            <label>First Name</label>
                            <input type="text" class="form-control" name="firstName"
                                   placeholder="First Name" value="${param.firstName}">
                            <label>Last Name</label>
                            <input type="text" class="form-control" name="lastName"
                                   placeholder="last name" value="${param.lastName}">
                            <label>E-Mail</label>
                            <input type="text" class="form-control" name="email"
                                   placeholder="E-Mail" value="${param.email}">
                            <label>Address</label>
                            <input type="password" class="form-control" name="address"
                                   placeholder="Address" value="${param.address}">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" placeholder="Password">
                            <label>Confirm Password</label>
                            <input type="text" class="form-control" name="confirm" placeholder="Confirm Password">
                            <button type="submit" name="register" class="btn btn-danger btn-block">Register</button>
                        </form>
                        <ul>
                            <c:forEach items="${errors}" var="error">
                                <li style="color: red;">${error.value}</li>
                            </c:forEach>
                        </ul>
                        <div>
                            <a href="login" class="btn btn-simple btn-secondary btn-block">Login</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer register-footer text-center">
            <h6>&copy; 2019, Amt Project</h6>
        </div>
    </div>
</div>
<script src="./assets/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="./assets/js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>

<script src="./bootstrap3/js/bootstrap.js" type="text/javascript"></script>

<%--<!--  Plugins -->--%>
<script src="./assets/js/ct-paper-checkbox.js"></script>
<script src="./assets/js/ct-paper-radio.js"></script>
<script src="./assets/js/bootstrap-select.js"></script>
<script src="./assets/js/bootstrap-datepicker.js"></script>

<script src="./assets/js/ct-paper.js"></script>
</body>
</html>