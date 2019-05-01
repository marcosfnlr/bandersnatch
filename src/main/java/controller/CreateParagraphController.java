/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.BookDAO;
import dao.ParagraphDAO;
import dao.ChoiceDAO;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import model.*;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "CreateParagraphController", urlPatterns = {"/create_paragraph_controller"})
public class CreateParagraphController extends AbstractController {

    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        // TODO entender pq nao da certo deixar os DAO como "globais"
        BookDAO bookDAO = new BookDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        try {
            int idBook;

            switch(action) {
                case "create_book":
                    idBook = addBook(request, response, bookDAO);
                    createParagraphWithChoices(request, response, idBook, paragraphDAO, choiceDAO);
                    break;
                case "add_paragraph":
                    idBook = Integer.parseInt(String.valueOf(request.getParameter("id_book")));
                    createParagraphWithChoices(request, response, idBook, paragraphDAO, choiceDAO);
                    break;
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    /**
     * Adds a book and returns its generated id.
     */
    private int addBook(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException {
        
        String title = request.getParameter("title");
        boolean openToWrite = Boolean.parseBoolean(request.getParameter("open_write"));
        Account account = (Account)request.getSession().getAttribute("logged_account");
        String creator = account.getIdAccount(); 

        return bookDAO.addBook(title, openToWrite, creator);
    }
    
    /**
     * Adds a paragraph to a book and returns its id.
     */
    private int addParagraph(HttpServletRequest request, HttpServletResponse response, int idBook, boolean isBeginning, ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        String text = request.getParameter("parag_text");
        boolean conclusion = Boolean.parseBoolean(request.getParameter("conclusion"));
        Account account = (Account)request.getSession().getAttribute("logged_account");
        String author = account.getIdAccount();
        
        return paragraphDAO.addParagraph(text, isBeginning, conclusion, idBook, author);
    }
    
    /**
     * Adds a choice to a paragraph and returns its id.
     */
    private int addChoice(HttpServletRequest request, HttpServletResponse response, String choiceText, int idParagOrig, ChoiceDAO choiceDAO) throws ServletException, IOException {

        boolean onlyChoice = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("only_choice"));
        boolean condShouldPass = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("cond_should_pass"));
        int paragCond = 1;//Integer.parseInt(request.getParameter("id_parag_cond"));
        
        return choiceDAO.addChoice(choiceText, onlyChoice, condShouldPass, idParagOrig, paragCond);
    }

    // TODO change parameters. Will have a validation before
    private void createParagraphWithChoices(HttpServletRequest request, HttpServletResponse response, int idBook, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO) throws ServletException, IOException {

        boolean isBeginning = Boolean.parseBoolean(request.getParameter("beginning"));
        int idParag = addParagraph(request, response, idBook, isBeginning, paragraphDAO);

        String choiceText[] = request.getParameterValues("choices_text");
        for(int i = 0; i < choiceText.length; i++) {
            int idChoice = addChoice(request, response, choiceText[i], idParag, choiceDAO);
        }

        // Paragraph 'came' from a choice.
        if(!isBeginning) {
            int idChoiceOrig = Integer.parseInt(String.valueOf(request.getParameter("id_choice_orig"))); // TODO: set this on view before sending form
            choiceDAO.setParagDest(idChoiceOrig, idParag);
            choiceDAO.setLocked(idChoiceOrig, false);
        }
    }
}
