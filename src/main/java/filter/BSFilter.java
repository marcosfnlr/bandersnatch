package filter;

import model.FeedbackMessage;
import model.TypeFeedback;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/home.jsp"}, filterName = "user_filter")
public class BSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getSession().getAttribute("logged_account") == null) {
            String errorMessage = "Il faut être membre pour y accéder";
            request.setAttribute("feedbackMessages", Arrays.asList(new FeedbackMessage(errorMessage, TypeFeedback.INFO)));
            request.getRequestDispatcher("WEB-INF/" + "index.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
