package org.web.pahanaedu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.web.pahanaedu.util.DatabaseUtil;
import org.web.pahanaedu.model.Admin;

public class AdminDAO {

    public static boolean validateAdmin(String username, String rawPassword) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT password FROM admin WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
                    // Password is hashed using BCrypt
                    return BCrypt.checkpw(rawPassword, storedPassword);
                } else {
                    // Password is stored in plaintext (legacy)
                    if (storedPassword.equals(rawPassword)) {
                        // Password matches, so hash and update the DB with the hash
                        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
                        String updateSql = "UPDATE admin SET password=? WHERE username=?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, hashed);
                            updateStmt.setString(2, username);
                            updateStmt.executeUpdate();
                        }
                        return true;
                    } else {
                        // Password does not match
                        return false;
                    }
                }
            }
            return false; // user not found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Admin getAdminByUsername(String username) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM admin WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setRole(rs.getString("role"));
                return admin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM admin";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setRole(rs.getString("role"));
                admins.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admins;
    }

    public static boolean addAdmin(String username, String rawPassword, String role) {
        try (Connection conn = DatabaseUtil.getConnection()) {

            // Hash the password before storing
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

            String sql = "INSERT INTO admin (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword); // store hashed password here
            stmt.setString(3, role);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRole(String username, String newRole) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE admin SET role=? WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newRole);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAdmin(String username) {
        String sql = "DELETE FROM admin WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
