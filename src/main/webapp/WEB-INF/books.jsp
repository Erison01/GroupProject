<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            margin: 0;
            background: linear-gradient(#603c52, #e76f6f);
        }
        .navbar {
            padding: 10px 20px;
            background: linear-gradient(#30142b, #e76f6f);
            color: #0056b3;


        }
        .nav-link{
            display: inline-block;
            padding: 10px 20px;
            background-color: dodgerblue;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;


        }
        .container {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
        }
        .books-list {
            flex: 2;
            padding-right: 20px;
        }
        .add-book {
            flex: 1;
            padding-left: 20px;
            border-left: 1px solid #ddd;
        }
        ul {
            list-style: none;
            padding-left: 0;
        }
        .book-container {
            border: 1px solid #ddd;
            padding: 5px;
            margin-bottom: 5px;
            border-radius: 5px;
        }
        .favorite-label {
            display: block;
            margin-top: 5px;
            font-style: italic;
        }
        h2{
            color: #0056b3;
        }
        .book-title{
            text-decoration: none !important;
        }
    </style>
</head>
<body>
<nav class="navbar">
    <h1>Welcome, ${user.firstName}!</h1>
    <form:form class="form-inline" action="/books" method="get" >
        <input class="form-control mr-sm-2 " type="search" placeholder="Search by Title" aria-label="Search" name="title">
        <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>
    </form:form>
    <a class="nav-link" href="/logout">Logout</a>
</nav>

<div class="container">
    <div class="books-list">
        <h2>All Books</h2>
        <c:if test="${not empty books}">
            <ul>
                <c:forEach items="${books}" var="book">
                    <li class="book-container">
                        <a class="book-title" href="/books/${book.id}">
                        <p>${book.title}</p>
                        </a>
                        <p>(added by ${book.user.firstName} ${book.user.lastName})</p>
                        <span class="favorite-label">
                            <c:choose>
                                <c:when test="${user.id == book.user.id}">
                                    <p>this is one of your favorites</p>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${book.userFavorite}">
                                        this is one of your favorites
                                    </c:if>
                                    <c:if test="${not book.userFavorite}">
                                        <a href="/books/${book.id}/addFavorite">Add Favorite</a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <!-- Like button -->
                        <a href="/books/${book.id}/like">
                            <i class="fas fa-thumbs-up"></i> Like
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
    <div class="add-book">
        <h2>Add New Book</h2>
        <form:form action="/books" method="post" modelAttribute="newBook">
            <div class="form-group">
                <form:label path="title">Title:</form:label>
                <form:input path="title" class="form-control"/>
                <form:errors path="title" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <form:label path="description">Description:</form:label>
                <form:input path="description" class="form-control"/>
                <form:errors path="description" cssClass="text-danger"/>
            </div>
            <button type="submit" class="btn btn-primary">Add Book</button>
        </form:form>
    </div>
</div>
</body>
</html>
