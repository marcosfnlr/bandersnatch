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
    <title>Bandersnatch</title>
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center" id="index-top">
        <span>Bandersnatch</span>: Sponsorisé par perso<img src="images/n.png"/><img
            src="images/n.png"/>e
    </div>
</div>
<div class="container">
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
    <div class="row justify-content-center mt-5">
        <div class="col-12 col-lg-4 align-self-center">
            <h1>Bienvenue!</h1>
            <p>Tu n'es pas encore membre? <a href="register.jsp">Deviens ici</a>!</p>
            <p>Tu peux aussi <a href="home.jsp">te connecter comme visiteur</a> <i
                    class="far fa-laugh-wink"></i></p>
        </div>
        <div class="col-12 col-lg-4">
            <form class="custom-validation" method="post" action="account_controller" accept-charset="UTF-8" novalidate>
                <h1>Login</h1>
                <div class="form-row justify-content-center">
                    <div class="col-12 mb-3">
                        <label for="id_account">Utilisateur:</label>
                        <input type="text" class="form-control" id="id_account" name="id_account" required>
                    </div>
                </div>
                <div class="form-row justify-content-center">
                    <div class="col-12 mb-3">
                        <label for="password">Mot de passe:</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <button type="submit" class="btn btn-danger">Login</button>
                </div>
                <input type="hidden" name="action" value="check_account" />
            </form>
        </div>
    </div>
</div>
</body>
</html>