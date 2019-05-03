package controller;

import dao.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet(name = "CreateParagraphController", urlPatterns = {"/create_paragraph_controller"})
public class CreateParagraphController extends AbstractController {

    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        try {
            switch (action) {
                case "cancel_creation":
                    cancelCreation(request, response, choiceDAO);
                    break;
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
    }

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        BookDAO bookDAO = new BookDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
        InvitationDAO invitationDAO = new InvitationDAO(ds);

        try {
            int idBook;

            switch (action) {
                case "create_book":
                    idBook = addBook(request, response, bookDAO, invitationDAO);
                    createParagraphWithChoices(request, response, idBook, paragraphDAO, choiceDAO);
                    break;
                case "add_paragraph":
                    idBook = Integer.parseInt(request.getParameter("id_book"));
                    createParagraphWithChoices(request, response, idBook, paragraphDAO, choiceDAO);
                    break;
                case "add_choice":
                    int idParag = Integer.parseInt(request.getParameter("id_parag"));
                    String text = request.getParameter("text");
                    addChoice(request, response, text, idParag, choiceDAO);
                    request.setAttribute("paragraph", paragraphDAO.getParagraphWithChoices(idParag));
                    request.getRequestDispatcher("WEB-INF/" + "modify_parag.jsp").forward(request, response);
                    break;
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
    }

    /**
     * Adds a book and returns its generated id.
     */
    private int addBook(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, InvitationDAO invitationDAO) throws ServletException, IOException {

        String title = request.getParameter("title");
        boolean openToWrite = Boolean.parseBoolean(request.getParameter("open_write"));
        Account account = (Account) request.getSession().getAttribute("logged_account");
        String creator = account.getIdAccount();

        int idBook = bookDAO.addBook(title, openToWrite, creator);

        if (!openToWrite) {
            invitationDAO.addInvitation(creator, idBook);
        }

        return idBook;
    }

    /**
     * Adds a paragraph to a book and returns its id.
     */
    private int addParagraph(HttpServletRequest request, HttpServletResponse response, int idBook, boolean isBeginning, boolean isConclusion, ParagraphDAO paragraphDAO) throws ServletException, IOException {

        String text = request.getParameter("parag_text");
        Account account = (Account) request.getSession().getAttribute("logged_account");
        String author = account.getIdAccount();

        return paragraphDAO.addParagraph(text, isBeginning, isConclusion, idBook, author);
    }

    /**
     * Adds a choice to a paragraph and returns its id.
     */
    private int addChoice(HttpServletRequest request, HttpServletResponse response, String choiceText, int idParagOrig, ChoiceDAO choiceDAO) throws ServletException, IOException {

        boolean onlyChoice = false;

        if (request.getParameter("unique") != null) {
            onlyChoice = true;
        }

        boolean condShouldPass = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("cond_should_pass"));
        int paragCond = 1;//Integer.parseInt(request.getParameter("id_parag_cond"));

        return choiceDAO.addChoice(choiceText, onlyChoice, condShouldPass, idParagOrig, paragCond);
    }

    private void createParagraphWithChoices(HttpServletRequest request, HttpServletResponse response, int idBook, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO) throws ServletException, IOException {

        boolean isBeginning = Boolean.parseBoolean(request.getParameter("beginning"));
        boolean isConclusion = false;

        // Checkbox return different than null if selected
        if (request.getParameter("conclusion") != null) {
            isConclusion = true;
        }

        int idParag = addParagraph(request, response, idBook, isBeginning, isConclusion, paragraphDAO);

        if (!isConclusion) {
            String choiceText[] = request.getParameterValues("choices_text");
            for (int i = 0; i < choiceText.length; i++) {
                int idChoice = addChoice(request, response, choiceText[i], idParag, choiceDAO);
            }
        }

        // Paragraph 'came' from a choice.
        if (!isBeginning) {
            int idChoiceOrig = Integer.parseInt(String.valueOf(request.getParameter("id_choice_orig")));
            choiceDAO.setParagDest(idChoiceOrig, idParag);
        }
    }

    /**
     * Adds a choice to a paragraph and returns its id.
     */
    private void cancelCreation(HttpServletRequest request, HttpServletResponse response, ChoiceDAO choiceDAO) throws ServletException, IOException {

        boolean isBeginning = Boolean.parseBoolean(request.getParameter("beginning"));

        // Paragraph 'came' from a choice.
        if (!isBeginning) {
            int idChoiceOrig = Integer.parseInt(request.getParameter("id_choice_orig"));
            choiceDAO.setLocked(idChoiceOrig, false);
        }
    }
}
