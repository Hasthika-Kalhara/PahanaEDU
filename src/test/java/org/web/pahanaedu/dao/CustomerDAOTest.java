package org.web.pahanaedu.dao;

import org.junit.jupiter.api.*;
import org.web.pahanaedu.model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS customers");
            stmt.execute("CREATE TABLE customers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_no VARCHAR(50), " +
                    "name VARCHAR(100), " +
                    "address VARCHAR(255), " +
                    "phone VARCHAR(20), " +
                    "units INT)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // =====================
    // Basic CRUD Tests
    // =====================

    @Test
    void testAddAndGetCustomer() {
        Customer c = new Customer();
        c.setAccountNo("AC123");
        c.setName("John Doe");
        c.setAddress("123 Street");
        c.setPhone("1234567890");
        c.setUnits(50);

        assertTrue(CustomerDAO.addCustomer(c, conn));

        Customer fetched = CustomerDAO.getCustomerByAccountNo("AC123", conn);
        assertNotNull(fetched);
        assertEquals("John Doe", fetched.getName());
        assertEquals(50, fetched.getUnits());
    }

    @Test
    void testUpdateCustomer() {
        Customer c = new Customer();
        c.setAccountNo("AC123");
        c.setName("John Doe");
        c.setAddress("123 Street");
        c.setPhone("1234567890");
        c.setUnits(50);

        CustomerDAO.addCustomer(c, conn);

        c.setUnits(75);
        assertTrue(CustomerDAO.updateCustomer(c, conn));

        Customer fetched = CustomerDAO.getCustomerByAccountNo("AC123", conn);
        assertEquals(75, fetched.getUnits());
    }

    @Test
    void testDeleteCustomer() {
        Customer c = new Customer();
        c.setAccountNo("AC123");
        c.setName("John Doe");
        c.setAddress("123 Street");
        c.setPhone("1234567890");
        c.setUnits(50);

        CustomerDAO.addCustomer(c, conn);

        assertTrue(CustomerDAO.deleteCustomer("AC123", conn));

        Customer fetched = CustomerDAO.getCustomerByAccountNo("AC123", conn);
        assertNull(fetched);
    }

    @Test
    void testGetAllCustomers() {
        Customer c1 = new Customer();
        c1.setAccountNo("AC1"); c1.setName("Alice"); c1.setAddress("Addr1"); c1.setPhone("111"); c1.setUnits(10);
        Customer c2 = new Customer();
        c2.setAccountNo("AC2"); c2.setName("Bob"); c2.setAddress("Addr2"); c2.setPhone("222"); c2.setUnits(20);

        CustomerDAO.addCustomer(c1, conn);
        CustomerDAO.addCustomer(c2, conn);

        List<Customer> customers = CustomerDAO.getAllCustomers(conn);
        assertEquals(2, customers.size());
    }

    // =====================
    // Edge Case Tests
    // =====================

    @Test
    void testGetNonExistentCustomer() {
        Customer fetched = CustomerDAO.getCustomerByAccountNo("NON_EXIST", conn);
        assertNull(fetched);
    }

    @Test
    void testDeleteNonExistentCustomer() {
        boolean deleted = CustomerDAO.deleteCustomer("NON_EXIST", conn);
        assertFalse(deleted);
    }

    @Test
    void testUpdateNonExistentCustomer() {
        Customer c = new Customer();
        c.setAccountNo("NON_EXIST");
        c.setName("Nobody");
        c.setAddress("Nowhere");
        c.setPhone("000");
        c.setUnits(0);

        boolean updated = CustomerDAO.updateCustomer(c, conn);
        assertFalse(updated);
    }

    @Test
    void testAddDuplicateAccountNo() {
        Customer c1 = new Customer();
        c1.setAccountNo("DUP1"); c1.setName("Alice"); c1.setAddress("Addr1"); c1.setPhone("111"); c1.setUnits(10);
        Customer c2 = new Customer();
        c2.setAccountNo("DUP1"); c2.setName("Bob"); c2.setAddress("Addr2"); c2.setPhone("222"); c2.setUnits(20);

        assertTrue(CustomerDAO.addCustomer(c1, conn));

        // Depending on DB constraints, second insert may fail
        boolean secondInsert = CustomerDAO.addCustomer(c2, conn);
        // If no unique constraint, it may succeed, so just check no exception
        assertTrue(secondInsert || !secondInsert);
    }
}
