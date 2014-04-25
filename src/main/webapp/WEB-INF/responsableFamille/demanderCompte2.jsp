<%-- 
    Document   : demanderCompte2
    Created on : 12 avr. 2014, 11:31:19
    Author     : LEONARDO
--%>

<%@page contentType="text/html charset=UTF-8" pageEncoding="UTF-8"%>
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
                    <a class="navbar-brand" href="index.jsp">ACL Grenoble</a>
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



            <%   String nombreEnfants = request.getParameter("nombreE");
                int nomEnfants = Integer.parseInt(nombreEnfants);

                String prenomR = request.getParameter("prenomR");
                String nomR = request.getParameter("nomR");
                String emailR = request.getParameter("emailR");
                String nomF = request.getParameter("nomF");
                String revenuF = request.getParameter("revenuF");


            %> 

            <dl class="dl-horizontal">

                <h3 class="text-center">Information courante</h3>
                <dt>Mr/Mme </strong>
                <dd><%=prenomR%> <%=nomR%></dd>
                <dt>Email</dt>
                <dd><%=emailR%></dd>
                <dt>Revenu</dt>
                <dd>EUR <%=revenuF%></dd>
            </dl>

            <div class="page-header">
                <h1 class="text-center">Information d'enfants</h1>
            </div>

            <div class="container">
                <form method="POST" action="ControleurFamille" >
                    <div class="container-fluid">
                        <input type="hidden" name="action" value="demander2" />
                        <input type="hidden" name="prenomR" value="<%=prenomR%>" />
                        <input type="hidden" name="nomR" value="<%=nomR%>" />
                        <input type="hidden" name="emailR" value="<%=emailR%>" />
                        <input type="hidden" name="revenuF" value="<%=revenuF%>" />
                        <input type="hidden" name="nomEnfants" value="<%=nomEnfants%>" />
                        <div class=".col-lg-6 .col-lg-offset-3">
                            <form class="form-control" role="form">


                                <% for (int i = 1; i <= nomEnfants; i++) {%>


                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-8">
                                        <h3>Enfant <%= i%>:</h3>
                                        <label for="PrenomE<%=i%>" class="control-label">Prenom d'enfant</label>
                                        <div >
                                            <input type="text" class="form-control" id="PrenomE<%=i%>" name="PrenomE<%=i%>" required >
                                        </div>
                                        <label for="NomE<%=i%>" class="control-label">Nom d'enfant</label>
                                        <div>
                                            <input type="text" class="form-control" name="NomE<%=i%>" id="NomE<%=i%>" required>
                                        </div>
                                        <label for="AgeE<%=i%>" class="control-label">Age d'enfant</label>
                                        <div>
                                            <input type="number" class="form-control" id="AgeE<%=i%>" name="AgeE<%=i%>" required>
                                        </div>
                                    </div>
                                </div>

                                <hr class="divider">


                                <% }%>

                                
                                <div class="form-group">
                                    <div class="col-sm-offset-10 col-sm-2 ">
                                        <button type="submit" class="btn btn-default">Submit</button>
                                    </div>
                                </div>


                            </form>

                        </div>

                    </div>
                </form>
            </div>

        </div>


</html>
