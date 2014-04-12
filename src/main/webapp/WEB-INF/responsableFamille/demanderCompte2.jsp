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
        <% try{
            int nombreEnfants = 
                Integer.parseInt(request.getParameter("nomEnfants"));
            }
            catch(Exception e){
                System.out.println(request.getParameter("nomEnfants"));
            }    
            %> 
        <form action="ControleurFamille" method="POST">
            <% for(int i=1;i<=4;i++){ %>
                Enfant nro <%= i%>:<br>
                Prenom: <input type="text" name="prenomE" value="" />
                Age: <input type="text" name="ageE" value="" />
            <% } %>
        </form>
    </body>
</html>
