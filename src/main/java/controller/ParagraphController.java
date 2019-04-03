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
     * GET : controls actions of getListParagraphs, getParagraph.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
    }
    
    /**
     * Lists all paragraphs from a book. 
     * TODO : when to use ?
     */
    private void actionGetListParagraphs(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        //needs to know from which book the paragraphs are from
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        List<Paragraph> paragraphs = paragraphDAO.getListParagraphs(idBook);
        request.setAttribute("paragraphs", paragraphs);
    }
    
    /**
     * Gets all information of a paragraph given its identifier idParagraph. 
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
        
    }
    
    /**
     * Adds a paragraph to a book.
     */
    private void actionAddParagraph(HttpServletRequest request, HttpServletResponse response, 
            ParagraphDAO paragraphDAO) throws ServletException, IOException {
        
        String text = request.getParameter("text");
        boolean conclusion = Boolean.parseBoolean(request.getParameter("conclusion"));
        int fk_book = Integer.parseInt(request.getParameter("fk_book"));
        String fk_account = request.getParameter("fk_account");
        
        paragraphDAO.addParagraph(text, conclusion, fk_book, fk_account);
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
