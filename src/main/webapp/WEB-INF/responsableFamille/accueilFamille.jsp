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
        <jsp:forward page="demanderCompte.jsp"/>
        <a href="inscrireEnfant.jsp">inscrire enfant</a>
        <a href="annulerInscription.jsp">annuler inscription</a>
    </body>
</html>