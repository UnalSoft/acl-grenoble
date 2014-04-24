<%-- 
    Document   : inscrireEnfant
    Created on : 11 avr. 2014, 20:30:35
    Author     : sparrow
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mes enfants - ACL Grenoble</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>

    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="ControleurAssociation">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <ul class="nav navbar-nav">
                        <li class="disabled"><a href="#">Inscrire un Enfant</a></li>
                        <li class="disabled"><a href="#">Gestion d'Enfant</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="disabled"><a href="#">${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Mes enfants</h1>
        </div>
        <div class="form-table col-lg-8 col-lg-offset-2">
            <table class="table table-striped">
                <!-- Test-->
                <tr>
                    <th>#</th>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th>Age</th>
                    <th></th>
                    <th></th>
                </tr>
                <% int i = 0; %>
                <c:forEach items="${enfants}" var="enf">
                    <%i++;%>
                    <!-- Code pour montrer l'information de chaque enfant -->
                    <tr>
                        <td><%=i%></td>
                        <td>${enf.getNomEnfant()}</td>
                        <td>${enf.getPrenomEnfant()}</td>
                        <td>${enf.getAge()}</td>

                        <td><a href="ControleurFamille?action=inscrire&nom=${enf.getNomEnfant()}&prenom=${enf.getPrenomEnfant()}" class="btn btn-success">Inscrire à une nouvelle activité</a></td>
                        <!-- Code pour montrer l'information de chaque activite dans lequel il est present -->

                        <td><a href="ControleurFamille?action=gestion&nom=${enf.getNomEnfant()}&prenom=${enf.getPrenomEnfant()}" class="btn btn-info">Plus...</a></td>

                    </tr>
                </c:forEach>
            </table>
        </div>

    </body>
</html>
