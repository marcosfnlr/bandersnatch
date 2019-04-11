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
public class ChoiceController extends AbstractController {

    
    
    /**
     * GET : controls actions of listParagraphChoices, getChoice.
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
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
        List<Choice> choices = choiceDAO.listParagOrigChoices(idParagOrig);
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
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);

        try {
            if(action.equals("delete_choice")) {
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
     * Deletes a choice of a paragraph.
     */
    private void actionDeleteChoice(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        choiceDAO.deleteChoice(idChoice);
    }
    
    
    /**
     * Updates destiny paragraph of a choice.
     */
    protected void actionSetParagDest(HttpServletRequest request, HttpServletResponse response, 
            ChoiceDAO choiceDAO) throws ServletException, IOException {
        
        int idChoice = Integer.parseInt(request.getParameter("id_choice"));
        choiceDAO.deleteChoice(idChoice);
    }
}
