/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import model.FeedbackMessage;
import model.TypeFeedback;
import model.Paragraph;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "ParagraphController", urlPatterns = {"/paragraph_controller"})
public class ParagraphController extends HttpServlet {
    
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
     * GET : controls actions of listParagraphs, getParagraph.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        
        try {
            if (action == null) {
                //TODO : where to redirect here?
                //request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("list_paragraphs")){
                actionListParagraphs(request, response, paragraphDAO);
            } else if (action.equals("get_paragraph")){
                actionGetParagraph(request, response, paragraphDAO);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        
    }
    
    /**
     * Lists all paragraphs from a book. 
     * TODO : when to use ?
     */
    private void actionListParagraphs(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        //needs to know from which book the paragraphs are from
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        List<Paragraph> paragraphs = paragraphDAO.listParagraphs(idBook);
        request.setAttribute("paragraphs", paragraphs);
    }
    
    /**
     * Gets all information of a paragraph given its identifier id_paragraph. 
     */
    private void actionGetParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));
        String view = request.getParameter("view");
        // TODO : review here ; need to show authors when show book
        if (view.equals("show_paragraph") || view.equals("delete_paragraph") || view.equals("modify_paragraph") ) {
            Paragraph paragraph = paragraphDAO.getParagraph(idParagraph);
            request.setAttribute("paragraph", paragraph);
            request.getRequestDispatcher("WEB-INF/" + view + ".jsp").forward(request, response);
        }
        else invalidParameters(request, response);
    }
    
    
    /**
     * POST : controls actions of addParagraph, deleteParagraph, modifyParagraph.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
        
        try {
            if(action.equals("add_paragraph")) {
                actionAddParagraph(request, response, paragraphDAO);
                //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
            } else if(action.equals("delete_paragraph")) {
                actionDeleteParagraph(request, response, paragraphDAO);
                //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
            } else if(action.equals("modify_paragraph")) {
                actionModifyParagraph(request, response, paragraphDAO);
                //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
            } else {
                invalidParameters(request, response);
                return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        
    }
    
    /**
     * Adds a paragraph to a book.
     */
    private void actionAddParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        String text = request.getParameter("parag_text");
        boolean beginning = Boolean.parseBoolean(request.getParameter("beginning"));
        boolean conclusion = Boolean.parseBoolean(request.getParameter("conclusion"));
        int book = Integer.parseInt(request.getParameter("book"));
        String author = request.getParameter("author");
        
        paragraphDAO.addParagraph(text, beginning, conclusion, book, author);
    }
    
    /**
     * Deletes a paragraph of a book.
     */
    private void actionDeleteParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));
        paragraphDAO.deleteParagraph(idParagraph);
    }
    
    /**
     * Modifies the text of a paragraph.
     */
    private void actionModifyParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        int idParagraph = Integer.parseInt(request.getParameter("id_paragraph"));
        String text = request.getParameter("text");
        paragraphDAO.modifyParagraph(idParagraph, text);
    }
}
