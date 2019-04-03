/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.UserDAO;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import model.FeedbackMessage;
import model.TypeFeedback;
import model.User;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "UserController", urlPatterns = {"/user_controller"})
public class UserController extends HttpServlet {
    
    @Resource(name = "jdbc/Bandersnatch")
    private DataSource ds;
    
    // error messages
    private void invalidParameters(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String errorMessage = "Paramètres invalides";
        request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    private void erreurBD(HttpServletRequest request, HttpServletResponse response, DAOException e) 
            throws ServletException, IOException {
        e.printStackTrace();
        String errorMessage = "Une erreur d’accès à la base de données vient de se produire : " + e.getMessage();
        request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    /**
     * GET : controls actions of logoutUser.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO(ds);

        try {
            if (action == null) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("logout_user")){
                actionLogoutUser(request, response);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    
    /**
     * User logout.
     */
    private void actionLogoutUser (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect("index.jsp");
    }
    
    /**
     * POST : controls actions of addUser, checkUser.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO(ds);
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
                        
        try {
            if (action.equals("add_user")) {
                actionAddUser(request, response, userDAO);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            else if (action.equals("check_user")) {
                if(actionCheckUser(request, response, userDAO)) { // correct login
                    //String message = "Vous êtes logged-in";
                    //request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(message, TypeFeedback.SUCCESS)));
                    request.getSession().setAttribute("utilisateur", request.getParameter("id_account"));
                    request.getRequestDispatcher("/user_main_page.jsp").forward(request, response);
                } else { // incorrect login
                    String errorMessage = "Mauvaise combinaison utilisateur et mot de passe";
                    request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            }
            else {
                invalidParameters(request, response);
                return;
            }
        } catch(DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    /**
     * Adds a user.
     */
    private void actionAddUser(HttpServletRequest request, HttpServletResponse response, 
            UserDAO userDAO) throws ServletException, IOException {
        
        String idAccount = request.getParameter("id_account");
        String password = request.getParameter("password");
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        userDAO.addUser(idAccount, password, lastName, firstName);
    }
    
    /**
     * Checks if user exists with correct id_account and password.
     */
    private boolean actionCheckUser(HttpServletRequest request, HttpServletResponse response, 
            UserDAO userDAO) throws ServletException, IOException {
        
        String idAccount = request.getParameter("id_account");
        String password = request.getParameter("password");
        return userDAO.checkUser(idAccount, password);
    }
    
    
}
