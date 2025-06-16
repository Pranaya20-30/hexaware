<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password - Cozy Haven</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 50px;
        }
        .reset-box {
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container d-flex justify-content-center">
    <div class="col-md-6 reset-box">
        <h3 class="text-center mb-4">Reset Your Password</h3>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>

        <form action="reset-password" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" name="email" id="email" required
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">

                <label for="newPassword" class="form-label mt-3">New Password</label>
                <input type="password" class="form-control" name="newPassword" id="newPassword" required>

                <label for="confirmPassword" class="form-label mt-3">Confirm Password</label>
                <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" required>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-warning">Reset Password</button>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="ownerlogin.jsp">Back to Login</a>
        </div>
    </div>
</div>
</body>
</html>
