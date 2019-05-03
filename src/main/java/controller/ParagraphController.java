package controller;

import dao.ChoiceDAO;
import dao.DAOException;
import dao.ParagraphDAO;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import model.Account;
import model.FeedbackMessage;
import model.TypeFeedback;
import model.Paragraph;

@WebServlet(name = "ParagraphController", urlPatterns = {"/paragraph_controller"})
public class ParagraphController extends AbstractController {


    /**
     * GET : controls actions of Paragraph
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        try {
            switch (action) {
                case "write_paragraph":
                    writeParagraph(request, response, choiceDAO);
                    break;
                case "modify_paragraph":
                    modifyParagraph(request, response, paragraphDAO);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * POST : controls actions of Paragraph.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);

        try {
            switch (action) {
                case "delete_paragraph":
                    deleteParagraph(request, response, paragraphDAO);
                    break;
                case "edit_paragraph_text":
                    editParagraphText(request, response, paragraphDAO);
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
     * Write paragraph from a given choice.
     */
    private void writeParagraph(HttpServletRequest request, HttpServletResponse response,
                                ChoiceDAO choiceDAO) throws ServletException, IOException {

        int idChoiceOrig = Integer.parseInt(String.valueOf(request.getParameter("id_choice_orig")));
        choiceDAO.setLocked(idChoiceOrig, true);
        request.getRequestDispatcher("WEB-INF/" + "add_parag.jsp").forward(request, response);
    }

    /**
     * Goes paragraph edition page.
     */
    private void modifyParagraph(HttpServletRequest request, HttpServletResponse response,
                                 ParagraphDAO paragraphDAO) throws ServletException, IOException {

        int idParagraph = Integer.parseInt(request.getParameter("id"));

        Paragraph paragraph = paragraphDAO.getParagraphWithChoices(idParagraph);
        checkProperties(request, paragraph);

        request.setAttribute("paragraph", paragraph);

        request.getRequestDispatcher("WEB-INF/" + "modify_parag.jsp").forward(request, response);
    }

    /**
     * Modifies the text of a paragraph.
     */
    private void editParagraphText(HttpServletRequest request, HttpServletResponse response,
                                   ParagraphDAO paragraphDAO) throws ServletException, IOException {

        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));
        String text = request.getParameter("parag_text");

        paragraphDAO.modifyParagraphText(idParagraph, text);

        Paragraph paragraph = paragraphDAO.getParagraphWithChoices(idParagraph);

        checkProperties(request, paragraph);
        request.setAttribute("paragraph", paragraph);

        request.getRequestDispatcher("WEB-INF/" + "modify_parag.jsp").forward(request, response);
    }

    private void checkProperties(HttpServletRequest request, Paragraph paragraph) {
        Account account = (Account) request.getSession().getAttribute("logged_account");
        if (!paragraph.isConclusion() && !paragraph.getChoices().isEmpty() && !paragraph.getChoices().get(0).isOnlyChoice()) {
            paragraph.setChoiceAddable(true);
        }

        if (account != null && account.equals(paragraph.getAuthor())) {
            paragraph.setEditable(true);
        }
    }

    /**
     * Deletes a paragraph of a book.
     */
    private void deleteParagraph(HttpServletRequest request, HttpServletResponse response,
                                 ParagraphDAO paragraphDAO) throws ServletException, IOException {
        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));

        paragraphDAO.deleteParagraph(idParagraph);
    }
}

//            request.getRequestDispatcher("WEB-INF/" + "WEB-INF/" + view + ".jsp").forward(request, response);
