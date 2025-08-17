package org.web.pahanaedu.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.web.pahanaedu.dao.ItemDAO;
import org.web.pahanaedu.model.Item;

import java.io.IOException;
import java.util.List;

//@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Item> itemList = ItemDAO.getAllItems();
        request.setAttribute("itemList", itemList);
        request.getRequestDispatcher("items.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ItemDAO.deleteItem(id);
            session.setAttribute("message", "Item deleted successfully!");
        }
        else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setPrice(price);
            item.setQuantity(quantity);

            ItemDAO.updateItem(item);
            session.setAttribute("message", "Item updated successfully!");
        }
        else if ("add".equals(action)) {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Item item = new Item();
            item.setName(name);
            item.setPrice(price);
            item.setQuantity(quantity);

            ItemDAO.addItem(item);
            session.setAttribute("message", "Item added successfully!");
        }

        response.sendRedirect("items"); // prevent form resubmission
    }
}
