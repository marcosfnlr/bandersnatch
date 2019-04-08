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

    private HistoryDAO historyDAO = new HistoryDAO(ds);

    /**
     * GET : controls actions of listUserHistory... TODO: Add more functionalities
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {
        try {
            switch(action) {
                case "list_user_history":
                    actionListUserHistory(request, response);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * POST : controls actions of addHistory, deleteHistory. TODO: Add more actions.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        try {
            switch(action) {
                case "add_history":
                    actionAddHistory(request, response);
                    break;
                case "delete_history":
                    actionDeleteHistory(request, response);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    // TODO: Add verification step before calling DAO.

    /**
     * Lists all the History of an User.
     */
    private void actionListUserHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        List<History> histories = historyDAO.listUserHistory(idAccount);
        request.setAttribute("histories", histories); // TODO: Check what is this.
    }

    /**
     * Adds a history from a choice made by an user.
     */
    private void actionAddHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        historyDAO.addHistory(idAccount, idBook, idChoice);
    }

    /**
     * Deletes a specific history entry.
     */
    private void actionDeleteHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        historyDAO.deleteHistory(idAccount, idBook, idChoice);
    }
}