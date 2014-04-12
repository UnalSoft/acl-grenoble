<%-- 
    Document   : demanderCompte2
    Created on : 12 avr. 2014, 11:31:19
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
        <%   String nombreEnfants = (String)
                request.getAttribute("nomEnfants");                
             int nomEnfants = Integer.parseInt(nombreEnfants);
            %> 
        <form action="ControleurFamille" method="POST">
            <% for(int i=1;i<=nomEnfants;i++){ %>
            <h3>Enfant <%= i%>:</h3>
                Prenom: <input type="text" name=<% String.valueOf(i); %>  value="" />
                Age: <input type="text" name=<% String.valueOf(i); %> value="" /><br/>
            <% } %>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
