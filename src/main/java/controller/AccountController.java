/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.AccountDAO;
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
import model.Account;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "AccountController", urlPatterns = {"/account_controller"})
public class AccountController extends HttpServlet {
    
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
     * GET : controls actions of logoutAccount.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        AccountDAO accountDAO = new AccountDAO(ds);

        try {
            if (action == null) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("logout_account")){
                actionLogoutAccount(request, response);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    
    /**
     * Account logout.
     */
    private void actionLogoutAccount (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect("index.jsp");
    }
    
    /**
     * POST : controls actions of addAccount, checkAccount.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        AccountDAO accountDAO = new AccountDAO(ds);
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
                        
        try {
            if (action.equals("add_account")) {
                actionAddAccount(request, response, accountDAO);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            else if (action.equals("check_account")) {
                if(actionCheckAccount(request, response, accountDAO)) { // correct login
                    //String message = "Vous êtes logged-in";
                    //request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(message, TypeFeedback.SUCCESS)));
                    request.getSession().setAttribute("id_account", request.getParameter("id_account"));
                    request.getRequestDispatcher("/account_main_page.jsp").forward(request, response);
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
     * Adds a account.
     */
    private void actionAddAccount(HttpServletRequest request, HttpServletResponse response, 
            AccountDAO accountDAO) throws ServletException, IOException {
        
        String idAccount = request.getParameter("id_account");
        String password = request.getParameter("password");
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        accountDAO.addAccount(idAccount, password, lastName, firstName);
    }
    
    /**
     * Checks if account exists with correct id_account and password.
     */
    private boolean actionCheckAccount(HttpServletRequest request, HttpServletResponse response, 
            AccountDAO accountDAO) throws ServletException, IOException {
        
        String idAccount = request.getParameter("id_account");
        String password = request.getParameter("password");
        return accountDAO.checkAccount(idAccount, password);
    }
    
    
}
