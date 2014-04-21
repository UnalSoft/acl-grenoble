<%-- 
    Document   : administrerDemandeCompte
    Created on : 11 avr. 2014, 20:20:32
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrer Compte</title>
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
                        <li class="active"><a href="#">Administrer Demandes de Compte</a></li>
                        <li><a href="ControleurAssociation?action=recruterAnimateur">Recruter animateur permanent</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="disabled"><a href="#">${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Administrer Demandes de Compte</h1>
        </div>
        <div class="form-table">
            <table class="table table-striped">
                <tr>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th>Ressources</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${familles}" var="fam">
                    <tr>
                        <td>${fam.getNomFamille()}</td>
                        <td>${fam.getPrenom()}</td>
                        <td>${fam.getRessources()}</td>
                        <td><a href="ControleurAssociation?action=accepter&resp=${fam.getCompte().getNomUtilisateur()}" class="btn btn-success">Accepter</a></td>
                        <td><a href="ControleurAssociation?action=refuser&resp=${fam.getCompte().getNomUtilisateur()}" class="btn btn-danger">Refuser</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <c:if test="${demande != null}">
            <!-- Modal Accepter -->
            <form method="POST" action="ControleurAssociation">
                <div id="modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <c:if test="${demande == true}">
                                    <h4 class="modal-title" id="myModalLabel">Accepter demande</h4>
                                </c:if>
                                <c:if test="${demande == false}">
                                    <h4 class="modal-title" id="myModalLabel">Refuser demande</h4>
                                </c:if>
                            </div>
                            <div class="modal-body">
                                <p>Le message suivant va être envoyée à: ${rFamille.getMail()}</p>
                                <textarea class="form-control" rows="10">${message}</textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Canceler</button>
                                <c:if test="${demande == true}">
                                    <input type="submit" value="Accepter" class="btn btn-primary"/>
                                    <input type="hidden" name="action" value="accepter" />
                                </c:if>
                                <c:if test="${demande == false}">
                                    <input type="submit" value="Refuser" class="btn btn-primary"/>
                                    <input type="hidden" name="action" value="refuser" />
                                </c:if>    
                                <input type="hidden" name="resp" value="${rFamille.getCompte().getNomUtilisateur()}" />
                                <input type="hidden" name="message" value="${message}" />
                            </div>

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </form>
            <script>
                $('#modal').modal('show');
            </script>
        </c:if>
    </body>
</html>
