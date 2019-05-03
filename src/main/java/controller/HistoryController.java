package controller;

import dao.DAOException;
import dao.HistoryDAO;

import model.Account;
import model.Book;
import model.History;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HistoryController", urlPatterns = {"/history_controller"})
public class HistoryController extends AbstractController {


    /**
     * GET : controls actions of listUserHistory... TODO: Add more functionalities
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        HistoryDAO historyDAO = new HistoryDAO(ds);

        try {
            switch (action) {
                case "save_history":
                    saveHistory(request, response, historyDAO);
                    break;
                case "list_user_history":
                    listUserHistory(request, response, historyDAO);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    /**
     * POST : controls actions.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        HistoryDAO historyDAO = new HistoryDAO(ds);

        try {
            switch (action) {
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }


    /**
     * Lists all the History of an User.
     */
    private void listUserHistory(HttpServletRequest request, HttpServletResponse response, HistoryDAO historyDAO) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        List<History> histories = historyDAO.listUserHistoryFromBook(idAccount, idBook);
        request.setAttribute("histories", histories);
    }

    /**
     * Adds a history from a choice made by an user.
     */
    private void saveHistory(HttpServletRequest request, HttpServletResponse response, HistoryDAO historyDAO) throws ServletException, IOException {

        List<History> newHistories = (List<History>) request.getSession().getAttribute("histories");

        String idAccount = newHistories.get(0).getAccount().getIdAccount();
        int idBook = newHistories.get(0).getBook().getIdBook();

        List<History> oldHistories = historyDAO.listUserHistoryFromBook(idAccount, idBook);

        // Delete old history
        for(History history: oldHistories) {
            historyDAO.deleteHistory(history);
        }

        // Add new history
        for (History history : newHistories) {
            historyDAO.addHistory(history);
        }
    }
}