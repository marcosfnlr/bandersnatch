/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOException;
import dao.BookDAO;
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
@WebServlet(name = "BookController", urlPatterns = {"/book_controller"})
public class BookController extends HttpServlet{
    
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
     * GET : controls actions of listPublishedBooks, getBook.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        BookDAO bookDAO = new BookDAO(ds);

        try {
            if (action == null) {
                //TODO : where to redirect here?
                //request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if (action.equals("list_published_books")){
                actionListPublishedBooks(request, response, bookDAO);
            } else if (action.equals("get_book")){
                actionGetBook(request, response, bookDAO);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    /**
     * Lists all published books. 
     */
    private void actionListPublishedBooks(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        
        List<Book> publishedBooks = bookDAO.listPublishedBooks();
        request.setAttribute("published_books", publishedBooks);
        //request.getRequestDispatcher("TODO INSERT PAGE").forward(request, response);
    }
    
    /**
     * Gets all information of a book given its identifier idBook. 
     */
    private void actionGetBook(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        String view = request.getParameter("view");
        // TODO : review here ; need to show authors when show book
        if (view.equals("show_book") || view.equals("delete_book")) {
            Book book = bookDAO.getBook(idBook);
            request.setAttribute("book", book);
            request.getRequestDispatcher("WEB-INF/" + view + ".jsp").forward(request, response);
        }
        else invalidParameters(request, response);
    }
    
    /**
     * POST : controls actions of addBook, deleteBook, publishBook.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        BookDAO bookDAO = new BookDAO(ds);
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
        
        try {
            if(action.equals("add_book")) {
                actionAddBook(request, response, bookDAO);
                //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
            } else if(action.equals("delete_book")) {
                actionDeleteBook(request, response, bookDAO);
                //request.getRequestDispatcher("TODO goes to which page").forward(request, response);
            } else if(action.equals("publish_book")) {
                actionPublishBook(request, response, bookDAO);
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
     * Adds a book.
     */
    private void actionAddBook(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        
        String title = request.getParameter("title");
        boolean openToWrite = Boolean.parseBoolean(request.getParameter("open"));
        boolean published = Boolean.parseBoolean(request.getParameter("published"));
        String creator = request.getParameter("creator"); 
        int firstParagraph = Integer.parseInt(request.getParameter("first_paragraph"));
        
        bookDAO.addBook(title, openToWrite, published, creator, firstParagraph);
    }
    
    
    /**
     * Deletes a book.
     * It's called when first paragraph of book is deleted. How to do it best ?
     * Constraints of first paragraph deletion are in paragraph controller
     */
    private void actionDeleteBook(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        bookDAO.deleteBook(idBook);
    }
    
    
    /**
     * Modifies a book's publication status to published or unpublished.
     */
    private void actionPublishBook(HttpServletRequest request, HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
        
        //TODO : check if account is creator
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        boolean published = Boolean.parseBoolean(request.getParameter("published"));
        bookDAO.publishBook(idBook, published);
    }
}
