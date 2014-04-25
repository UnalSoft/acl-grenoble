<%-- 
    Document   : recruterAnimateurPermanent
    Created on : 11 avr. 2014, 20:27:27
    Author     : juanmanuelmartinezromero
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recruter Animateur</title>
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
                        <li><a href="ControleurAssociation">Administrer Demandes de Compte</a></li>
                        <li class="active"><a href="#">Recruter animateur permanent</a></li>
                        <li><a href="ControleurAssociation?action=genererFactures">Generer Factures</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a>${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Recruter Animateur Permanent</h1>
        </div>
        <div class="col-md-4 col-md-offset-4"> 
            <form method="POST" class="form-horizontal" action="ControleurAssociation">
                <div class="form-group">
                    <label for="prenom" class="col-sm-2 control-label">Prenom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="prenom" placeholder="Prenom" name="prenom" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nom" class="col-sm-2 control-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="nom" placeholder="Nom" name="nom" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="email" placeholder="Email" name="email" required>
                    </div>
                </div>
                <div class="form-group">
                    <h2>Competences</h2>
                    <c:forEach items="${competences}" var="competence">
                            <input type="checkbox" name="competences" value="${competence.toString()}" />${competence.getName()}<br>   
                    </c:forEach>
                </div>
                <div class="form-group">
                    <h2>Periodes de disponiblité</h2>
                    <c:forEach items="${periodes}" var="periode">
                        <div class="checkbox">
                            <label class="checkbox">
                                <input type="checkbox" name="periodes" value="${periode.nomPeriode()}" />${periode.nomPeriode()}
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default" name="action" value="insererAnimateur">Recruter</button>
                    <button type="reset" class="btn"  name="reinitialiser">Reinitialiser</button>
                    <input type="hidden" name="action" value="insererAnimateur" />
                </div>
            </form>
        </div>
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
                                <p>L'animateur a été inseré avec succes</p>
                            </c:if>
                            <c:if test="${creationReussi == false}">
                                <p>L'animateur existe déjà ou L'information donnée est incorrect, veuillez verifier</p>
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
