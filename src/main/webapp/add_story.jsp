<%-- 
    Document   : create_story
    Created on : 28-Mar-2019, 05:52:45
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Story</title>
    </head>
    <body>
        <form action="add_story" method="post" accept-charset="UTF-8">
            <p>Entrez le premier paragraphe de l'histoire :
                <textarea name="premier_parag" rows="3" cols="70"></textarea>
            </p>
            <p> Mode écriture :
                <label><input type="radio" name="ecriture" value="libre"/>Libre</label>
                <label><input type="radio" name="ecriture" value="invitation"/>Sur invitation</label>
            </p>
            <p><input type="submit" name="creer" value="Créer"/></p>
        </form>
    </body>
</html>
