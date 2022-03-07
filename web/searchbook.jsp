<%-- 
    Document   : searchbook
    Created on : Mar 7, 2022, 2:50:15 PM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Search Cake</h1>
        <form action="SearchBookController" method="POST">
            From price <input type="text" name="txtPriceFrom" value="" />
            To Price <input type="text" name="txtpriceTo" value="" />
            <input type="submit" value="Submit" />
        </form>
    <c:if test="${requestScope.LIST_BOOK != null}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Title</th>
                        <th>Author Title</th>
                        <th>Firstname</th>
                        <th>LastName</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.LIST_BOOK}" var="dto" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.bookTitle}</td>
                            <td>${dto.authorTitle}</td>
                            <td>${dto.firstName}</td>
                            <td>${dto.lastName}</td>
                            <td>${dto.price}</td>
                            <td>
                                <c:url var ="delink" value="DeleteController">
                                    <c:param name="txtId" value="${dto.id}"/>
                                </c:url>
                                <a href="${delink}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>
    </body>
</html>
