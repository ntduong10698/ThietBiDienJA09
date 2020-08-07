package com.bksoftwarevn.itstudent.service;

import com.bksoftwarevn.itstudent.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Product insert(Product product)  throws Exception;

    boolean update(Product product) throws Exception;

    boolean delete(int id) throws Exception;

    List<Product> findAll() throws Exception;

    Product findById(int id) throws Exception;

    List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category, int bouth, int promotion) throws Exception;

    List<Product> sortBy(String field, boolean isAsc) throws SQLException;

    List<Product> findByCategory(int idCategory) throws Exception;

}
