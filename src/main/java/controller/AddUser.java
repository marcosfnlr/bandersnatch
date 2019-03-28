/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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
@WebServlet(name = "AddUser", urlPatterns = {"/add_user"})
public class AddUser extends HttpServlet {
    
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
            out.println("<title>Registration</title>");  
            // TODO: add reference for stylesheet
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            if(!insertUser(request)) out.println("<p>Error during registration</p>");
            out.println(traiteDonnees(request));
            out.println("<p>Retour vers <a href=\"index.jsp\">l'accueil</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private boolean insertUser(HttpServletRequest request) {
        String query = "INSERT INTO Users VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, request.getParameter("login"));
            ps.setString(2, request.getParameter("password"));
            ps.setString(3, request.getParameter("nom"));
            ps.setString(4, request.getParameter("prenom"));
            int i = ps.executeUpdate();
            if(i == 1) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private String traiteDonnees(HttpServletRequest request) {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        
        if (nom.equals("") || prenom.equals("")) {
            return "<p>Erreur : tous les champs doivent être remplis.</p>";
        }
        else return "<p>Bienvenue " + prenom + " " + nom + ".</p>";        
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