<%-- 
    Document   : create_book
    Created on : 28-Mar-2019, 05:52:45
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/additional-methods.min.js"></script>

    <script src="js/common.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <title>Bandersnatch</title>
</head>
<body>
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
    <h1>Nouvelle Histoire</h1>
    <form action="add_parag.jsp" class="custom-validation" novalidate>
        <input type="hidden" name="is_new_book" value="true">
        <div class="form-row">
            <div class="col-12 col-lg-8 mb-3">
                <label for="title">Nom de l'histoire:</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Entrez nom" required>
                <div class="invalid-tooltip">
                    Dis-moi un nom cool.
                </div>
            </div>
            <div class="col-12 col-lg-4 mb-3">
                <label for="open_write">Mode écriture:</label>
                <div id="open_write">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="open_write" id="modeOpen" value="true"
                               checked="checked">
                        <label class="form-check-label" for="modeOpen">Libre</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="open_write" id="modeInvitation"
                               value="false">
                        <label class="form-check-label" for="modeInvitation">Sur invitation</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <button id="create" type="submit" class="btn btn-danger">Créer</button>
        </div>
    </form>
</div>
</body>
</html>
