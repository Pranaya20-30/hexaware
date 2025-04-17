package com.hexaware.dao;

import com.hexaware.entity.Product;
import com.hexaware.entity.Orders;
import com.hexaware.entity.OrderItem;
import com.hexaware.entity.Users;
import com.hexaware.exception.OrderNotFoundException;
import com.hexaware.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements IOrderRepository {

    @Override
    public void placeOrder(Orders order) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String insertOrderSQL = "INSERT INTO orders (userid) VALUES (?)";
        String insertOrderItemSQL = "INSERT INTO orderitem (orderid, productid, quantity) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false);
            PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getUserId());
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemSQL);
            for (OrderItem item : order.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            conn.commit();
            System.out.println("Order placed successfully with ID: " + orderId);

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    public void createUser(Users user) throws SQLException {
        String sql = "INSERT INTO users (userid, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            throw new SQLException("Error creating user: " + e.getMessage(), e);
        }
    }

    public void createProduct(Users admin, Product product) throws SQLException {
        if (!"Admin".equalsIgnoreCase(admin.getRole())) {
            throw new SQLException("Only Admins can create products.");
        }

        String sql = "INSERT INTO products (productid, productname, description, price, quantityInStock, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantityInStock());
            stmt.setString(6, product.getType());

            stmt.executeUpdate();
            System.out.println("Product created successfully.");
        } catch (SQLException e) {
            throw new SQLException("Error creating product: " + e.getMessage(), e);
        }
    }

    // Get all orders by user ID
 // Get all orders by user ID
    public List<Orders> getOrdersByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE userid = ?";
        List<Orders> orderList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderid");
                Date orderDate = rs.getDate("orderdate");

                List<OrderItem> items = getOrderItemsByOrderId(orderId);
                Orders order = new Orders(orderId, userId, orderDate, items);
                orderList.add(order);
            }
        }
        return orderList;
    }



    // Get order items by order ID
    private List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM orderitem WHERE orderid = ?";
        List<OrderItem> items = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productid");
                int quantity = rs.getInt("quantity");
                items.add(new OrderItem(orderId, productId, quantity));
            }
        }

        return items;
    }

    // Cancel an order by order ID
    
    public void cancelOrder(int userId, int orderId) throws SQLException, OrderNotFoundException {
        // Get the connection from the DB utility class
        try (Connection conn = DBUtil.getConnection()) {

            // Check if the order exists for the user
            String check = "SELECT * FROM orders WHERE orderid = ? AND userid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(check)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, userId);
                ResultSet rs = stmt.executeQuery();
                
                if (!rs.next()) {
                    throw new OrderNotFoundException("Order not found for userId: " + userId);
                }
            }

            // Delete order items and the order itself
            String deleteItems = "DELETE FROM orderitem WHERE orderid = ?";
            String deleteOrder = "DELETE FROM orders WHERE orderid = ?";
            
            try (PreparedStatement itemStmt = conn.prepareStatement(deleteItems);
                 PreparedStatement orderStmt = conn.prepareStatement(deleteOrder)) {

                itemStmt.setInt(1, orderId);
                itemStmt.executeUpdate();

                orderStmt.setInt(1, orderId);
                orderStmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SQLException("Error during order cancellation: " + e.getMessage(), e);
        }
    }

    // Get all products
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productid");
                String productName = rs.getString("Productname");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantityInStock = rs.getInt("quantityInStock");
                String type = rs.getString("type");

                Product product = new Product(productId, productName, description, price, quantityInStock, type);
                products.add(product);
            }
        }

        return products;
    }

	public Users getUserById(int adminId) {
		// TODO Auto-generated method stub
		return null;
	}
}
