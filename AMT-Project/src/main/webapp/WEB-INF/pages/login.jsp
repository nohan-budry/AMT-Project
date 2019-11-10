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
                        <h3 class="title">Farmer Login</h3>
                        <form action="login" method="post" class="register-form">
                            <label>Username</label>
                            <input type="text" class="form-control" name="username"
                                   placeholder="Username" value="${param.username}">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" placeholder="Password">
                            <button type="submit" name="login" class="btn btn-danger btn-block">Login</button>
                        </form>
                        <c:if test="${not empty errors}">
                            <ul class="margin-top">
                                <c:forEach items="${errors}" var="error">
                                    <li style="color: red;">${error.value}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <div>
                            <a href="registration" class="btn btn-simple btn-secondary btn-block">Registration</a>
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
</body>
</html>