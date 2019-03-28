<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Bandersnatch</title>
</head>
<body>
<h1>Connexion</h1>
<form method="post" action="check_user" accept-charset="UTF-8">
    <ul>
        <li> Login: <input type="text" name="login"/></li>
        <li> Mot de passe: <input type="password" name="password"/></li>
    </ul>
    <input type="submit" name="Login"/>
</form>
<p><a href="register.jsp">Registration</a></p>
<p><a href="guest_main_page.jsp">Guest Access</a></p>
</body>
</html>