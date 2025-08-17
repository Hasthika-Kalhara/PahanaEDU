package org.web.pahanaedu.dao;

import org.web.pahanaedu.model.Customer;
import org.web.pahanaedu.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ===========================
    // Production methods (no-arg)
    // ===========================

    public static boolean addCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return addCustomer(customer, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return updateCustomer(customer, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customer getCustomerByAccountNo(String accountNo) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return getCustomerByAccountNo(accountNo, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Customer> getAllCustomers() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return getAllCustomers(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean deleteCustomer(String accountNo) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            return deleteCustomer(accountNo, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===========================
    // Testable methods (with Connection)
    // ===========================

    public static boolean addCustomer(Customer customer, Connection conn) {
        String sql = "INSERT INTO customers (account_no, name, address, phone, units) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNo());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setInt(5, customer.getUnits());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(Customer customer, Connection conn) {
        String sql = "UPDATE customers SET name=?, address=?, phone=?, units=? WHERE account_no=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, customer.getUnits());
            stmt.setString(5, customer.getAccountNo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customer getCustomerByAccountNo(String accountNo, Connection conn) {
        String sql = "SELECT * FROM customers WHERE account_no=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setAccountNo(rs.getString("account_no"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                c.setUnits(rs.getInt("units"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Customer> getAllCustomers(Connection conn) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setAccountNo(rs.getString("account_no"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                c.setUnits(rs.getInt("units"));
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static boolean deleteCustomer(String accountNo, Connection conn) {
        String sql = "DELETE FROM customers WHERE account_no=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
