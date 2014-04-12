<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACL Grenoble</title>
    </head>
    <body>
        <h1>ACL Grenoble</h1>
        <h2>Connexion</h2>
        <form method="GET" action="ControlUtilisateur">
            <ul>
                <li> User : <input type="text" name="user"/></li>
                <li> Mot de passe : <input type="password" name="motDePass"/></li>
            </ul>
            <input type="submit" name="Connexion"/>
        </form>
    </body>
</html>
