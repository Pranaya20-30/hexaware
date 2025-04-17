package com.hexaware.main;

import com.hexaware.dao.OrderRepositoryImpl;
import com.hexaware.entity.Orders;
import com.hexaware.entity.OrderItem;
import com.hexaware.entity.Users;
import com.hexaware.entity.Product;
import com.hexaware.exception.OrderNotFoundException;
import com.hexaware.exception.UserNotFoundException;

import java.sql.SQLException;
import java.util.*;

public class OrderManagement {
    public static void main(String[] args) throws UserNotFoundException, OrderNotFoundException {
        Scanner sc = new Scanner(System.in);
        OrderRepositoryImpl processor = new OrderRepositoryImpl();

        while (true) {
            System.out.println("\n===== Order Management Menu =====");
            System.out.println("1. Create User");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. Get All Products");
            System.out.println("6. Get Order by User");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter userId: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter username: ");
                        String uname = sc.nextLine();
                        System.out.print("Enter password: ");
                        String pwd = sc.nextLine();
                        System.out.print("Enter role (Admin/User): ");
                        String role = sc.nextLine();
                        Users user = new Users(uid, uname, pwd, role);
                        processor.createUser(user);
                    }
                    case 2 -> {
                        System.out.print("Enter Admin userId: ");
                        int aid = sc.nextInt();
                        System.out.print("Enter productId: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter product name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter stock: ");
                        int stock = sc.nextInt();
                        sc.nextLine();
                        
                        
                        System.out.print("Enter Type (Electronics/Clothing): ");
                        String type = sc.nextLine();
                        processor.createProduct(new Users(aid, "", "", "Admin"),
                                new Product(pid, name, desc, price,stock, type));
                        break;
                    }

                    case 3 -> {
                        // Create Order
                        System.out.print("Enter Order ID: ");
                        int orderId = sc.nextInt();
                        System.out.print("Enter user ID: ");
                        int userId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter number of products: ");
                        int count = sc.nextInt();
                        sc.nextLine();

                        List<OrderItem> items = new ArrayList<>();
                        for (int i = 0; i < count; i++) {
                            System.out.print("Enter Product ID " + (i + 1) + ": ");
                            int pid = sc.nextInt();
                            System.out.print("Enter Quantity: ");
                            int qty = sc.nextInt();
                            items.add(new OrderItem(0, pid, qty));
                        }

                        Orders order = new Orders(orderId, userId, new Date(), items);
                        processor.placeOrder(order);
                        System.out.println("Order placed successfully!");
                    }
                    case 4 ->{
                        System.out.print("Enter User ID: ");
                        int cuid = sc.nextInt();
                        System.out.print("Enter Order ID: ");
                        int oid = sc.nextInt();
                        processor.cancelOrder(cuid, oid);
                        break;
                    }
                    case 5 -> {
                        List<Product> products = processor.getAllProducts();
                        if (products.isEmpty()) {
                            System.out.println("No products found.");
                        } else {
                            for (Product p : products) {
                                System.out.println(p);
                            }
                        }
                    }
                    case 6 -> {
                        System.out.print("Enter user ID to view orders: ");
                        int uid = sc.nextInt();
                        List<Orders> orders = processor.getOrdersByUserId(uid);
                        if (orders.isEmpty()) {
                            System.out.println("No orders found.");
                        } else {
                            for (Orders o : orders) {
                                System.out.println("Order ID: " + o.getOrderId() + ", Status: " );
                                for (OrderItem item : o.getItems()) {
                                    System.out.println("\tProduct ID: " + item.getProductId() + ", Quantity: " + item.getQuantity());
                                }
                            }
                        }
                    }
                    case 7 -> {
                        System.out.println("Exiting... Goodbye!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
