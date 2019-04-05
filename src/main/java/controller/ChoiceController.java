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
            } else if (action.equals("add_choice")){ // redirected from paragraph controller
                doPost(request, response);
            } else {
                request.getRequestDispatcher("/account_main_page.jsp").forward(request, response);
                //invalidParameters(request, response);
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
    
    /**
     * Gets all information of a choice given its identifier id_choice. 
     */
    private void actionGetChoice(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        String view = request.getParameter("view");
        // TODO : review here ; need to show authors when show book
        if (view.equals("delete_choice")) {
            Choice choice = choiceDAO.getChoice(idChoice);
            request.setAttribute("choice", choice);
            request.getRequestDispatcher("WEB-INF/" + view + ".jsp").forward(request, response);
        }
        else invalidParameters(request, response);
    }
    
    /**
     * POST : controls actions of addChoice, deleteChoice.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        //String action = request.getParameter("action");
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
        String action = (String)request.getAttribute("action");
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
        
        try {
            if(action.equals("add_choice")) {
                String text[] = request.getParameterValues("choices_text");
                for(int i = 0; i < text.length; i++) {
                    request.setAttribute("choice_text", text[i]);
                    actionAddChoice(request, response, choiceDAO);
                }
                request.getRequestDispatcher("/account_main_page.jsp").forward(request, response);
            } else if(action.equals("delete_choice")) {
                actionDeleteChoice(request, response, choiceDAO);
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
     * Adds a choice to a paragraph and returns its id.
     */
    private int actionAddChoice(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        String text = request.getParameter("choice_text");
        boolean locked = false; //TODO default is false
        boolean onlyChoice = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("only_choice"));
        boolean condShouldPass = false;//TODO for now is always false Boolean.parseBoolean(request.getParameter("cond_should_pass"));
        int paragOrigin = Integer.parseInt(String.valueOf(request.getAttribute("id_parag_orig")));
        int paragDest = 1;//Integer.parseInt(request.getParameter("id_parag_dest"));
        int paragCond = 1;//Integer.parseInt(request.getParameter("id_parag_cond"));
        
        return choiceDAO.addChoice(text, locked, onlyChoice, condShouldPass, paragOrigin, paragDest, paragCond);
        //return choiceDAO.addChoice("text", false, false, false, 1, 1, 1);
    }
    
    /**
     * Deletes a choice of a paragraph.
     */
    private void actionDeleteChoice(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        choiceDAO.deleteChoice(idChoice);
    }
}
