package com.bksoftwarevn.itstudent.service_impl;

import com.bksoftwarevn.itstudent.model.Product;
import com.bksoftwarevn.itstudent.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductService_Impl implements ProductService {
    @Override
    public Product insert(Product product) throws Exception {
        //kiểm tra category có tồn tại không
        return null;
    }

    @Override
    public boolean update(Product product) throws Exception {
        //kiểm tra category có tồn tại không
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        return false;
    }

    @Override
    public List<Product> findAll() throws Exception {
        return null;
    }

    @Override
    public Product findById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category, int bouth, int promotion) throws Exception {
        return null;
    }

    @Override
    public List<Product> sortBy(String field, boolean isAsc) throws SQLException {
        return null;
    }

    @Override
    public List<Product> findByCategory(int idCategory) throws Exception {
        return null;
    }
}
