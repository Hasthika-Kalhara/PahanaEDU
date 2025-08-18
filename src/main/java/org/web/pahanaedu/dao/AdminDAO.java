package org.web.pahanaedu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.web.pahanaedu.model.Admin;
import org.web.pahanaedu.util.DatabaseUtil;
import org.web.pahanaedu.util.PasswordUtil;

public class AdminDAO {

    // no-arg methods

    public static boolean validateAdmin(String username, String rawPassword) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return validateAdmin(username, rawPassword, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Admin getAdminByUsername(String username) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return getAdminByUsername(username, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Admin> getAllAdmins() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return getAllAdmins(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean addAdmin(String username, String rawPassword, String role) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return addAdmin(username, rawPassword, role, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRole(String username, String newRole) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return updateRole(username, newRole, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAdmin(String username) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return deleteAdmin(username, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // methods with Connection

    public static boolean validateAdmin(String username, String rawPassword, Connection conn) {
        try {
            String sql = "SELECT password FROM admin WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
                    // Old BCrypt password
                    if (BCrypt.checkpw(rawPassword, storedPassword)) {
                        // Rehash to SHA-256
                        String shaHash = PasswordUtil.hashPassword(rawPassword);
                        String updateSql = "UPDATE admin SET password=? WHERE username=?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setString(1, shaHash);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    // SHA-256
                    return PasswordUtil.verifyPassword(rawPassword, storedPassword);
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Admin getAdminByUsername(String username, Connection conn) {
        try {
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

    public static List<Admin> getAllAdmins(Connection conn) {
        List<Admin> admins = new ArrayList<>();
        try {
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

    public static boolean addAdmin(String username, String rawPassword, String role, Connection conn) {
        try {
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);
            String sql = "INSERT INTO admin (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRole(String username, String newRole, Connection conn) {
        try {
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

    public static boolean deleteAdmin(String username, Connection conn) {
        try {
            String sql = "DELETE FROM admin WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
