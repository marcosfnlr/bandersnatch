/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "AddChoice", urlPatterns = {"/add_choice"})
public class AddChoice extends HttpServlet {

    @Resource(name="jdbc/Bandersnatch")
    private DataSource ds;


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
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>New Choice</title>");  
            // TODO: add reference for stylesheet
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            if(!insertChoice(request)) {
                out.println("<p>Error during choice creation</p>");
                out.println("<p><a href=\"add_choice.jsp\">Essayez</a> une autre fois</p>");
                out.println("<p>Retournez vers <a href=\"user_main_page.jsp\">la page de l'utilisateur</a></p>");
            }
            else {
                out.println("<p><a href=\"add_choice.jsp\">Continuez</a> avec la création d'une autre choix</p>");
                out.println("<p>Retournez vers <a href=\"user_main_page.jsp\">la page de l'utilisateur</a></p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private boolean insertChoice(HttpServletRequest request) {
        //TODO
        return true;
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
