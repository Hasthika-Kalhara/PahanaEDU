package org.web.pahanaedu.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.web.pahanaedu.dao.AdminDAO;
import org.web.pahanaedu.model.Admin;

import java.io.IOException;
import java.util.List;

public class RoleManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Admin> adminList = AdminDAO.getAllAdmins();
        request.setAttribute("adminList", adminList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("role-management.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            String username = request.getParameter("username");
            String role = request.getParameter("role");

            boolean updated = AdminDAO.updateRole(username, role);
            if (updated) {
                request.setAttribute("success", "Role updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update role.");
            }

        } else if ("add".equals(action)) {
            String newUsername = request.getParameter("newUsername");
            String newPassword = request.getParameter("newPassword");
            String newRole = request.getParameter("newRole");

            boolean added = AdminDAO.addAdmin(newUsername, newPassword, newRole);
            if (added) {
                request.setAttribute("success", "New user added successfully.");
            } else {
                request.setAttribute("error", "Failed to add user. Username might already exist.");
            }
        } else if ("delete".equals(action)) {
            String username = request.getParameter("username");
            System.out.println("Trying to delete user: " + username); // Debug print

            boolean deleted = AdminDAO.deleteAdmin(username);
            System.out.println("Deleted? " + deleted); // Debug print
            if (deleted) {
                request.setAttribute("success", "User deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete user.");
            }
    }

    doGet(request, response); // Refresh page with updated list
    }
}
