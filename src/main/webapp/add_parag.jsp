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
    <h1><%=request.getParameter("title")%>
    </h1>
    <form action="create_paragraph_controller" method="post" class="custom-validation" novalidate>
        <%
            String isNewBook = request.getParameter("is_new_book");
            if ("true".equals(isNewBook)) {
        %>
        <input type="hidden" name="title" value="<%=request.getParameter("title")%>">
        <input type="hidden" name="open_write" value="<%=request.getParameter("open_write")%>">
        <input type="hidden" name="beginning" value="true">
        <input type="hidden" name="action" value="create_book" />
        <%
        } else {
        %>
        <input type="hidden" name="id_choice_orig" value="<%=request.getParameter("id_choice_orig")%>">
        <input type="hidden" name="action" value="add_paragraph" />
        <%
            }
        %>
        <input type="hidden" name="id_book" value="${id_book}">
        <div class="form-row">
            <div class="col-12 mb-2">
                <label for="text">Texte du paragraphe:</label>
                <textarea class="form-control" id="text" name="parag_text" rows="3" required></textarea>
                <div class="invalid-tooltip">
                    Donne-moi une vie.
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="col-12 mb-3">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" name="conclusion" id="isConclusion">
                    <label class="form-check-label" for="isConclusion">J'suis la conclusion!</label>
                </div>
            </div>
        </div>
        <div class="row align-items-center">
            <div class="col-11 mb-3">
                <h3>Mes choix:</h3>
            </div>
            <div class="col-1 mb-3">
                <button type="button" class="btn btn-danger" id="addChoix"><i class="fas fa-plus"></i></button>
            </div>

        </div>
        <div id="choices">
            <div class="form-row align-items-center" id="choix1">
                <div class="col-11 mb-3">
                    <textarea class="form-control choix" name="choices_text" rows="2" required></textarea>
                    <div class="invalid-tooltip">
                        Donne-moi un sens.
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <button type="submit" class="btn btn-danger">Ajouter</button>
        </div>
    </form>
</div>
</body>
</html>
