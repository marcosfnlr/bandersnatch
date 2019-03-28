<%-- 
    Document   : add_parag
    Created on : 28-Mar-2019, 06:23:56
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Paragraphe</title>
    </head>
    <body>
        <form action="add_parag" method="post" accept-charset="UTF-8">
            <p>Entrez un paragraphe de l'histoire :
                <textarea name="parag" rows="3" cols="70"></textarea>
            </p>
            <p><input type="submit" name="ajouter" value="Ajouter"/></p>
        </form>
    </body>
</html>
