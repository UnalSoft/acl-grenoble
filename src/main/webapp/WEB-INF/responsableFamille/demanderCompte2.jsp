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
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-8">
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
                                Prenom: <input type="text" name="PrenomE<%=i%>"  />
                                Age: <input type="text" name="AgeE<%=i%>"/><br/>
                                <% }%>
                                <input type="submit" value="Submit" />
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <dl class="dl-horizontal">
                        <%
                            String prenomR = request.getParameter("prenomR");
                            String nomR = request.getParameter("nomR");
                            String emailR = request.getParameter("emailR");
                            String nomF = request.getParameter("nomF");
                            String revenuF = request.getParameter("revenuF");

                        %>
                        <input type="hidden" name="prenomR" value="<%=prenomR%>" />
                        <input type="hidden" name="nomR" value="<%=nomR%>" />
                        <input type="hidden" name="emailR" value="<%=emailR%>" />
                        <input type="hidden" name="nomF" value="<%=nomF%>" />
                        <input type="hidden" name="revenuF" value="<%=revenuF%>" />
                        <input type="hidden" name="nomEnfants" value="<%=nomEnfants%>" />
                                
                        <h3>Information courante</h3>
                        <strong style="column-fill: auto">Mr <%=prenomR%> <%=nomR%></strong>
                        <dt>Email:</dt>
                        <dd><%=emailR%></dd>
                        <dt>Famille Gestionnée:</dt>
                        <dd><%=nomF%></dd>
                        <dt>Revenu:</dt>
                        <dd>EUR <%=revenuF%></dd>
                    </dl>
                </div>
            </div>
        </div>

</html>
