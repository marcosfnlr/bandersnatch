<%@ page import="model.Paragraph" %>
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
    <link rel="stylesheet" href="css/edit.css">
    <title>Nouveau Paragraphe</title>
</head>
<body>
<%
    Paragraph paragraph = (Paragraph) request.getAttribute("paragraph");
    List<Paragraph> paragraphs = (List<Paragraph>) session.getAttribute("paragraphs");
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
    <div class="row bg-light">
        <div class="col-12 col-lg-10 bg-white">
            <h1>
                <%=paragraph.getBook().getTitle()%>
            </h1>
            <hr>
            <div class="row multi-collapse-parag collapse show justify-content-center" id="parag">
                <div class="col-12 mb-3 text-center">
                    <%=paragraph.getText() %>
                </div>
                <button type="button" class="btn btn-danger" data-toggle="collapse"
                        data-target=".multi-collapse-parag" aria-expanded="false"
                        aria-controls="edit-parag parag"><i
                        class="fas fa-edit"></i>
                </button>
            </div>
            <%
                if (true) {
            %>
            <div class="collapse multi-collapse-parag" id="edit-parag">
                <form action="update_parag" method="post" class="custom-validation" novalidate>
                    <div class="form-row">
                        <div class="col-12 mb-2">
                            <textarea class="form-control" name="text" rows="3"
                                      required><%=paragraph.getText()%></textarea>
                            <div class="invalid-tooltip">
                                Donne-moi une vie.
                            </div>
                        </div>
                    </div>
                    <input type="hidden" value="<%=paragraph.getIdParagraph()%>" name="id">
                    <div class="row justify-content-center">
                        <button type="button" class="btn btn-danger" data-toggle="collapse"
                                data-target=".multi-collapse-parag" aria-expanded="false"
                                aria-controls="edit-parag parag">
                            <i class="fas fa-arrow-circle-left"></i> Annuler
                        </button>
                        <button type="submit" class="btn btn-danger ml-2">
                            <i class="fas fa-check-circle"></i> Confirmer
                        </button>
                    </div>
                </form>
            </div>
            <%
                }
            %>
            <%
                for (Choice c : paragraph.getChoices()) {
            %>
            <div class="card multi-collapse-<%=c.getIdChoice()%> collapse show mt-3" id="choice-<%=c.getIdChoice()%>">
                <div class="row card-body">
                    <div class="col-8">
                        <%=c.getText()%>
                    </div>
                    <div class="col-4 align-self-center text-right">
                        <a class="btn btn-light"
                           href="paragraph_controller?action=write_paragraph&title=<%=paragraph.getBook().getTitle()%>&id_book=<%=paragraph.getBook().getIdBook()%>&id_choice_orig=<%=c.getIdChoice()%>&beginning=false&id_book=<%=paragraph.getBook().getIdBook()%>">
                            <i class="fas fa-tag"></i></a>
                        <a class="btn btn-light" href="#"><i class="fas fa-link"></i></a>
                        <button type="button" class="btn btn-light" data-toggle="collapse"
                                data-target=".multi-collapse-<%=c.getIdChoice()%>" aria-expanded="false"
                                aria-controls="edit-choice-<%=c.getIdChoice()%> choice-<%=c.getIdChoice()%>"><i
                                class="fas fa-edit"></i></button>
                    </div>
                </div>
            </div>
            <%
                if (true) {
            %>
            <div class="collapse multi-collapse-<%=c.getIdChoice()%> mt-3" id="edit-choice-<%=c.getIdChoice()%>">
                <form action="update_choice" method="post" class="custom-validation" novalidate>
                    <div class="form-row">
                        <div class="col-12 mb-2">
                            <textarea class="form-control" name="text" rows="2" required><%=c.getText()%></textarea>
                            <div class="invalid-tooltip">
                                Donne-moi une vie.
                            </div>
                        </div>
                    </div>
                    <input type="hidden" value="<%=c.getIdChoice()%>" name="id">
                    <div class="row justify-content-center">
                        <button type="button" class="btn btn-danger" data-toggle="collapse"
                                data-target=".multi-collapse-<%=c.getIdChoice()%>" aria-expanded="false"
                                aria-controls="edit-choice-<%=c.getIdChoice()%> choice-<%=c.getIdChoice()%>">
                            <i class="fas fa-arrow-circle-left"></i> Annuler
                        </button>
                        <button type="submit" class="btn btn-danger ml-2">
                            <i class="fas fa-check-circle"></i> Confirmer
                        </button>
                    </div>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="col-12 col-lg-2" id="list-parag">
            <%
                for (Paragraph p : paragraphs) {
            %>
            <a class="link-p mt-2" href="paragraph_controller?action=modify_paragraph&id=<%=p.getIdParagraph()%>">
                <%=p.getLabelText()%>
            </a>
            <%
                }
            %>
        </div>
    </div>
</div>
</body>
</html>
