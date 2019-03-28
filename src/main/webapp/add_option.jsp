<%-- 
    Document   : create_option
    Created on : 28-Mar-2019, 06:04:47
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Option</title>
    </head>
    <body>
        <form action="add_option" method="post" accept-charset="UTF-8">
            <p>Entrez une choix :
                <textarea name="choix" rows="3" cols="70"></textarea>
            </p>
            <p><input type="submit" name="ajouter" value="Ajouter"/></p>
        </form>
    </body>
</html>
