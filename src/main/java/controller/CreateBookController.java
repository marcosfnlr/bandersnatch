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
@WebServlet(name = "CreateBookController", urlPatterns = {"/create_book_controller"})
public class CreateBookController extends HttpServlet {

    @Resource(name = "jdbc/Bandersnatch")
    private DataSource ds;
    
    // error messages
    private void invalidParameters(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String errorMessage = "Paramètres invalides";
        request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    private void erreurBD(HttpServletRequest request, HttpServletResponse response, DAOException e) 
            throws ServletException, IOException {
        e.printStackTrace();
        String errorMessage = "Une erreur d’accès à la base de données vient de se produire : " + e.getMessage();
        request.setAttribute("feedbackMessages", Arrays.asList( new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    /**
     * GET.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        
    }
    
    
    /**
     * POST.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        BookDAO bookDAO = new BookDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
                
        try {
            if(action.equals("create_book")) {
                int idBook = actionAddBook(request, response, bookDAO);
                request.setAttribute("id_book", idBook);
                
                int idParag = actionAddParagraph(request, response, paragraphDAO);
                request.setAttribute("id_parag_orig", idParag);
                
                String text[] = request.getParameterValues("choices_text");
                for(int i = 0; i < text.length; i++) {
                    request.setAttribute("choice_text", text[i]);
                    actionAddChoice(request, response, choiceDAO);
                }
                
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
    private int actionAddBook(HttpServletRequest request, HttpServletResponse response, 
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
    private int actionAddParagraph(HttpServletRequest request, HttpServletResponse response, 
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
    private int actionAddChoice(HttpServletRequest request, HttpServletResponse response, 
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
        //return choiceDAO.addChoice("text", false, false, false, 1, 1, 1);
    }
}
