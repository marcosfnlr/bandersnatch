<%-- 
    Document   : register
    Created on : 28-Mar-2019, 05:26:11
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <form action="account_controller" method="post" accept-charset="UTF-8">
            <p> Nom : <input type="text" name="last_name" size="15"/></p>
            
            <p> Prénom : <input type="text" name="first_name" size="15"/></p>
            
            <hr style="border: 1px solid black;" />
            
            <p> Login : <input type="text" name="id_account" size="15"/></p>
            
            <p> Mot de passe : <input type="password" name="password" size="15"/></p>
            
            <a href="index.jsp">Annuler</a>
            
            <p><input type="submit" name="creer" value="Créer"/></p>
            
            <input type="hidden" name="action" value="add_account" />
            
        </form>
    </body>
</html>
