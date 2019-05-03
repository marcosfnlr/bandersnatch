package controller;

import dao.DAOException;
import dao.HistoryDAO;

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
     * GET : controls actions History
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
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
    }

    /**
     * POST : controls actions of History.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

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
     * Adds a history from a choice made by an user.
     */
    private void saveHistory(HttpServletRequest request, HttpServletResponse response, HistoryDAO historyDAO) throws ServletException, IOException {

        List<History> newHistories = (List<History>) request.getSession().getAttribute("histories");

        String idAccount = newHistories.get(0).getAccount().getIdAccount();
        int idBook = newHistories.get(0).getBook().getIdBook();

        List<History> oldHistories = historyDAO.listUserHistoryFromBook(idAccount, idBook);

        // Delete old history
        for (History history : oldHistories) {
            historyDAO.deleteHistory(history);
        }

        // Add new history
        for (History history : newHistories) {
            historyDAO.addHistory(history);
        }
    }
}