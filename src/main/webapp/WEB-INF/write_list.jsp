<%@ page import="model.Account" %>
<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="model.History" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script src="js/common.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/profile.css">
    <title>Bandersnatch</title>
</head>
<body>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
%>
<%
    boolean isLogged = request.getSession().getAttribute("logged_account") != null;
%>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="<%=isLogged ? "home.jsp": "index.jsp"%>"><i class="fas fa-home"></i> BSnatch</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav mr-auto">
            <a class="nav-item nav-link" href="book_controller?action=list_published_books"><i
                    class="fas fa-book-reader"></i> Lire</a>
            <%
                if (isLogged) {
            %>
            <a class="nav-item nav-link" href="book_controller?action=list_open_books">
                <i class="fas fa-pencil-alt"></i> Écrire
            </a>
            <%
                }
            %>
        </div>
        <%
            if (isLogged) {
        %>
        <div class="navbar-nav" id="navbarUser">
            <a class="nav-item nav-link mr-2" href="account_controller?action=view_profile"><i class="fas fa-user"></i>
                Profil</a>
            <a class="btn btn-outline-light my-2 my-sm-0" href="account_controller?action=logout_account">Log out</a>
        </div>
        <%
            }
        %>
    </div>
</nav>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-12 text-center">
            <h1>
                Aide à créer des nouvelles aventures!
            </h1>
        </div>
    </div>
    <div class="col-12">
        <a href="add_book.jsp" class="btn btn-danger float-right"><i class="fas fa-plus"></i> Nouvelle histoire</a>
    </div>
    <hr class="mt-1">
    <div class="row">
        <%
            for (Book b : books) {
        %>
        <div class="col-12 col-lg-3 mt-3">
            <a href="book_controller?action=get_book&id=<%=b.getIdBook()%>" class="btn w-100 text-left"><i
                    class="fas fa-book"></i> <%=b.getLabelTitle()%>
            </a>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>