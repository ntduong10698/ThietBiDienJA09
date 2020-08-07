package com.bksoftwarevn.itstudent.controller;

import com.bksoftwarevn.itstudent.model.Category;
import com.bksoftwarevn.itstudent.model.JsonResult;
import com.bksoftwarevn.itstudent.service.CategoryService;
import com.bksoftwarevn.itstudent.service_impl.CategoryServiceImpl;
import com.google.gson.Gson;

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
        String rs = "";
        try {
            //Client sẽ gửi một chuỗi có định dạng json lên cho server
            //chuỗi json phải có định dạng tương ứng với một đối tượng category
            //sử dụng gson để chuyển chuỗi json sang đối tượng category
            //thực hiện lấy trường name bằng getName truyền vào cho hàm insert

            //sử dụng fromJson để chuyển dữ liệu client truyền lên cho server thành
            //một đối tượng tượng tham sô của hàm nãy tương ứng là:
            //tham số đầu thì là một bộ đêm để đọc chuỗi hoặc là một chuỗi
            //Tham số thứ hai là class muốn chuyển từ json về
            Category category = new Gson().fromJson(request.getReader(),Category.class);
            Category newCategory = categoryService.insert(category.getName());
            rs = newCategory != null ? jsonResult.jsonSuccess(newCategory) :
                    jsonResult.jsonSuccess("Không thể thêm danh mục!");
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload category fail!");
        }
        response.getWriter().write(rs);
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
                rs = jsonResult.jsonSuccess(category == null ? "Không tìm thấy danh mục tương ứng" : category);
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
        String rs = "";
        try {
            Category category = new Gson().fromJson(req.getReader(), Category.class);
            rs = jsonResult.jsonSuccess(categoryService.update(category));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("update category fail!");
        }
        resp.getWriter().write(rs);
    }

    //thực hiện với các chức năng xóa category
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(categoryService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("delete category fail");
        }
        resp.getWriter().write(rs);
    }
}
