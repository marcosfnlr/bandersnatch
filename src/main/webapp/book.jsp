<%@ page import="model.Book" %>
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
    <title>Bandersnatch</title>
</head>
<body>
<%
    Book book = (Book) request.getAttribute("book");
%>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="home.jsp"><i class="fas fa-home"></i> BSnatch</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav mr-auto">
            <a class="nav-item nav-link" href="read_list.jsp"><i class="fas fa-book-reader"></i> Lire</a>
            <a class="nav-item nav-link" href="write_list.jsp"><i class="fas fa-pencil-alt"></i> Écrire</a>
        </div>
        <div class="navbar-nav" id="navbarUser">
            <a class="nav-item nav-link mr-2" href="account_controller?action=view_profile"><i class="fas fa-user"></i>
                Profil</a>
            <a class="btn btn-outline-light my-2 my-sm-0" href="account_controller?action=logout_account">Log out</a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row mt-3 justify-content-center">
        <div class="col-12 col-lg-3 text-center text-lg-right"><i class="fas fa-book fa-10x"></i></div>
        <div class="col-12 col-lg-5 align-self-center">
            <div class="row">
                <div class="col-12">
                    <h1>
                        <%=book.getTitle()%>
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <%=book.getCreator().getNameLabel()%>
                </div>
            </div>
            <div class="row mt-3">
                <%
                    if (book.isCanUserRead()) {
                %>
                <div class="col-4 text-center">
                    <a href="read_controller?action=start_reading&id_book=<%=book.getIdBook()%>"
                       class="btn btn-danger w-100"><i class="fas fa-book-reader"></i> Lire
                    </a>
                </div>
                <%
                    }
                %>
                <%
                    if (book.isCanUserWrite()) {
                %>
                <div class="col-4">
                    <a href="book_controller?action=write_book&id=<%=book.getIdBook()%>"
                       class="btn btn-danger w-100 text-center"><i class="fas fa-pencil-alt"></i> Écrire</a>
                </div>
                <%
                    }
                %>
                <%
                    if (book.isPublishable()) {
                %>
                <div class="col-4">
                    <a href="book_controller?action=publish_book&published=true&id_book=<%=book.getIdBook()%>"
                       class="btn btn-danger w-100 text-center"><i class="fas fa-globe-americas"></i> Publier</a>
                </div>
                <%
                    }
                %>
                <%
                    if (book.isUnpublishable()) {
                %>
                <div class="col-4">
                    <a href="book_controller?action=publish_book&published=false&id_book=<%=book.getIdBook()%>"
                       class="btn btn-danger w-100 text-center"><i class="fas fa-lock"></i> Depublier</a>
                </div>
                <%
                    }
                %>
                <%
                    if (book.isInvitable()) {
                %>
                <div class="col-4">
                    <a href="invitation_controller?action=invite_users&id_book=<%=book.getIdBook()%>"
                       class="btn btn-danger w-100 text-center"><i class="fas fa-users"></i> Inviter</a>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
</body>
</html>