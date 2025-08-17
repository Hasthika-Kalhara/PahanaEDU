package org.web.pahanaedu.dao;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import org.web.pahanaedu.model.Admin;
import org.web.pahanaedu.util.PasswordUtil;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOIntegrationTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        // Create a new H2 in-memory database connection
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        try (Statement stmt = conn.createStatement()) {
            // Drop table if exists to avoid duplicate table error
            stmt.execute("DROP TABLE IF EXISTS admin");

            // Create fresh admin table
            stmt.execute("CREATE TABLE admin (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50), " +
                    "password VARCHAR(255), " +
                    "role VARCHAR(50))");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    void testAddAndGetAdmin() throws Exception {
        String username = "admin";
        String password = "pass123";
        String role = "super";

        AdminDAO.addAdmin(username, password, role, conn);
        Admin admin = AdminDAO.getAdminByUsername(username, conn);

        assertNotNull(admin);
        assertEquals(username, admin.getUsername());
        assertTrue(PasswordUtil.verifyPassword(password, admin.getPassword()));
        assertEquals(role, admin.getRole());
    }

    @Test
    void testValidateAdmin_SHA256() throws Exception {
        String username = "admin";
        String password = "pass123";
        AdminDAO.addAdmin(username, password, "super", conn);

        boolean valid = AdminDAO.validateAdmin(username, password, conn);
        assertTrue(valid);
    }

    @Test
    void testValidateAdmin_BCryptMigration() throws Exception {
        String username = "admin";
        String password = "pass123";

        // Insert old BCrypt hash
        String bcryptHash = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO admin (username, password, role) VALUES (?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, bcryptHash);
        stmt.setString(3, "super");
        stmt.executeUpdate();

        boolean valid = AdminDAO.validateAdmin(username, password, conn);
        assertTrue(valid);

        // Confirm password rehashed with SHA-256
        Admin admin = AdminDAO.getAdminByUsername(username, conn);
        assertTrue(PasswordUtil.verifyPassword(password, admin.getPassword()));
    }

    @Test
    void testGetAllAdmins() throws Exception {
        AdminDAO.addAdmin("admin1", "pass1", "super", conn);
        AdminDAO.addAdmin("admin2", "pass2", "staff", conn);

        List<Admin> admins = AdminDAO.getAllAdmins(conn);
        assertEquals(2, admins.size());
        assertEquals("admin1", admins.get(0).getUsername());
        assertEquals("admin2", admins.get(1).getUsername());
    }

    @Test
    void testUpdateRole() throws Exception {
        AdminDAO.addAdmin("admin", "pass123", "super", conn);

        boolean updated = AdminDAO.updateRole("admin", "staff", conn);
        assertTrue(updated);

        Admin admin = AdminDAO.getAdminByUsername("admin", conn);
        assertEquals("staff", admin.getRole());
    }

    @Test
    void testDeleteAdmin() throws Exception {
        AdminDAO.addAdmin("admin", "pass123", "super", conn);

        boolean deleted = AdminDAO.deleteAdmin("admin", conn);
        assertTrue(deleted);

        Admin admin = AdminDAO.getAdminByUsername("admin", conn);
        assertNull(admin);
    }
}
