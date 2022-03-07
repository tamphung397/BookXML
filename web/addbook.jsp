<%-- 
    Document   : addbook
    Created on : Mar 7, 2022, 2:26:32 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Add book</h1>
        <form action="AddBookController" method="POST">
            id <input type="text" name="txtID" value="" /><br>
            BookTitle <input type="text" name="txtBookTitle" value="" /><br>
            Author title <input type="text" name="txtAuthorTitle" value="" /><br>
            FirstName <input type="text" name="txtFirstName" value="" /><br>
            LastName <input type="text" name="txtLastName" value="" /><br>
            Price <input type="text" name="txtPrice" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
