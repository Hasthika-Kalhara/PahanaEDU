<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .dashboard-card {
            border-radius: 15px;
            transition: transform 0.2s ease-in-out;
        }

        .dashboard-card:hover {
            transform: scale(1.02);
        }

        .icon {
            font-size: 3rem;
            color: #ffffff;
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="text-center mb-5">
        <h1 class="fw-bold">Admin Dashboard</h1>
        <p class="text-muted">Manage user accounts and roles</p>
    </div>

    <div class="row g-4 justify-content-center align-items-stretch">
        <!-- Add Account -->
        <div class="col-md-4">
            <a href="add-customer.html" class="text-decoration-none">
                <div class="card bg-success text-white dashboard-card p-4 text-center shadow h-100">
                    <div class="card-body">
                        <div class="icon mb-3">👤➕</div>
                        <h5 class="card-title">Add Account</h5>
                        <p class="card-text">Create or edit user accounts.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Role Management -->
        <div class="col-md-4">
            <a href="role-management" class="text-decoration-none">
                <div class="card bg-primary text-white dashboard-card p-4 text-center shadow h-100">
                    <div class="card-body">
                        <div class="icon mb-3">👮‍♂️🔧</div>
                        <h5 class="card-title">Role Management</h5>
                        <p class="card-text">Change roles or add new admin/staff.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Add Items -->
        <div class="col-md-4">
            <a href="items" class="text-decoration-none">
                <div class="card bg-success text-white dashboard-card p-4 text-center shadow h-100">
                    <div class="card-body">
                        <div class="icon mb-3">📚➕</div>
                        <h5 class="card-title">Add Items</h5>
                        <p class="card-text">Add or manage items.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Billing -->
        <div class="col-md-4">
            <a href="billing.jsp" class="text-decoration-none">
                <div class="card bg-warning text-dark dashboard-card p-4 text-center shadow h-100">
                    <div class="card-body">
                        <div class="icon mb-3">🧾💳</div>
                        <h5 class="card-title">Billing</h5>
                        <p class="card-text">Create and manage customer bills.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Help & Support -->
        <div class="col-md-4">
            <a href="help.jsp" class="text-decoration-none">
                <div class="card bg-info text-white dashboard-card p-4 text-center shadow h-100">
                    <div class="card-body">
                        <div class="icon mb-3">❓💬</div>
                        <h5 class="card-title">Help & Support</h5>
                        <p class="card-text">FAQs, contact info, and support form.</p>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <div class="text-center mt-5">
        <a href="logout" class="btn btn-outline-danger">Logout</a>
    </div>
</div>
</body>
</html>
