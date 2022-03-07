/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tampd.utils.XMLUtils;

/**
 *
 * @author USER
 */
@WebServlet(name = "AddBookController", urlPatterns = {"/AddBookController"})
public class AddBookController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "view.jsp";
    private static final String CREATESUCCESS = "createSuccess.jsp";
    private static final String CREATE_ID_EXIST = "createIdExist.jsp";
    private static final String XML_FILE = "/WEB-INF/book.xml";
    private boolean found = false;

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
            String bookTitle = request.getParameter("txtBookTitle");
            String authorTitle = request.getParameter("txtAuthorTitle");
            String firstName = request.getParameter("txtFirstName");
            String lastName = request.getParameter("txtLastName");
            String price = request.getParameter("txtPrice");
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XML_FILE;
            Document doc = XMLUtils.parseFileToDom(filePath);
            if (doc != null) {
                found = false;
                checkID(id, doc);
                if (found) {
                    url = CREATE_ID_EXIST;
                } else {
                    Element bookE = doc.createElement("book");
                    Element idE = doc.createElement("id");
                    idE.setTextContent(id);
                    Element titleE = doc.createElement("title");
                    titleE.setTextContent(bookTitle);
                    Element authorE = doc.createElement("author");
                    authorE.setAttribute("title", authorTitle);
                    Element firstNameE = doc.createElement("firstname");
                    firstNameE.setTextContent(firstName);
                    Element lastnameE = doc.createElement("lastname");
                    lastnameE.setTextContent(lastName);
                    Element priceE = doc.createElement("price");
                    priceE.setTextContent(price);

                    authorE.appendChild(firstNameE);
                    authorE.appendChild(lastnameE);

                    bookE.appendChild(idE);
                    bookE.appendChild(titleE);
                    bookE.appendChild(authorE);
                    bookE.appendChild(priceE);

                    NodeList listCake = doc.getElementsByTagName("library");
                    if (listCake != null) {
                        if (listCake.getLength() > 0) {
                            listCake.item(0).appendChild(bookE);
                            boolean result = XMLUtils.transformDOMtoResult(doc, filePath);
                            if (result) {
                                url = CREATESUCCESS;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

  private void checkID(String id, Node node) {
        if (node == null) {
            return;
        }
        if (node.getNodeName().equals("book")) {
            NodeList childrenOfStudent = node.getChildNodes();
            for (int i = 0; i < childrenOfStudent.getLength(); i++) {
                Node tmp = childrenOfStudent.item(i);
                if (tmp.getNodeName().equals("id")) {
                    if (id.equals(tmp.getTextContent().trim())) {
                        found = true;
                        return;
                    }

                }
            }
        }
        NodeList children = node.getChildNodes();
        int count = 0;

        while (count < children.getLength()) {
            checkID(id, children.item(count++));
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
