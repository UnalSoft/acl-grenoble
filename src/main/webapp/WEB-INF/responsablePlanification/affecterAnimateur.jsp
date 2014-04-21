<%-- 
    Document   : affecterAnimateur
    Created on : 11 avr. 2014, 20:29:08
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Affecter Animateurs</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
    </head>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="ControleurPlanification">ACL Grenoble</a>
            </div>
            <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                <ul class="nav navbar-nav">
                    <li class="active"><a>Affecter animateurs à une activité</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="disabled"><a href="#">${utilisateur}</a></li>
                    <li><a href="ControleurPlanification?action=logout">Fermer la session</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="page-header">
        <h1 class="text-center">Affecter animateurs à une activité</h1>
    </div>
    <div class="form-table">
        <table class="table table-striped">
            <tr>
                <th>Activité</th>
                <th>Date de Début</th>
                <th>Date de Fin</th>
                <th>Affecter Activité</th>
            </tr>
            <c:forEach items="${etatsPreconfirmes}" var="etat">
                <tr>
                    <td>${etat.activite.nomActivite}</td>
                    <td>${etat.periode.dateDebut}</td>
                    <td>${etat.periode.dateFin}</td>
                    <td><a href="ControleurAssociation?action=affecter&resp=${etat}" class="btn btn-success">Accepter</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>         
</html>