<%-- 
    Document   : updateBook
    Created on : Mar 7, 2022, 8:46:39 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
    </head>
    <body>
        <h1>Update Book</h1>
        <form method="POST" action="UpdateBookController">
            ID: <input type="text" name="txtID" value="" /><br/>
            Title: <input type="text" name="txtTitle" value="" /><br/>
            Author: <br/>
            - Title of author: <input type="text" name="txtTitleAuthor" value="" /><br/>
            - First name:<input type="text" name="txtFirstname" value="" /><br/>
            - Last name:<input type="text" name="txtLastname" value="" /><br/>
            Price: <input type="text" name="txtPrice" value="" /><br/>
            <input type="submit" value="Update" /><br/>

        </form>
    <c:if test="${not empty requestScope.SUCCESS}">
        ${requestScope.SUCCESS}
    </c:if>
</body>
</html>
