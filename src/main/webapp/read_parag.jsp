<%@ page import="model.Paragraph" %>
<%@ page import="model.History" %>
<%@ page import="model.Choice" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/additional-methods.min.js"></script>

    <script src="js/common.js"></script>
    <script src="js/add-paragraph.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <title>Nouveau Paragraphe</title>
</head>
<body>
<%
    Paragraph paragraph = (Paragraph) request.getAttribute("paragraph");
    List<History> histories = (List<History>) session.getAttribute("histories");
%>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="home.jsp">BSnatch</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="#">Features</a>
            <a class="nav-item nav-link" href="#">Pricing</a>
            <a class="nav-item nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
        </div>
    </div>
</nav>
<div class="container">
    <h1>
        <%=paragraph.getBook().getTitle()%>
    </h1>
    <%
        if (histories != null && !histories.isEmpty()) {
    %>
    <a href="history_controller?action=save_history" class="btn btn-danger"><i class="fas fa-save"></i></a>
    <%
        int i = 0;
        for (History h : histories) {
    %>
    <a href="read_controller?action=previous_paragraph&index_current_choice=<%=i++%>">
        <%=h.getChoice().getLabelText()%>
    </a> >
    <%
        }
    %>
    <%
        }
    %>
    <hr>
    <div class="row">
        <div class="col-12">
            <%= paragraph.getFinalText() %>
        </div>
    </div>
    <%
        for (Choice c : paragraph.getFinalChoices()) {
    %>
    <div class="row mt-3">
        <div class="col-12">
            <a href="read_controller?action=next_paragraph&id_book=<%=paragraph.getBook().getIdBook()%>&chosen_choice=<%=c.getIdChoice()%>"
               class="btn btn-light w-100 text-left">
                <i class="fas fa-arrow-right"></i> <%=c.getText()%>
            </a>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
