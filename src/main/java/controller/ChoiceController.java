/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
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
import model.Choice;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "ChoiceController", urlPatterns = {"/choice_controller"})
public class ChoiceController extends HttpServlet {

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
     * GET : controls actions of listParagraphChoices, getChoice.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
        
        try {
            if (action == null) {
                //TODO : where to redirect here?
                //request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("list_paragraph_choices")){
                actionListParagraphChoices(request, response, choiceDAO);
            } else if (action.equals("get_choice")){
                actionGetChoice(request, response, choiceDAO);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    /**
     * Lists all choices of a paragraph. 
     * TODO : when to use ?
     */
    private void actionListParagraphChoices(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        //needs to know from which book the paragraphs are from
        int idParagOrig = Integer.parseInt(request.getParameter("id_parag_orig"));
        List<Choice> choices = choiceDAO.listParagraphChoices(idParagOrig);
        request.setAttribute("choices", choices);
    }
}
