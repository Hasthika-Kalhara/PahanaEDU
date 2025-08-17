<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>PahanaEDU - Help & Support</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<!-- Main Content -->
<div class="container mt-5">
  <h2 class="text-center mb-4">Help & Support</h2>

  <!-- FAQ Section -->
  <div class="accordion mb-5" id="faqAccordion">
    <div class="accordion-item">
      <h2 class="accordion-header" id="faq1">
        <button class="accordion-button" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          How do I create a new account?
        </button>
      </h2>
      <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="faq1">
        <div class="accordion-body">
          To create a new account, click on <strong>Role Management</strong> from the dashboard
          and fill the details. The account will be created instantly.
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="faq2">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
          How can I reset my password?
        </button>
      </h2>
      <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="faq2">
        <div class="accordion-body">
          Contact <strong>Admin</strong>.
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="faq3">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          How do I contact support?
        </button>
      </h2>
      <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="faq3">
        <div class="accordion-body">
          You can contact our support team at <a href="mailto:support@pahanaedu.com">
          support@pahanaedu.com</a> or call us at +94-11-1234567.
        </div>
      </div>
    </div>
  </div>

  <!-- Contact Form -->
  <div class="card shadow-sm mb-5">
    <div class="card-header bg-primary text-white">
      Contact Support
    </div>
    <div class="card-body">
      <form action="ContactSupportServlet" method="post">
        <div class="mb-3">
          <label for="name" class="form-label">Your Name</label>
          <input type="text" class="form-control" id="name" name="name" required>
        </div>

        <div class="mb-3">
          <label for="email" class="form-label">Your Email</label>
          <input type="email" class="form-control" id="email" name="email" required>
        </div>

        <div class="mb-3">
          <label for="message" class="form-label">Your Message</label>
          <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
        </div>

        <button type="submit" class="btn btn-success">Send Message</button>
      </form>
    </div>
  </div>

  <a href="admin-dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>

  <!-- Contact Info -->
  <div class="text-center">
    <p class="mb-1">📧 support@pahanaedu.com</p>
    <p class="mb-1">📞 +94-11-1234567</p>
    <p>📍 Colombo, Sri Lanka</p>
  </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3 mt-5">
  &copy; 2025 PahanaEDU. All Rights Reserved.
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
