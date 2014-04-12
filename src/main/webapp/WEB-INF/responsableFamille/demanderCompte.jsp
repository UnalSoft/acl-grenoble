<%-- 
    Document   : demanderCompte
    Created on : 11 avr. 2014, 20:30:19
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="ControleurFamille">
            <input type="hidden" name="action" value="demander1" />
            <h1>Information du responsable du famille</h1>
            Prenom: <input type="text" name="prenomR" value="" />           
            Nom: <input type="text" name="nomR" value="" />
            Email: <input type="text" name="emailR" value="" />
            <h1>Information de la famille</h1>
            Nom de famille: <input type="text" name="nomF" value="" />
            Revenu Annuel: <input type="text" name="revenuF" value="" />
            <h1>Information des enfants</h1>
            Nombre d'enfants: <input type="text" name="nombreE" value="" />
            <input type="submit" name="Envoyer"/>
        </form>
    </body>
</html>
