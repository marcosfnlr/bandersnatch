<%-- 
    Document   : register
    Created on : 28-Mar-2019, 05:26:11
    Author     : raphaelcja
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.FeedbackMessage" %>
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
    <link rel="stylesheet" href="css/index.css">
    <title>Sign Up</title>
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center" id="index-top">
        <span>Bandersnatch</span>: Sponsorisé par perso<img src="images/n.png"/><img
            src="images/n.png"/>e
    </div>
</div>
<div class="container">
    <div class="form-row justify-content-center">
        <div class="col-12 col-lg-8 mt-3">
            <h1>Sign Up</h1>
        </div>
    </div>
    <%
        List<FeedbackMessage> feedbackMessages = (List<FeedbackMessage>) request.getAttribute("feedbackMessages");
        if (feedbackMessages != null && !feedbackMessages.isEmpty()) {
            for (FeedbackMessage fm : feedbackMessages) {
    %>
    <div class="<%=fm.getAlertClasses()%> mt-2" role="alert">
        <%=fm.getMessage()%>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
            }
        }
    %>
    <form class="custom-validation mt-3" method="post" action="account_controller" accept-charset="UTF-8" novalidate>
        <div class="form-row justify-content-center">
            <div class="col-12 col-lg-4 mb-3">
                <label for="last_name">Nom:</label>
                <input type="text" class="form-control" name="last_name" id="last_name" required/>
                <div class="invalid-tooltip">
                    Comment tu t'appele?
                </div>
            </div>
            <div class="col-12 col-lg-4 mb-3">
                <label for="first_name">Prénom:</label>
                <input type="text" class="form-control" name="first_name" id="first_name" required/>
                <div class="invalid-tooltip">
                    Comment tu t'appele?
                </div>
            </div>
        </div>
        <div class="form-row justify-content-center">
            <div class="col-12 col-lg-4 mb-3">
                <label for="id_account">Login:</label>
                <input type="text" class="form-control" name="id_account" id="id_account" required/>
                <div class="invalid-tooltip">
                    C'est comment on va t'indifier.
                </div>
            </div>
            <div class="col-12 col-lg-4 mb-3">
                <label for="first_name">Mot de passe:</label>
                <input type="password" class="form-control" name="password" id="password" required/>
                <div class="invalid-tooltip">
                    C'est notre secret.
                </div>
            </div>
        </div>
        <input type="hidden" name="action" value="add_account"/>
        <div class="row justify-content-center mt-">
            <button type="submit" class="btn btn-danger">Créer</button>
        </div>
        <input type="hidden" name="action" value="check_account"/>
    </form>
    <div class="form-row justify-content-center">
        <div class="col-12 col-lg-8 mt-3">
            <a href="index.jsp">Annuler</a>
        </div>
    </div>

</div>
</body>
</html>
