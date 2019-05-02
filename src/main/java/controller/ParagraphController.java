/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * @author raphaelcja
 */
@WebServlet(name = "ParagraphController", urlPatterns = {"/paragraph_controller"})
public class ParagraphController extends AbstractController {


    /**
     * GET : controls actions of listParagraphs, getParagraph.
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        try {
            switch (action) {
                case "write_paragraph":
                    writeParagraph(request, response, choiceDAO);
                case "list_paragraphs":
                    listParagraphs(request, response, paragraphDAO);
                case "get_paragraph":
                    getParagraph(request, response, paragraphDAO);
                case "modify_paragraph":
                    modifyParagraph(request, response, paragraphDAO);
                default:
                    invalidParameters(request, response);
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
        request.getRequestDispatcher("/add_parag.jsp").forward(request, response);
    }

    /**
     * Lists all paragraphs from a book.
     * TODO : when to use ?
     */
    private void listParagraphs(HttpServletRequest request, HttpServletResponse response,
                                ParagraphDAO paragraphDAO) throws ServletException, IOException {

        //needs to know from which book the paragraphs are from
        int idBook = Integer.parseInt(String.valueOf(request.getParameter("id_book")));
        List<Paragraph> paragraphs = paragraphDAO.listParagraphs(idBook);
        request.setAttribute("paragraphs", paragraphs);
    }

    /**
     * Gets all information of a paragraph given its identifier id_paragraph.
     */
    private void getParagraph(HttpServletRequest request, HttpServletResponse response,
                              ParagraphDAO paragraphDAO) throws ServletException, IOException {

        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));
        String view = request.getParameter("view");
        // TODO : review here ; need to show authors when show book
        if (view.equals("show_paragraph") || view.equals("delete_paragraph") || view.equals("modify_paragraph")) {
            Paragraph paragraph = paragraphDAO.getParagraph(idParagraph);
            request.setAttribute("paragraph", paragraph);
            request.getRequestDispatcher("WEB-INF/" + view + ".jsp").forward(request, response);
        } else invalidParameters(request, response);
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

        request.getRequestDispatcher("modify_parag.jsp").forward(request, response);
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

        request.getRequestDispatcher("modify_parag.jsp").forward(request, response);
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
     * POST : controls actions of addParagraph, deleteParagraph, modifyParagraph.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);

        try {
            switch (action) {
                case "delete_paragraph":
                    deleteParagraph(request, response, paragraphDAO);
                    //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
                case "edit_paragraph_text":
                    editParagraphText(request, response, paragraphDAO);
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
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
