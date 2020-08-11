package com.bksoftwarevn.itstudent.controller;

import com.bksoftwarevn.itstudent.model.JsonResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UploadFileController", value="/upload-file/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 2014 * 50,
        maxRequestSize = 1024 * 1024 * 50)
//fileSizeThreshold: Nếu kích thước một file upload nó quá mức đặt thì sẽ ghi trực tiếp
//file này vào ổ cứng chứ không thông qua bộ đêm.
//maxFileSize: tức là kích thước tối data 1 file được upload
//maxRequestSize: là kích thước tối đa của 1 request
public class UploadFileController extends HttpServlet {

    private JsonResult jsonResult = new JsonResult();

    private static final String SAVE_DIRECTORY = "file_upload";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = "";
        List<String> listRs = new ArrayList<>();
        try {
            Collection<Part> partCollection = request.getParts();
            long time = new Date().getTime();
            for (Part part: partCollection) {
                String fileName = getFileName(part);
                if(fileName != null) {
                    String filePath = getFolderUpload(time).getAbsolutePath() + File.separator + fileName;
                    System.out.println("Write file: " + filePath);
                    //thực hiện ghi file
                    part.write(filePath);
                    listRs.add(SAVE_DIRECTORY+"/"+time+"/"+fileName);
                }
            }
            rs = jsonResult.jsonSuccess(listRs);
        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFail("upload fail");
        }
        response.getWriter().write(rs);
    }

    private File getFolderUpload(long time) {
        String appPath = "C:\\Sever\\apache-tomee-8.0.3-plume\\apache-tomee-plume-8.0.3\\webapps\\";
        appPath += SAVE_DIRECTORY+"\\" + time;
        File foldeUpload = new File(appPath);
        //kiểm tra xem thư mục đã được tạo hay chưa để tiến hành tọa
        if(!foldeUpload.exists()) {
            foldeUpload.mkdirs();
        }
        return foldeUpload;
    }

    private String getFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for(String s: items) {
            if(s.trim().startsWith("filename")) {
                String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                fileName = fileName.replace("\\", "/");
                int i = fileName.lastIndexOf("/");
                return fileName.substring(i + 1);
            }
        }
        return null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
