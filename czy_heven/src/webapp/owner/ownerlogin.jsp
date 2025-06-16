<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Cozy Haven</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url("images/ch1.jpg");
            background-size: cover;
            background-position: center;
            height: 100vh;
            margin: 0;
        }
        .login-box {
            background: rgba(255, 255, 255, 0.9);
            padding: 40px;
            border-radius: 15px;
            margin-top: 80px;
        }
    </style>
</head>
<body>
<div class="container d-flex justify-content-center">
    <div class="col-md-5 login-box">
        <h2 class="text-center">Login to Cozy Haven</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger mt-3">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <% if (request.getAttribute("email") != null) { %>
            <div class="alert alert-success mt-3">
                Registration successful for <strong><%= request.getAttribute("email") %></strong>. Please login.
            </div>
        <% } %>

        <form action="login" method="post">
            <div class="mb-3">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
            </div>
            <div class="mb-3">
                <input type="password" name="passwordHash" class="form-control" placeholder="Password" required>
            </div>
            <div class="d-grid">
                <button class="btn btn-primary" type="submit">Login</button>
            </div>
        <div class="text-center mt-2">
            Don't have account? <a href="owner.jsp">Register here</a>
        </div>
        </form>
      <div class="text-center mt-2"> <a href="forgotpassword.jsp">Forgot Password?</a> </div>
    </div>
</div>
</body>
</html>
