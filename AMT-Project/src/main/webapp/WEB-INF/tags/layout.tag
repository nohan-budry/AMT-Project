<%--
  Created by IntelliJ IDEA.
  User: nohanbudry
  Date: 10.11.19
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="icon" type="image/png" href="../assets/paper_img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

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

    <jsp:invoke fragment="header"/>
</head>
<body>
<nav class="navbar navbar-ct-transparent navbar-relative " role="navigation-demo" id="register-navbar">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navigation-example-2">
            <ul class="nav navbar-nav navbar-right">
                <%-- TODO: show rights and fields links only for admins --%>
                <li>
                    <a href="rights" class="btn btn-simple">Manage Exploitation Rights</a>
                </li>
                <li>
                    <a href="fields" class="btn btn-simple">Manage Fields</a>
                </li>
                <li>
                    <a href="profile" class="btn btn-simple">Profile</a>
                </li>
                <li>
                    <a href="logout" class="btn btn-simple">Sing Out</a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-->
</nav>
<div class="profile-background">
    <div class="filter-black"></div>
</div>
<jsp:doBody/>
<footer class="footer-demo section-nude">
    <div class="container">
        <div class="copyright pull-right">&copy; 2019, AMT PROJECT</div>
    </div>
</footer>
</body>
</html>
