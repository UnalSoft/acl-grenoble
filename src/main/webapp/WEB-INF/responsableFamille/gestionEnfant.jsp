<%-- 
    Document   : inscrireEnfant
    Created on : 11 avr. 2014, 20:30:35
    Author     : sparrow
--%>

<%@page contentType="text/html charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion d'Enfant - ACL Grenoble</title>
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
                        <li><a href="">Inscrire un Enfant</a></li>
                        <li class="active"><a href="">Gestion d'enfant</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="disabled"><a href="#">${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Gestion d'enfant: ${prenom} ${nom}</h1>
        </div>
        <div class="form-table col-lg-8 col-lg-offset-2">
            <table class="table table-striped">
                
                <tr>
                    <th>ID</th>
                    <th>Nom Activite</th>
                    <th>Periode</th>
                    <th></th>
                </tr>
                <c:forEach items="${listeActivites}" var="act">
                    <!-- Code pour montrer l'information de chaque enfant -->
                    <tr>
                        <td>${act.getActivite().getIdActivite()}</td>
                        <td>${act.getActivite().getNomActivite()}</td>
                        <td>${act.getPeriode()}</td>
                        
                        <td><a href="ControleurFamille?action=verifEffacer&nom=${nom}&prenom=${prenom}&activite=${act.getActivite().getIdActivite()}&periode=${act.getPeriode().nomPeriode()}" class="btn btn-warning">Desinscrire</a></td>
                        <!-- Code pour montrer l'information de chaque activite dans lequel il est present -->

                    </tr>
                </c:forEach>
            </table>
        </div>

    </body>
</html>
