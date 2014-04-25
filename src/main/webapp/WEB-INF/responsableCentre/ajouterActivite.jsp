<%-- 
    Document   : ajouterActivite
    Created on : 11 avr. 2014, 20:27:59
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                        <li><a>${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
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
                    <label for="theme" class="col-sm-4 control-label">Thèmes</label>
                    <div class="col-sm-8">
                        <select name="theme" class="form-control" required>
                            <c:forEach items="${themes}" var="theme">
                                <option value="${theme.theme.name}">${theme.theme.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="descriptif" class="col-sm-4 control-label">Description</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" rows="5" id="descriptif" placeholder="Descriptif" name="descriptif" required></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="periode" class="col-sm-4 control-label">Periode</label>
                    <div class="col-sm-8">
                        <select name="periode" class="form-control" required>
                            <c:forEach items="${periodes}" var="periode">
                                <option value="${periode.nomPeriode()}">${periode.nomPeriode()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dateDebut" class="col-sm-4 control-label">Date de Debut</label>
                    <div class="col-sm-8">
                        <input type="date" class="form-control" id="dateDebut" placeholder="aaaa-mm-dd" name="dateDebut" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dateFin" class="col-sm-4 control-label">Date de Fin</label>
                    <div class="col-sm-8">
                        <input type="date" class="form-control" id="dateFin" placeholder="aaaa-mm-dd" name="dateFin" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nombreAnimateurs" class="col-sm-4 control-label">N. Animateurs</label>
                    <div class="col-sm-8">
                        <input type="number" class="form-control" id="nombreAnimateurs" name="nombreAnimateurs" min="1" value="1" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="prix" class="col-sm-4 control-label">Prix par jour</label>
                    <div class="col-sm-8">
                        <input type="number" class="form-control" id="prix" name="prix" min="10" value="10" step="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="competences" class="col-sm-4 control-label">Competences</label>
                    <div class="row col-md-6 col-md-offset-4">
                        <c:forEach items="${competences}" var="competence">
                            <input type="checkbox"name="competences" value="${competence.toString()}" />${competence.getName()}<br>
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
        <c:if test="${creationReussi !=null}">
            <div id="modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <c:if test="${creationReussi == true}">
                                <h4 class="modal-title" id="myModalLabel">Insertion réussi</h4>
                            </c:if>
                            <c:if test="${creationReussi == false}">
                                <h4 class="modal-title" id="myModalLabel">Insertion échoué</h4>
                            </c:if>
                        </div>
                        <div class="modal-body">
                            <c:if test="${creationReussi == true}">
                                <p>L'activité a été inseré avec succes</p>
                            </c:if>
                            <c:if test="${creationReussi == false}">
                                <p>L'information donnée est incorrect, veuillez 
                                    verifier que les données soient valides et 
                                    que les dates correspondent aux periodes</p>
                            </c:if>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                $('#modal').modal('show');
            </script>
        </c:if>
    </body>
</html>
