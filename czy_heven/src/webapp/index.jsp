<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Welcome to Cozy Haven</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Great+Vibes&family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background: url("image/ch1.jpg") no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            position: relative;
        }

        .overlay {
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.4);
            z-index: 1;
        }

        .welcome-box {
            position: relative;
            z-index: 2;
            max-width: 600px;
            margin: auto;
            padding: 50px 30px;
            background: rgba(0, 0, 0, 0.65);
            color: white;
            border-radius: 20px;
            text-align: center;
            top: 50%;
            transform: translateY(-50%);
        }

        .welcome-box h1 {
            font-family: 'Great Vibes', cursive;
            font-size: 3.5rem;
            margin-bottom: 10px;
        }

        .welcome-box p {
            font-size: 14px;
            color: #ccc;
            margin-bottom: 30px;
        }

        .btn-custom {
            padding: 12px 30px;
            font-size: 1rem;
            border-radius: 10px;
            margin: 10px;
            transition: all 0.3s ease-in-out;
            width: 140px;
        }

        .btn-custom:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 12px rgba(255, 255, 255, 0.3);
        }

        @media (max-width: 576px) {
            .btn-custom {
                width: 100%;
                margin: 10px 0;
            }
        }
    </style>
</head>
<body>

<!-- Dark Overlay -->
<div class="overlay" aria-hidden="true"></div>

<!-- Welcome Box -->
<div class="welcome-box" role="region" aria-label="Welcome Section">
    <h1>Welcome to Cozy Haven</h1>
    <p>Sign in or create an account to experience comfort tailored just for you.</p>

    <!-- Sign In and Sign Up Buttons -->
    <a href="owner/owner.jsp" class="btn btn-light btn-custom" role="button" aria-label="Owner">Owner</a>
    <a href="guest/guest.jsp" class="btn btn-primary btn-custom" role="button" aria-label="Guest">Guest</a>
  
</div>

</body>
</html>
