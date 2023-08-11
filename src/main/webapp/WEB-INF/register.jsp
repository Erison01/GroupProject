<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
    <style>
        body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: sans-serif;
            background: linear-gradient(#30142b, #a12727);
        }
        .register-box {
            position: absolute;
            top: 50%;
            left: 50%;
            width: 400px;
            padding: 40px;
            transform: translate(-50%, -50%);
            background: rgba(0, 0, 0, .5);
            box-sizing: border-box;
            box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
            border-radius: 10px;
        }
        .register-box h3 {
            margin: 0 0 30px;
            padding: 0;
            color: yellow;
            text-align: center;
        }
        .register-box .form-group {
            position: relative;
        }
        .register-box .form-group input {
            width: 100%;
            padding: 10px 0;
            font-size: 16px;
            color: white;
            margin-bottom: 20px;
            border: none;

            outline: none;
            background: transparent;
        }
        .register-box .form-group label {
            position: absolute;
            top: 0;
            left: 0;
            padding: 10px 0;
            font-size: 15px;
            pointer-events: none;
            transition: .5s;
        }
        .register-box .form-group input:focus~label,
        .register-box .form-group input:valid~label {
            top: -20px;
            left: 0;
            color: #f68e44;
            font-size: 15px;
        }
        .register-box form a {
            position: relative;
            display: inline-block;
            padding: 10px 20px;
            color: #b79726;
            font-size: 16px;
            text-decoration: none;
            text-transform: uppercase;
            overflow: hidden;
            transition: .5s;
            margin-top: 40px;
            letter-spacing: 4px
        }
        .register-box a:hover {
            background: #f49803;

            border-radius: 5px;
            box-shadow: 0 0 5px #f4c803,
            0 0 25px #bd9d0b,
            0 0 50px #f4e403,
            0 0 100px #d5cf1e;
        }
        .register-box a span {
            position: absolute;
            display: block;
        }
        .register-box a span:nth-child(1) {
            top: 0;
            left: -100%;
            width: 100%;
            height: 2px;
            background: linear-gradient(90deg, transparent, #f4c003);
            animation: btn-anim1 1s linear infinite;
        }
        @keyframes btn-anim1 {
            0% {
                left: -100%;
            }
            50%,
            100% {
                left: 100%;
            }
        }
        .register-box a span:nth-child(2) {
            top: -100%;
            right: 0;
            width: 2px;
            height: 100%;
            background: linear-gradient(180deg, transparent, #f4bc03);
            animation: btn-anim2 1s linear infinite;
            animation-delay: .25s
        }
        @keyframes btn-anim2 {
            0% {
                top: -100%;
            }
            50%,
            100% {
                top: 100%;
            }
        }
        .register-box a span:nth-child(3) {
            bottom: 0;
            right: -100%;
            width: 100%;
            height: 2px;
            background: linear-gradient(270deg, transparent, #f4dc03);
            animation: btn-anim3 1s linear infinite;
            animation-delay: .5s
        }
        @keyframes btn-anim3 {
            0% {
                right: -100%;
            }
            50%,
            100% {
                right: 100%;
            }
        }
        .register-box a span:nth-child(4) {
            bottom: -100%;
            left: 0;
            width: 2px;
            height: 100%;
            background: linear-gradient(360deg, transparent, #f4b003);
            animation: btn-anim4 1s linear infinite;
            animation-delay: .75s
        }
        @keyframes btn-anim4 {
            0% {
                bottom: -100%;
            }
            50%,
            100% {
                bottom: 100%;
            }
        }
    </style>
</head>
<body>
<div class="register-box">
    <h3>Register</h3>
    <form:form action="/register" method="post" modelAttribute="newUser">
        <div class="form-group">
            <form:input path="firstName" id="firstName" />
            <form:label path="firstName">First Name</form:label>
            <form:errors path="firstName" class="text-danger"></form:errors>
        </div>
        <div class="form-group">
            <form:input path="lastName" id="lastName" />
            <form:label path="lastName">Last Name</form:label>
            <form:errors path="lastName" class="text-danger"></form:errors>
        </div>
        <div class="form-group">
            <form:input path="email" id="email" />
            <form:label path="email">Email</form:label>
            <form:errors path="email" class="text-danger"></form:errors>
        </div>
        <div class="form-group">
            <form:input path="password" id="password" type="password" />
            <form:label path="password">Password</form:label>
            <form:errors path="password" class="text-danger"></form:errors>
        </div>
        <div class="form-group">
            <form:input path="confirm" id="confirm" type="password" />
            <form:label path="confirm">Confirm Password</form:label>
            <form:errors path="confirm" class="text-danger"></form:errors>
        </div>
        <div>
            <input type="submit" value="Register" class="btn btn-warning w-50">
            <p></p>
            <a href="/login">Log In<span></span><span></span><span></span><span></span></a>
        </div>
    </form:form>
</div>
</body>
</html>
