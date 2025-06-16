<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Guest Registration - Cozy Haven</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: url("images/ch1.jpg") no-repeat center center fixed;
            background-size: cover;
        }
        .overlay {
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.4);
            z-index: 1;
        }
        .form-container {
            position: relative;
            z-index: 2;
            max-width: 600px;
            margin: 80px auto;
            background: rgba(255, 255, 255, 0.95);
            padding: 40px;
            border-radius: 15px;
        }
    </style>
</head>
<body>
<div class="overlay"></div>

<div class="container">
    <div class="form-container">
        <h2 class="text-center mb-4">Register as Owner</h2>

        <form action="register" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" name="name" placeholder="Full Name" required />
            </div>
            <div class="mb-3">
                <input type="email" class="form-control" name="email" placeholder="Email Address" required />
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" name="passwordHash" placeholder="Password" required />
            </div>
            <!-- Pre-set and hidden role -->
            <input type="hidden" name="role" value="hotel_owner" />

            <div class="mb-3">
                <input type="tel" class="form-control" name="contactNumber" placeholder="Contact Number" />
            </div>
            <div class="mb-3">
                <textarea name="address" class="form-control" placeholder="Address"></textarea>
            </div>
            <button type="submit" class="btn btn-primary w-100">Register</button>
        </form>
        
        
        <div class="text-center mt-3">
            Already have an account? <a href="ownerlogin.jsp">Login here</a>
        </div>
    </div>
</div>
</body>
</html>
