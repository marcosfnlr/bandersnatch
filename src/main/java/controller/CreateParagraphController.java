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
import model.FeedbackMessage;
import model.TypeFeedback;
import model.Book;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "CreateParagraphController", urlPatterns = {"/create_paragraph_controller"})
public class CreateParagraphController extends AbstractController {

    @Resource(name = "jdbc/Bandersnatch")
    private DataSource ds;
    
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        
        BookDAO bookDAO = new BookDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
              
        try {
            if(action.equals("create_book")) {
                int idBook = addBook(request, response, bookDAO);
                request.setAttribute("id_book", idBook);
                
                int idParag = addParagraph(request, response, paragraphDAO);
                request.setAttribute("id_parag_orig", idParag);
                
                String text[] = request.getParameterValues("choices_text");
                for(int i = 0; i < text.length; i++) {
                    request.setAttribute("choice_text", text[i]);
                    int idChoice = addChoice(request, response, choiceDAO);
                }
                
            } else if(action.equals("add_paragraph")) {
                int idBook = Integer.parseInt(String.valueOf(request.getParameter("id_book")));
                request.setAttribute("id_book", idBook);
                
                int idParag = addParagraph(request, response, paragraphDAO);
                request.setAttribute("id_parag_orig", idParag);
                
                String text[] = request.getParameterValues("choices_text");
                for(int i = 0; i < text.length; i++) {
                    request.setAttribute("choice_text", text[i]);
                    int idChoice = addChoice(request, response, choiceDAO);
                } 
                
                request.setAttribute("id_parag_dest", idParag);
                setParagDest(request, response, choiceDAO);
                
                
                
            } else {
                invalidParameters(request, response);
                return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        
        request.getRequestDispatcher("/account_main_page.jsp").forward(request, response);
    }
    
   
    
    
    /**
     * Adds a book and returns its generated id.
     */
    private int addBook(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        
        String title = request.getParameter("title");
        boolean openToWrite = Boolean.parseBoolean(request.getParameter("open_write"));
        boolean published = false; // TODO : make initial data base value as false
        String creator = (String)request.getSession().getAttribute("id_account"); 
        
        return bookDAO.addBook(title, openToWrite, published, creator);
    }
    
    /**
     * Adds a paragraph to a book and returns its id.
     */
    private int addParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        String text = request.getParameter("parag_text");
        boolean beginning = Boolean.parseBoolean(request.getParameter("beginning"));
        boolean conclusion = Boolean.parseBoolean(request.getParameter("conclusion"));
        int book = Integer.parseInt(String.valueOf(request.getAttribute("id_book")));
        String author = (String)request.getSession().getAttribute("id_account");
        
        return paragraphDAO.addParagraph(text, beginning, conclusion, book, author);
    }
    
    /**
     * Adds a choice to a paragraph and returns its id.
     */
    private int addChoice(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        //String text = request.getParameter("choice_text");
        String text = String.valueOf(request.getAttribute("choice_text"));
        boolean locked = false; //TODO default is false
        boolean onlyChoice = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("only_choice"));
        boolean condShouldPass = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("cond_should_pass"));
        int paragOrigin = Integer.parseInt(String.valueOf(request.getAttribute("id_parag_orig")));
        int paragDest = 1;//Integer.parseInt(request.getParameter("id_parag_dest"));
        int paragCond = 1;//Integer.parseInt(request.getParameter("id_parag_cond"));
        
        return choiceDAO.addChoice(text, locked, onlyChoice, condShouldPass, paragOrigin, paragDest, paragCond);
    }
    
    /**
     * Updates destiny paragraph of a choice.
     */
    protected void setParagDest(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        int idChoice = Integer.parseInt(request.getParameter("id_choice_orig"));
        int idParagDest = Integer.parseInt(String.valueOf(request.getAttribute("id_parag_dest")));;
        choiceDAO.setParagDest(idChoice, idParagDest);
    }

}
