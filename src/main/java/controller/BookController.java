package controller;

import dao.DAOException;
import dao.BookDAO;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ParagraphDAO;
import model.*;

@WebServlet(name = "BookController", urlPatterns = {"/book_controller"})
public class BookController extends AbstractController {

    /**
     * GET : controls actions of Book.
     */
    @Override
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        BookDAO bookDAO = new BookDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);

        try {
            switch (action) {
                case "write_book":
                    writeBook(request, response, paragraphDAO);
                    break;
                case "list_published_books":
                    listPublishedBooks(request, response, bookDAO);
                    break;
                case "list_open_books":
                    listOpenBooks(request, response, bookDAO);
                    break;
                case "get_book":
                    getBook(request, response, bookDAO);
                    break;
                case "publish_book":
                    publishBook(request, response, bookDAO);
                    break;
                default:
                    invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * POST : controls actions of Book.
     */
    @Override
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        BookDAO bookDAO = new BookDAO(ds);

        try {
            switch (action) {
                case "delete_book":
                    deleteBook(request, response, bookDAO);
                    break;
                default:
                    invalidParameters(request, response);
                    return;
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * Go to page of writing/editing book.
     */
    private void writeBook(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO) throws ServletException, IOException {

        int idBook = Integer.parseInt(request.getParameter("id"));
        List<Paragraph> paragraphs = paragraphDAO.listParagraphs(idBook);

        request.getSession().setAttribute("paragraphs", paragraphs);

        Paragraph paragraph = paragraphDAO.getBeginningWithChoices(idBook);
        Account account = (Account) request.getSession().getAttribute("logged_account");

        if (account != null && account.equals(paragraph.getAuthor())) {
            paragraph.setEditable(true);
        }
        if (!paragraph.isConclusion() && !paragraph.getChoices().isEmpty() && !paragraph.getChoices().get(0).isOnlyChoice()) {
            paragraph.setChoiceAddable(true);
        }

        request.setAttribute("paragraph", paragraph);

        request.getRequestDispatcher("WEB-INF/" + "modify_parag.jsp").forward(request, response);
    }

    /**
     * Lists all published books.
     */
    private void listPublishedBooks(HttpServletRequest request, HttpServletResponse response,
                                    BookDAO bookDAO) throws ServletException, IOException {
        List<Book> publishedBooks = bookDAO.listPublishedBooks();
        request.setAttribute("books", publishedBooks);
        request.getRequestDispatcher("WEB-INF/" + "read_list.jsp").forward(request, response);
    }

    /**
     * Lists all open to write books.
     */
    private void listOpenBooks(HttpServletRequest request, HttpServletResponse response,
                               BookDAO bookDAO) throws ServletException, IOException {
        List<Book> openBooks = bookDAO.listOpenBooks();
        request.setAttribute("books", openBooks);
        request.getRequestDispatcher("WEB-INF/" + "write_list.jsp").forward(request, response);
    }

    /**
     * Gets all information of a book given its identifier idBook.
     */
    private void getBook(HttpServletRequest request, HttpServletResponse response,
                         BookDAO bookDAO) throws ServletException, IOException {

        int idBook = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.getBook(idBook);
        Account account = (Account) request.getSession().getAttribute("logged_account");

        book.setFinished(bookDAO.checkConclusion(idBook));
        book.setCanUserRead(checkReadAccess(book, account, bookDAO));
        book.setCanUserWrite(checkWriteAccess(book, account, bookDAO));

        if (!book.isPublished() && book.isFinished() && account != null && account.equals(book.getCreator())) {
            book.setPublishable(true);
        }

        if (!book.isOpenToWrite() && account != null && account.equals(book.getCreator())) {
            book.setInvitable(true);
        }

        if (book.isPublished() && account != null && account.equals(book.getCreator())) {
            book.setUnpublishable(true);
        }

        request.setAttribute("book", book);

        request.getRequestDispatcher("WEB-INF/" + "book.jsp").forward(request, response);

    }

    /**
     * Checks if account has access to write book.
     */
    public boolean checkWriteAccess(Book book, Account account, BookDAO bookDAO) {
        // if in invitation mode, only invited accounts have access
        if (!book.isOpenToWrite()) {
            List<Account> invitations = bookDAO.listUsersInvited(book.getIdBook());
            return invitations.contains(account);
        }
        // if in open mode, every user have access
        return account != null;
    }

    /**
     * Checks if account has access to read book.
     */
    public boolean checkReadAccess(Book book, Account account, BookDAO bookDAO) {
        // if published everyone (user and non-user) has access
        if (book.isPublished()) {
            return true;
        }
        //if not published and in invitation mode, only invited accounts have access
        if (!book.isOpenToWrite()) {
            List<Account> invitations = bookDAO.listUsersInvited(book.getIdBook());
            return invitations.contains(account);
        }
        // if not published and in open mode, every user have access
        return account != null;
    }

    /**
     * Modifies a book's publication status to published or unpublished.
     */
    private void publishBook(HttpServletRequest request, HttpServletResponse response,
                             BookDAO bookDAO) throws ServletException, IOException {
        int idBook = Integer.parseInt(request.getParameter("id_book"));
        boolean published = Boolean.parseBoolean(request.getParameter("published"));

        bookDAO.publishBook(idBook, published);
        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
    }

    /**
     * Deletes a book.
     * It's called when first paragraph of book is deleted.
     * Constraints of first paragraph deletion are in paragraph controller
     */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response,
                            BookDAO bookDAO) throws ServletException, IOException {
        int idBook = Integer.parseInt(request.getParameter("id_book"));

        bookDAO.deleteBook(idBook);
        request.getRequestDispatcher("WEB-INF/" + "home.jsp").forward(request, response);
    }
}