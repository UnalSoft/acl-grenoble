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
        <title>ACL Grenoble - Demander Compte</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="ControleurFamille?action=demanderCompte">Demander un compte</a></li>
                        <li><a href="ControleurExterne?action=sInscrire.jsp">S’inscrire comme animateur externe</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Information d'enfants</h1>
        </div>
        <%   String nombreEnfants = request.getParameter("nombreE");
            int nomEnfants = Integer.parseInt(nombreEnfants);
        %> 
        <div class="container">
            <form method="POST" action="ControleurFamille" >
                <div class="container-fluid">
                    <input type="hidden" name="action" value="demander2" />
                    <% for (int i = 1; i <= nomEnfants; i++) {%>
                    <h3>Enfant <%= i%>:</h3>
                    Prenom: <input type="text" name="PrenomE<%=i%>"  value="" />
                    Age: <input type="text" name="AgeE<%=i%>" value="" /><br/>
                    <% }%>
                    <input type="submit" value="Submit" />
                </div>
            </form>
        </div>
    </body>
</html>
