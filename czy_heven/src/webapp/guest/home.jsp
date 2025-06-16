<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Cozy Haven</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url("images/ch1.jpg");
            background-size: cover;
            background-position: center;
            height: 100vh;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: white;
        }
        .overlay {
            background-color: rgba(0, 0, 0, 0.5); /* semi-transparent dark overlay */
            height: 100%;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .welcome-text {
            font-size: 3rem;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="overlay">
        <div>
            <div class="welcome-text">Welcome to Cozy Haven</div>
            <p class="lead mt-3">Your comfort, our priority.</p>
        </div>
    </div>
</body>
</html>
