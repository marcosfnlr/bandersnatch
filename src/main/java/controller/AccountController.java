package controller;

import dao.DAOException;
import dao.AccountDAO;
import dao.BookDAO;
import dao.HistoryDAO;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.FeedbackMessage;
import model.TypeFeedback;
import model.Account;
import model.Book;

@WebServlet(name = "AccountController", urlPatterns = {"/account_controller"})
public class AccountController extends AbstractController {

    /**
     * GET : controls actions of logoutAccount.
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {

        try {
            switch (action) {
                case "logout_account":
                    logoutAccount(request, response);
                    break;
                case "view_profile":
                    viewProfile(request, response);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * POST : controls actions of addAccount, checkAccount.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        AccountDAO accountDAO = new AccountDAO(ds);

        try {
            switch (action) {
                case "add_account":
                    addAccount(request, response, accountDAO);
                    request.getRequestDispatcher("WEB-INF/" + "index.jsp").forward(request, response);
                    break;
                case "check_account":
                    if (checkAccount(request, response, accountDAO)) { // correct login
                        String idAccount = request.getParameter("id_account");
                        Account account = accountDAO.getAccount(idAccount);
                        request.getSession().setAttribute("logged_account", account);

                        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
                    } else { // incorrect login
                        String errorMessage = "Mauvaise combinaison utilisateur et mot de passe";
                        request.setAttribute("feedbackMessages", Arrays.asList(new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
                        request.getRequestDispatcher("WEB-INF/" + "index.jsp").forward(request, response);
                    }
                    break;
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Account logout.
     */
    private void logoutAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) session.invalidate();
        response.sendRedirect("index.jsp");
    }

    /**
     * Gets info about the account.
     */
    private void viewProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO(ds);
        HistoryDAO historyDAO = new HistoryDAO(ds);
        Account account = (Account) request.getSession().getAttribute("logged_account");

        List<Book> invitedBooks = bookDAO.listInvitationBooks(account.getIdAccount());
        List<Book> authorBooks = bookDAO.listAuthorBooks(account.getIdAccount());
        List<Book> startedBooks = historyDAO.listBooksUserHistory(account.getIdAccount());

        request.setAttribute("invited_books", invitedBooks);
        request.setAttribute("author_books", authorBooks);
        request.setAttribute("started_books", startedBooks);

        request.getRequestDispatcher("WEB-INF/" + "profile.jsp").forward(request, response);
    }

    /**
     * Adds a account.
     */
    private void addAccount(HttpServletRequest request, HttpServletResponse response,
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
    private boolean checkAccount(HttpServletRequest request, HttpServletResponse response,
                                 AccountDAO accountDAO) throws ServletException, IOException {
        String idAccount = request.getParameter("id_account");
        String password = request.getParameter("password");

        return accountDAO.checkAccount(idAccount, password);
    }
}
