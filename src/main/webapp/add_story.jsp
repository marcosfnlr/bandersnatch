<%-- 
    Document   : create_story
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

<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">BSnatch</a>
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
    <h1>Nouvelle Histoire</h1>
    <form action="add_parag.jsp" class="custom-validation" novalidate>
        <input type="hidden" name="isNew" value="true">
        <div class="form-row">
            <div class="col-12 col-lg-8 mb-3">
                <label for="nom">Nom de l'histoire:</label>
                <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrez nom" required>
                <div class="invalid-tooltip">
                    Dis-moi un nom cool.
                </div>
            </div>
            <div class="col-12 col-lg-4 mb-3">
                <label for="mode">Mode écriture:</label>
                <div id="mode">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="mode" id="modeLibre" value="true"
                               checked="checked">
                        <label class="form-check-label" for="modeLibre">Libre</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="mode" id="modeInvitation" value="false">
                        <label class="form-check-label" for="modeInvitation">Sur invitation</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <button id="creer" type="submit" class="btn btn-danger">Créer</button>
        </div>
    </form>
</div>
</body>
</html>