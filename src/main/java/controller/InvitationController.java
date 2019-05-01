package controller;

import dao.DAOException;
import dao.BookDAO;
import dao.InvitationDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "InvitationController", urlPatterns = {"/invitation_controller"})
public class InvitationController extends AbstractController {

    /**
     * GET : controls actions of inviteUsers, editUsersInvited. TODO: Add more functionalities
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        BookDAO bookDAO = new BookDAO(ds);

        try {
            switch (action) {
                case "invite_users":
                    inviteUsers(request, response, bookDAO);
                    break;
                case "edit_invited_users":
                    editUsersInvited(request, response, bookDAO);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Gets list of users not yet invited to a book and redirects to invitation's list addition page.
     */
    private void inviteUsers(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException {

        // TODO: Idealmente teriamos que fazer essa vlidacao sempre antes de fazer as coisas... Sugestoes?
        if (request.getParameter("id_book") == null) {
            invalidParameters(request, response);
            return;
        }

        int idBook = Integer.parseInt(request.getParameter("id_book"));

        request.setAttribute("users", bookDAO.listUsersNotInvited(idBook));
        request.getRequestDispatcher("/invite_users.jsp").forward(request, response);
    }

    /**
     * Gets list of users invited to a book and redirects to invitation's list edition page.
     */
    private void editUsersInvited(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException {

        // TODO: Idealmente teriamos que fazer essa vlidacao sempre antes de fazer as coisas... Sugestoes?
        if (request.getParameter("id_book") == null) {
            invalidParameters(request, response);
            return;
        }

        int idBook = Integer.parseInt(request.getParameter("id_book"));

        request.setAttribute("users", bookDAO.listUsersInvited(idBook));
        request.getRequestDispatcher("/edit_invited_users.jsp").forward(request, response);
    }

    /**
     * POST : controls actions of inviteUsers, addInvitationList, editInvitationList. TODO: Add more actions.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        InvitationDAO invitationDAO = new InvitationDAO(ds);

        try {
            switch (action) {
                case "add_invitation_list":
                    addInvitationList(request, response, invitationDAO);
                    break;
                case "edit_invitation_list":
                    editInvitationList(request, response, invitationDAO);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Adds a list of users to a book's invitation list.
     */
    private void addInvitationList(HttpServletRequest request, HttpServletResponse response, InvitationDAO invitationDAO) throws ServletException, IOException {

        int idBook = Integer.parseInt(request.getParameter("id_book"));
        String idAccounts[] = request.getParameterValues("id_accounts");

        for (int i = 0; i < idAccounts.length; i++) {
            invitationDAO.addInvitation(idAccounts[i], idBook);
        }
    }

    /**
     * Deletes a list of users from a book's invitation list.
     */
    private void editInvitationList(HttpServletRequest request, HttpServletResponse response, InvitationDAO invitationDAO) throws ServletException, IOException {

        int idBook = Integer.parseInt(request.getParameter("id_book"));
        String idAccounts[] = request.getParameterValues("id_account");

        for (int i = 0; i < idAccounts.length; i++) {
            invitationDAO.deleteInvitation(idAccounts[i], idBook);
        }
    }
}
