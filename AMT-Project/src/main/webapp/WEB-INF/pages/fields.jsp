<%--
  Created by IntelliJ IDEA.
  User: andresmoreno
  Date: 07.11.19
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
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

    <%--Data table    --%>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>
<%--    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css"/>--%>
<%--    <script type="text/javascript" charset="utf8"--%>
<%--            src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>--%>

    <title>Data</title>
</head>

<body>
<p class="wrapper">
    <div class="profile-background">
        <div class="filter-black"></div>
    </div>
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
                <c:when test="${!fields.isEmpty()}">
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
                    <a href="fields?page=${page - 1}">Previous page</a>
                </c:if>
            </div>
            <div class="col-xs-4 text-center">${page}</div>
            <div class="col-xs-4 text-right">
                <c:if test="${fields.size() >= amount}">
                    <a href="fields?page=${page + 1}">Next page</a>
                </c:if>
            </div>
        </div>
    </div>

    <footer class="footer-demo section-nude">
        <div class="container">
            <nav class="pull-left">

                </ul>
            </nav>
            <div class="copyright pull-right">&copy; 2019, AMT PROJECT</div>
        </div>
    </footer>

</div>

<script language="javascript" type="text/javascript">

    // $(document).ready(function () {
    //     $('#example').DataTable({
    //         scrollY: 400
    //     });
    // });
</script>



</body>
</html>
