package org.web.pahanaedu.dao;

import org.web.pahanaedu.model.Customer;
import org.web.pahanaedu.util.DatabaseUtil;

import java.sql.*;

public class CustomerDAO {
    public static boolean addCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO customers (account_no, name, address, phone, units) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getAccountNo());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setInt(5, customer.getUnits());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE customers SET name=?, address=?, phone=?, units=? WHERE account_no=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, customer.getUnits());
            stmt.setString(5, customer.getAccountNo());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customer getCustomerByAccountNo(String accountNo) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM customers WHERE account_no=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
