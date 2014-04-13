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
            <h2>Information du responsable du famille</h2>
            Prenom: <input type="text" name="prenomR" value="" /><br/>         
            Nom: <input type="text" name="nomR" value="" /><br/>
            Email: <input type="text" name="emailR" value="" /><br/>
            <h2>Information de la famille</h2>
            Nom de famille: <input type="text" name="nomF" value="" /><br/>
            Revenu Annuel: <input type="text" name="revenuF" value="" /><br/>
            <h2>Information des enfants</h2>
            Nombre d'enfants: <input type="text" name="nombreE" value="" /><br/>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
