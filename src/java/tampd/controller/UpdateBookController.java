/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import tampd.dto.BookDTO;
import tampd.utils.XMLUtils;

/**
 *
 * @author USER
 */
@WebServlet(name = "UpdateBookController", urlPatterns = {"/UpdateBookController"})
public class UpdateBookController extends HttpServlet {

    private static final String XMLFILE = "/WEB-INF/book.xml";
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "updateBook.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String id = request.getParameter("txtID");
            String title = request.getParameter("txtTitle");
            String firstName = request.getParameter("txtFirstname");
            String lastname = request.getParameter("txtLastname");
            String price = request.getParameter("txtPrice");
            String titleAuthor = request.getParameter("txtTitleAuthor");
            double priceNum = Double.parseDouble(price);

            String realPath = request.getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;

            XMLStreamReader reader = XMLUtils.createSTAXReader(filePath);
            List<BookDTO> dtos = new ArrayList<>();
            int found = -1;
            while (reader.hasNext()) {
                int cusor = reader.next();
                if (cusor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("book")) {
                        String idTmp = XMLUtils.getTextContent(reader, "id");
                        String titleTmp = XMLUtils.getTextContent(reader, "title");
                        String authorTitlTmp = XMLUtils.getTextAttribute(reader, "author", "title");
                        String firstnameTmp = XMLUtils.getTextContent(reader, "firstname");
                        String lastnametmp = XMLUtils.getTextContent(reader, "lastname");
                        reader.next();
                        String priceTmp = XMLUtils.getTextContent(reader, "price");
                        BookDTO tmpDTO = new BookDTO(idTmp, titleTmp, Double.parseDouble(priceTmp), authorTitlTmp, firstnameTmp,
                                lastnametmp);
                        dtos.add(tmpDTO);
                        if (idTmp.equals(id)) {

                            found = dtos.indexOf(tmpDTO);
                        }

                    }
                }
            }
            if (found >= 0) {
                XMLStreamWriter writer = XMLUtils.createSTAXWrite(filePath);
                if (writer != null) {
                    BookDTO updatedBook = new BookDTO(id, title, priceNum, titleAuthor, firstName, lastname);
                    dtos.set(found, updatedBook);

                    writer.writeStartDocument("UTF-8", "1.0");

                    writer.writeStartElement("library");

                    for (int count = 0; count < dtos.size(); count++) {
                        writer.writeStartElement("book");

                        writer.writeStartElement("id");
                        writer.writeCharacters(dtos.get(count).getId());
                        writer.writeEndElement();

                        writer.writeStartElement("title");
                        writer.writeCharacters(dtos.get(count).getBookTitle());
                        writer.writeEndElement();

                        writer.writeStartElement("author");
                        writer.writeAttribute("title", dtos.get(count).getAuthorTitle());

                        writer.writeStartElement("firstname");
                        writer.writeCharacters(dtos.get(count).getFirstName());
                        writer.writeEndElement();

                        writer.writeStartElement("lastname");
                        writer.writeCharacters(dtos.get(count).getLastName());
                        writer.writeEndElement();

                        writer.writeEndElement();

                        writer.writeStartElement("price");
                        writer.writeCharacters(dtos.get(count).getPrice() + "");
                        writer.writeEndElement();

                        writer.writeEndElement();
                    }

                    writer.writeEndDocument();

                    request.setAttribute("SUCCESS", "Update success");
                    url = SUCCESS;
                }
            } else {
                url = SUCCESS;
                request.setAttribute("SUCCESS", "Id does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
