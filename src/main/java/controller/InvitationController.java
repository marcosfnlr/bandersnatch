package controller;

import dao.DAOException;
import dao.InvitationDAO;

import model.Invitation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "InvitationController", urlPatterns = {"/invitation_controller"})
public class InvitationController extends AbstractController {
    private InvitationDAO invitationDAO = new InvitationDAO(ds);

    /**
     * GET : controls actions of listUserInvitations, listBookInvitations. TODO: Add more functionalities
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        try {
            switch(action) {
                case "list_user_invitations":
                    actionListUserInvitations(request, response);
                    break;
                case "list_book_invitations":
                    actionListBookInvitations(request, response);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * POST : controls actions of addInvitation, deleteInvitation. TODO: Add more actions.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        try {
            switch(action) {
                case "add_invitation":
                    actionAddInvitation(request, response);
                    break;
                case "delete_invitation":
                    actionDeleteInvitation(request, response);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Lists all the Invitations made to an User.
     */
    private void actionListUserInvitations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        List<Invitation> invitations = invitationDAO.listUserInvitations(idAccount);
        request.setAttribute("invitations", invitations); // TODO: Check what is this.
    }

    /**
     * Lists all the Invitations of a Book.
     */
    private void actionListBookInvitations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idBook = Integer.parseInt(request.getParameter("id_book"));
        List<Invitation> invitations = invitationDAO.listBookInvitations(idBook);
        request.setAttribute("invitations", invitations); // TODO: Check what is this.
    }

    /**
     * Adds an invitation made to an user.
     */
    private void actionAddInvitation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        invitationDAO.addInvitation(idAccount, idBook);
    }

    /**
     * Deletes a specific invitation entry.
     */
    private void actionDeleteInvitation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idAccount = request.getParameter("id_account");
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        invitationDAO.deleteInvitation(idAccount, idBook);
    }
}
