/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookDAO;
import dao.ChoiceDAO;
import dao.DAOException;
import dao.ParagraphDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import model.Account;
import model.Book;
import model.Choice;
import model.FeedbackMessage;
import model.Paragraph;
import model.TypeFeedback;

/**
 * @author raphaelcja
 */
@WebServlet(name = "ReadController", urlPatterns = {"/read_controller"})
public class ReadController extends AbstractController {

    List<Choice> finalChoices = new ArrayList<>();
    List<Paragraph> finalParagraphs = new ArrayList<>();
    Map<Paragraph, List<Choice>> dictionary = new HashMap<>();

    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException {

        try {
            if (action == null) {
                //TODO : where to redirect here?
            } else if (action.equals("read_book")) {
                postCover(request, response);
            } else if (action.equals("start_reading") || action.equals("next_paragraph")) {
                postParagraph(request, response, action);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Posts cover elements.
     */
    private void postCover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO(ds);

        int idBook = Integer.parseInt(request.getParameter("id_book"));
        Book book = bookDAO.getBook(idBook);
        List<Account> authors = bookDAO.listBookAuthors(idBook);

        request.setAttribute("title_book", book.getTitle());
        request.setAttribute("authors", authors); //TODO : get first and last name
        request.getRequestDispatcher("/read_book.jsp").forward(request, response);
    }

    /**
     * Posts paragraph(s) and its choices.
     */
    private void postParagraph(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {

        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        String finalText;
        Paragraph paragraph;
        int idBook = Integer.parseInt(request.getParameter("id_book"));

        if (action.equals("start_reading")) {
            paragraph = paragraphDAO.getBeginning(idBook);
        } else {
            Choice choice = choiceDAO.getChoice(Integer.parseInt(request.getParameter("chosen_choice")));
            paragraph = choice.getParagDest();
        }

        buildBook(idBook, paragraphDAO, choiceDAO);
        List<Choice> choices = dictionary.get(paragraph);

        finalText = paragraph.getText();

        while (choices.size() == 1) { //if more than one choice or no choice (conclusion) it leaves loop
            Paragraph paragDest = choices.get(0).getParagDest();
            choices.clear();
            choices = dictionary.get(paragDest);
            finalText = finalText + " " + paragDest.getText();
        }

        paragraph.setFinalText(finalText);
        paragraph.setFinalChoices(choices);
        request.setAttribute("paragraph", paragraph);

        request.getRequestDispatcher("/read_parag.jsp").forward(request, response);
    }

    /**
     * Builds book (arranges all elements that lead to a conclusion).
     */
    private void buildBook(int idBook, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO)
            throws ServletException, IOException {

        Paragraph beginning = paragraphDAO.getBeginning(idBook);
        List<Paragraph> conclusions = paragraphDAO.listConclusions(idBook);

        for (Paragraph conclusion : conclusions) {
            finalParagraphs.add(conclusion);
            recurtionParagChoice(beginning, conclusion, choiceDAO);
        }
        createDictionary(choiceDAO);

    }

    /**
     * Recursively selects all the choices and paragraphs of a book that will be available in reading mode.
     */
    private void recurtionParagChoice(Paragraph beginning, Paragraph paragDest, ChoiceDAO choiceDAO)
            throws ServletException, IOException {

        List<Choice> choices = choiceDAO.listParagDestChoices(paragDest.getIdParagraph());

        if (!paragDest.equals(beginning)) {
            for (Choice choice : choices) {
                if (!finalChoices.contains(choice)) {
                    finalChoices.add(choice);
                    if (!finalParagraphs.contains(choice.getParagOrigin()))
                        finalParagraphs.add(choice.getParagOrigin());
                    recurtionParagChoice(beginning, choice.getParagOrigin(), choiceDAO);
                }
            }
        }
    }

    /**
     * Creates a dictionary of paragraphs and its choices that will be available in reading mode.
     */
    private void createDictionary(ChoiceDAO choiceDAO)
            throws ServletException, IOException {

        for (Paragraph paragraph : finalParagraphs) {
            List<Choice> disclosedChoices = new ArrayList<>();
            List<Choice> paragraphChoices = choiceDAO.listParagOrigChoices(paragraph.getIdParagraph());
            for (Choice choice : paragraphChoices) {
                if (finalChoices.contains(choice))
                    disclosedChoices.add(choice);
            }
            dictionary.put(paragraph, disclosedChoices);
        }
    }


    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
