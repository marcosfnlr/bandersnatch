package controller;

import dao.DAOException;

import model.FeedbackMessage;
import model.TypeFeedback;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

public abstract class AbstractController extends HttpServlet {

    @Resource(name = "jdbc/Bandersnatch")
    protected DataSource ds;

    // Error messages
    protected void invalidParameters(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorMessage = "Paramètres invalides";
        request.setAttribute("feedbackMessages", Arrays.asList(new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("WEB-INF/" + "index.jsp").forward(request, response);
    }

    protected void erreurBD(HttpServletRequest request, HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace();
        String errorMessage = "Une erreur d’accès à la base de données vient de se produire : " + e.getMessage();
        request.setAttribute("feedbackMessages", Arrays.asList(new FeedbackMessage(errorMessage, TypeFeedback.DANGER)));
        request.getRequestDispatcher("WEB-INF/" + "index.jsp").forward(request, response);
    }

    /**
     * GET : controls actions for get.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            invalidParameters(request, response); // TODO: Maybe change this behavior
            return;
        }

        processGetRequest(request, response, action);
    }

    /**
     * POST : Controls actions for post.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            invalidParameters(request, response);
            return;
        }

        processPostRequest(request, response, action);
    }


    protected abstract void processGetRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException;

    protected abstract void processPostRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws IOException, ServletException;
}