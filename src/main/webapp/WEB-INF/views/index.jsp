<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
      integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<html>
<head>
    <title>Accident</title>
</head>

<body>
<a href="<c:url value='/create'/>">Добавить инцидент</a>
<div>
    Login as : ${user.username}
</div>
<div class="container">
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">Name</th>
                <th scope="col">Description</th>
                <th scope="col">Address</th>
                <th scope="col">Type</th>
                <th scope="col">Rules</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${accidents}" var="accident">
                <tr>
                    <td>
                        <a href='<c:url value="/edit?id=${accident.id}"/>'>
                            <i class="fa fa-edit mr-3"></i>
                        </a>
                        <c:out value="${accident.id}"/></td>
                    <td><c:out value="${accident.name}"/></td>
                    <td><c:out value="${accident.text}"/></td>
                    <td><c:out value="${accident.address}"/></td>
                    <td><c:out value="${accident.type.name}"/></td>
                    <td><c:forEach var="rule" items="${accident.rules}" >

                        <c:if test="${accident.rules.contains(rule)}">
                            ${rule.name}
                        </c:if>

                    </c:forEach></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
