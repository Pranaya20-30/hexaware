package com.hexaware.dao;

import com.hexaware.entity.Users;
import com.hexaware.entity.Orders;
import com.hexaware.entity.Product;

import java.sql.SQLException;

public interface IOrderRepository {
    void placeOrder(Orders order) throws SQLException;
    void createUser(Users user) throws SQLException;
    void createProduct(Users user, Product product) throws SQLException;
}