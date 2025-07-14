package org.web.pahanaedu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.pahanaedu.dao.CustomerDAO;
import org.web.pahanaedu.model.Customer;

import java.io.IOException;

@WebServlet("/billing/calculate")
public class BillingServlet extends HttpServlet
{
    private final ObjectMapper mapper = new ObjectMapper();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        String accountNo = req.getParameter("accountNo");

        Customer customer = CustomerDAO.getCustomerByAccountNo(accountNo);
        if(customer == null)
        {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(res.getWriter(), new Error("Customer not found"));
            return;
        }

        double ratePerUnit = 50.0;
        double totalBill = customer.getUnits() * ratePerUnit;

        res.setContentType("application/json");
        mapper.writeValue(res.getWriter(), new BillingResponse(customer.getName(), customer.getUnits(), totalBill));
    }

    private static class BillingResponse
    {
        public String customerName;
        public int units;
        public double total;

        public BillingResponse(String customerName, int units, double total)
        {
            this.customerName = customerName;
            this.units = units;
            this.total = total;
        }
    }

    public static class Error
    {
        public String message;
        public Error(String msg)
        {
            this.message = msg;
        }
    }
}
