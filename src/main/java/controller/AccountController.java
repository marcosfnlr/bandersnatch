/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.AccountDAO;
import dao.BookDAO;
import dao.HistoryDAO;
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
import model.Book;
import model.History;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "AccountController", urlPatterns = {"/account_controller"})
public class AccountController extends AbstractController {
    
    /**
     * GET : controls actions of logoutAccount.
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        AccountDAO accountDAO = new AccountDAO(ds);

        try {
            if (action == null) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("logout_account")){
                actionLogoutAccount(request, response);
            } else if (action.equals("view_profile")){
                actionViewProfile(request, response);
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
     * Gets info about the account.
     */
    private void actionViewProfile (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO(ds);
        HistoryDAO historyDAO = new HistoryDAO(ds);
        
        Account account = (Account) request.getSession().getAttribute("logged_account");
        
        List<Book> invitedBooks = bookDAO.listInvitationBooks(account.getIdAccount());
        List<Book> authorBooks = bookDAO.listAuthorBooks(account.getIdAccount());
        List<History> accountHistories = historyDAO.listUserHistory(account.getIdAccount());
        
        request.setAttribute("invited_books", invitedBooks);
        request.setAttribute("author_books", authorBooks);
        request.setAttribute("account_histories", accountHistories);
        
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
        
    }
    
    /**
     * POST : controls actions of addAccount, checkAccount.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        AccountDAO accountDAO = new AccountDAO(ds);
                        
        try {
            if (action.equals("add_account")) {
                actionAddAccount(request, response, accountDAO);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            else if (action.equals("check_account")) {
                if(actionCheckAccount(request, response, accountDAO)) { // correct login
                    String idAccount = request.getParameter("id_account");
                    Account account = accountDAO.getAccount(idAccount);
                    request.getSession().setAttribute("logged_account", account);
                    
                    request.getRequestDispatcher("/home.jsp").forward(request, response);
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
