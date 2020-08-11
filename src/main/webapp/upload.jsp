<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Upload File - ITStudent</title>
    </head>
    <body>
        <h1>Demo Servlet upload file</h1>
        <form method="post" action="upload-file" enctype="multipart/form-data">
            <lable>Select file to upload:</lable>
            <input type="file" name="file" multiple="multiple"/><br/>
            <input type="submit" value="Upload"/>
        </form>
    </body>
</html>
