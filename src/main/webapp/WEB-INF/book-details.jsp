<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>

<head>
    <title>Book Details</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        .navbar {
            display: flex;
            justify-content: space-between;
            color: #0056b3;
            align-items: center;
            background: linear-gradient(#30142b, #e76f6f);
            padding: 10px 20px;

        }
        body {
            padding: 2em;
            background: linear-gradient(#603c52, #e76f6f);
        }

         h3 {
            margin-bottom: 1.5em;
             color: #0056b3;
        }

        textarea {
            width: 50%;
            margin-bottom: 1em;
            padding: 0.5em;
            resize: vertical;
        }

        button, a {
            margin-right: 0.5em;
        }
    </style>
</head>

<body>
<nav class="navbar">
    <div>
        <h1>Welcome, ${user.firstName}!</h1>
    </div>
    <div>
        <a class="nav-link" href="/books">Dashboard</a>
    </div>
</nav>
<div class="container">
    <h3>Book Details</h3>

    <div class="card">
        <div class="card-body">
            <p><strong>Title:</strong> ${book.title}</p>
            <p><strong>Description:</strong> ${book.description}</p>
            <p><strong>Added By:</strong> ${book.user.firstName} ${book.user.lastName}</p>
            <p>Created At: <fmt:formatDate value="${book.createdAt}" pattern="yyyy-MM-dd hh:mm" /></p>
            <p>Updated At: <fmt:formatDate value="${book.updatedAt}" pattern="yyyy-MM-dd hh:mm" /></p>

            <c:if test="${user.id eq book.user.id}">
                <!-- User is the creator of the book, show update form -->
                <form:form action="/books/${book.id}/update" method="post" class="form-group">
                    <textarea name="description" class="form-control" rows="5">${book.description}</textarea>
                    <button type="submit" class="btn btn-primary mt-2">Update</button>
                    <!-- Delete button -->
                    <a href="/books/${book.id}/delete" class="btn btn-danger mt-2" onclick="return confirm('Are you sure you want to delete this book?');">Delete Book</a>
                </form:form>
            </c:if>

            <div>
                <c:if test="${isFavorited}">
                    <!-- User has favorited the book -->
                    <a href="/books/${book.id}/removeFavorite" class="btn btn-danger">Un-Favorite</a>
                </c:if>

                <c:if test="${not isFavorited}">
                    <!-- User has not favorited the book -->
                    <a href="/books/${book.id}/addFavorite" class="btn btn-success">Add-Favorite</a>
                </c:if>
            </div>
        </div>
    </div>

    <h3>Users who liked this book: (${numberOfLikes} likes)</h3>
    <ul class="list-group">
        <c:forEach items="${favoriteUsers}" var="favoriteUser">
            <li class="list-group-item">${favoriteUser.firstName} ${favoriteUser.lastName}</li>
        </c:forEach>
    </ul>

    <div class="mt-3 mb-3">
        <a href="/books/${book.id}/comments" class="btn btn-info">Add Comments</a>
    </div>

</div>

</body>
</html>
