package com.bksoftwarevn.itstudent.controller;

import com.bksoftwarevn.itstudent.model.Category;
import com.bksoftwarevn.itstudent.model.JsonResult;
import com.bksoftwarevn.itstudent.service.CategoryService;
import com.bksoftwarevn.itstudent.service_impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", value = "/api/v1/category/*")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private JsonResult jsonResult = new JsonResult();

    //thực hiện đối với các chức năng thêm category
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Post");
    }

    //thực hiện với các chức năng tìm kiếm category
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //find-by-id
        //find-all
        String pathInfor = request.getPathInfo();
        String rs = "";
        if(pathInfor.indexOf("/find-all") == 0) {
            try {
                List<Category> categoryList = categoryService.findAll();
//                rs = categoryList == null ? null :categoryList.toString();
                rs = jsonResult.jsonSuccess(categoryList);
            } catch (Exception e) {
                e.printStackTrace();
//                rs = "find all error";
                rs = jsonResult.jsonFail("find all error");
            }
            response.getWriter().write(rs);
            //gọi điện service find all
        } else if(pathInfor.indexOf("/find-by-id") == 0) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Category category = categoryService.findById(id);
                rs = jsonResult.jsonSuccess(category == null ? "null" : category);
            } catch (Exception e) {
                e.printStackTrace();
                rs = jsonResult.jsonFail("find by id error");
            }
            response.getWriter().write(rs);
            //gọi đến service find by id
        } else {
            response.sendError(404, "Url is not supported");
        }
    }

    //thực hiện với các chức năng sửa category
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Put");
    }

    //thực hiện với các chức năng xóa category
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Delete");
    }
}
