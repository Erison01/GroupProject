<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(#603c52, #e76f6f);
        }

        .container {
            width: 70%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        h3 {
            margin-bottom: 20px;
            font-size: 24px;
            text-align: left;

        }
       a{
           text-decoration: none;
       }

        h2 {
            margin-bottom: 10px;
            font-size: 36px;
            color: dodgerblue;
        }

        h5 {
            margin-bottom: 20px;
            font-size: 16px;
            color: #0056b3;
        }

        form {
            margin-bottom: 20px;
        }

        .form-row {
            margin-bottom: 10px;
        }

        label {
            font-size: 14px;
            font-weight: bold;
            color: #333;
            margin-right: 10px;
        }

        .input {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            font-size: 14px;
            cursor: pointer;
        }

        .button:hover {
            background-color: #0056b3;
        }
        .comment-card {
            background-color: #fff;
            padding: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }

        .comment-card h4 {
            margin: 0;
            font-size: 16px;
            color: #007BFF;
        }

        .comment-card p {
            margin: 5px 0;
            font-size: 14px;
            color: #333;
        }
    </style>
    <title>New Comment</title>
</head>
<body>

<div class="container">
    <h3><a href="/books">Dashboard</a></h3>

    <h2>Book: ${book.title}</h2>

    <form:form action="/books/${book.id}/comments" method="post" modelAttribute="comment">
        <div class="form-row">
            <label for="content">Add a comment:</label>
            <form:textarea rows="4" class="input" path="content"/>
        </div>
        <div class="form-row">
            <input class="button" type="submit" value="Submit"/>
        </div>
    </form:form>

    <hr>

    <c:forEach var="comment" items="${comments}">
        <div class="comment-card">
            <h4>Added by <c:out value="${comment.creator.firstName}"></c:out> at
                <fmt:formatDate value="${comment.createdAt}" pattern="h:mm a MMMM dd"/>:
            </h4>
            <p><c:out value="${comment.content}"></c:out></p>

            <!-- Add a condition to check if the logged-in user is the creator of the comment -->
            <c:if test="${comment.creator.id == sessionScope.loggedInUserId}">
                <form action="/books/${book.id}/comments/${comment.id}/delete" method="post">
                    <input type="submit" value="Delete Comment" />
                </form>
            </c:if>
        </div>
    </c:forEach>

</div>

</body>
</html>
