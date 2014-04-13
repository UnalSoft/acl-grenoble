<%-- 
    Document   : accueilFamille
    Created on : 12 avr. 2014, 10:59:50
    Author     : LEONARDO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="ControleurFamille" method="POST">
            <select name="action" size="2" disabled="disabled">
                <option>demander1</option>
                <option>Inscrire un Enfant</option>
                <option>Annuler une inscription</option>                
            </select>
            <input type="submit" value="Submit" />
        </form>        
    </body>
</html>
