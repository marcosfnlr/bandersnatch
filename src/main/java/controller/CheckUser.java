/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "CheckUser", urlPatterns = {"/check_user"})
public class CheckUser extends HttpServlet {
    
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
        boolean badLogin = false;
        HttpSession session = request.getSession();
        if(isLoginValid(request)) session.setAttribute("utilisateur", request.getParameter("login"));
        else badLogin = true;
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User Check</title>");
            // TODO: add reference for stylesheet
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");  
            if(badLogin) {
                out.println("<p>Incorrect login or password</p>");
                out.println("<p>Retour vers <a href=\"index.jsp\">l'accueil</a></p>");
            }
            else {
                out.println("<p>You are logged in</p>");
                out.println("<p>Aller vers <a href=\"user_main_page.jsp\">la page de l'utilisateur</a></p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private boolean isLoginValid(HttpServletRequest request) {
                
        String query = "SELECT login, password FROM Users WHERE login=? AND password=?";
        
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, request.getParameter("login"));
            ps.setString(2, request.getParameter("password"));
            ResultSet rs = ps.executeQuery();
            if(rs != null && rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
