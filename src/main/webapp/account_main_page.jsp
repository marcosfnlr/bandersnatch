<%-- 
    Document   : account_main_page
    Created on : 28-Mar-2019, 05:27:02
    Author     : raphaelcja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
    <body>
        <form action="account_controller" method="get" accept-charset="UTF-8">   
            <label class='logout'>
            <input type="submit" name="logout" value="Logout"/> 
            </label>
            <input type="hidden" name="action" value="logout_account" />
        </form>
        <h1>Mode Lecture</h1>
        <h2 id="catalogue" class="1">Catalogue</h2>
        <ul>
            <li><span class="titre">Livre 1 - Example</span></li>
        </ul>
        <h1>Mode Écriture</h1>
        <p><a href="add_book.jsp">Créer une nouvelle histoire</a></p>
        <h2 id="catalogue" class="1">Catalogue</h2>
        <ul>
            <li><span class="titre">Livre 1</span></li>
        </ul>
    </body>
</html>
