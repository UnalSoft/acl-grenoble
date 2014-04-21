<%-- 
    Document   : ajouterActivite
    Created on : 11 avr. 2014, 20:27:59
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajouter Activité</title>
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
                    <a class="navbar-brand" href="ControleurCentre">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <ul class="nav navbar-nav">
                        <li class="active"><a>Ajouter Activité</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="disabled"><a href="#">${utilisateur}</a></li>
                        <li><a href="ControleurCentre?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Ajouter activité</h1>
        </div>
        <form method="POST" class="form-horizontal" action="ControleurCentre">
            <div class="col-md-6 col-md-offset-3">

                <div class="form-group">
                    <label for="nom" class="col-sm-4 control-label">Nom de l'activité</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="nom" placeholder="Nom" name="nom" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Themes" class="col-sm-4 control-label">Thèmes</label>
                    <div class="col-sm-8">
                        <select name="Themes" class="form-control" required>
                            <c:forEach items="${themes}" var="theme">
                                <option value="${theme}">${theme.theme.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="descriptif" class="col-sm-4 control-label">Description</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" rows="5" id="descriptif" placeholder="Descriptif">${message}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="periodes" class="col-sm-4 control-label">Periodes</label>
                    <div class="row col-md-6 col-md-offset-4">
                        <c:forEach items="${periodes}" var="periode">
                            <input type="checkbox"name="periodes" value="${periode.nomPeriode()}" />${periode.nomPeriode()}<br>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nombreAnimateurs" class="col-sm-4 control-label">N. Animateurs</label>
                    <div class="col-sm-8">
                        <input type="number" class="form-control" id="nombreAnimateurs" name="nombreAnimateurs" min="1" value="1">
                    </div>
                </div>
                <div class="form-group">
                    <label for="competences" class="col-sm-4 control-label">Competences</label>
                    <div class="row col-md-6 col-md-offset-4">
                        <c:forEach items="${competences}" var="competence">
                            <label class="checkbox-inline">
                                <input type="checkbox"name="competences" value="${competence.toString()}" />${competence.getName()}<br>
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default col-md-offset-5" name="action" value="insererAnimateur">Créer activité</button>
                    <button type="reset" class="btn"  name="reinitialiser">Reinitialiser</button>
                    <input type="hidden" name="action" value="insererAnimateur" />
                </div>
            </div>

        </form>

    </body>
</html>
