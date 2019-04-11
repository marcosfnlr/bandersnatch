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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import model.Choice;
import model.FeedbackMessage;
import model.Paragraph;
import model.TypeFeedback;

/**
 *
 * @author raphaelcja
 */
@WebServlet(name = "ReadController", urlPatterns = {"/read_controller"})
public class ReadController extends AbstractController{
    
    List<Choice> finalChoices;

    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Gets all choices from a book that take to a conclusion. 
     */
    private void buildBook(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        ChoiceDAO choiceDAO = new ChoiceDAO(ds);
        
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        Paragraph beginning = paragraphDAO.getBeginning(idBook); //TODO : add to request
        List<Paragraph> conclusions = paragraphDAO.listConclusions(idBook);
        
        finalChoices = new ArrayList<>();
        
        for (Paragraph conclusion : conclusions) {
            recurtionParagChoice(beginning, conclusion, choiceDAO);
        }
        
        //TODO : add finalChoices to request
    }
    
    /**
     * Recursively adds to finalChoice all the choices that will be available in reading mode. 
     */
    private void recurtionParagChoice(Paragraph beginning, Paragraph paragDest, ChoiceDAO choiceDAO) {
        
        List<Choice> choices = choiceDAO.listParagDestChoices(paragDest.getIdParagraph());
        
        if (paragDest.equals(beginning)) {
            return;
        }
        else {
            for (Choice choice : choices) {
                if(!finalChoices.contains(choice)) { 
                    finalChoices.add(choice);
                    recurtionParagChoice(beginning, choice.getParagOrigin(), choiceDAO);
                }
            }
        }
    }
    

    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
