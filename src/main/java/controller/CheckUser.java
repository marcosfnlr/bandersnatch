/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.FeedbackMessage;
import model.TypeFeedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * @author raphaelcja
 */
@WebServlet("/CheckUser")
public class CheckUser extends HttpServlet {

    @Resource(name = "jdbc/Bandersnatch")
    private DataSource ds;


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher requestDispatcher;
        if (isLoginValid(request)) {
            session.setAttribute("utilisateur", request.getParameter("login"));
            requestDispatcher = getServletContext().getRequestDispatcher("/add_story.jsp");
        } else {
            request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage("Mauvaise combinaison utilisateur et mot de passe", TypeFeedback.DANGER)));
            requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");

        }
        requestDispatcher.forward(request, response);
    }

    private boolean isLoginValid(HttpServletRequest request) {

        String query = "SELECT login, password FROM Users WHERE login=? AND password=?";

        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, request.getParameter("login"));
            ps.setString(2, request.getParameter("password"));
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) return true;
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
