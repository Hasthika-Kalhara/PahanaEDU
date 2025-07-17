package org.web.pahanaedu.dao;

import java.sql.*;
import org.web.pahanaedu.util.DatabaseUtil;

public class AdminDAO {
    public static boolean validateAdmin(String username, String password) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // hash before comparing in production
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
