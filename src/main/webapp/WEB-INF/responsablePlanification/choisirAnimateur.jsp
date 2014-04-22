<%-- 
    Document   : choisirAnimateur
    Created on : 21 avr. 2014, 15:17:54
    Author     : Edward
--%>

<%@page import="com.unsoft.acl_grenoble.model.centre.Periode"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choisir Animateurs</title>
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
                    <a class="navbar-brand" href="ControleurPlanification">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <ul class="nav navbar-nav">
                        <li class="active"><a>Affecter animateurs à une activité</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="disabled"><a href="#">${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Affecter animateurs à une activité</h1>
        </div>

        <div class="form-table">
            <h2>Activite: ${periode.nomPeriode()}</h2>
            <h3>Enfants inscris: ${nbInscris}</h3>
            <h3>Animateurs necessaires =  Min:${nbMinAnim} - Max: ${nbMaxAnim}</h3>
            <hr>

            <c:if test="${besoinExtern !=null}">
                <c:if test="${besoinExtern == true}">
                    <h3 class="text-center">Animateurs Permanents</h3>
                    <table class="table table-striped">
                        <tr>
                            <th>Nom</th>
                            <th>Prenom</th>
                            <th></th>
                        </tr>
                        <%  String[] anims = (String[]) request.getAttribute("animInternes");
                            for (String anim : anims) {
                                String[] nomPrenom = anim.split(":");
                                String nom = nomPrenom[0];
                                String prenom = nomPrenom[1];
                        %>
                        <tr>
                            <td><%=nom%></td>
                            <td><%=prenom%></td>
                            <td><input type="checkbox" name="animateurs" disabled="true" /></td>
                        </tr>
                        <%}%>
                    </table>
                </c:if>
            </c:if>
        </div>
        <form method="POST" class="form-table" action="ControleurPlanification">
            <c:if test="${besoinExtern == null || besoinExtern == false}">
                <h3 class="text-center">Animateurs Permanents</h3>
            </c:if>
            <c:if test="${besoinExtern == true}">
                <h3 class="text-center">Animateurs Externes</h3>
            </c:if>
            <table class="table table-striped">
                <tr>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th></th>
                </tr>
                <c:forEach items="${animateurs}" var="anim">
                    <tr>
                        <td>${anim.getNomAnimateur()}</td>
                        <td>${anim.getPrenomAnimateur()}</td>
                        <td><input type="checkbox" name="animateurs" value="${anim.getNomAnimateur()}:${anim.getPrenomAnimateur()}" /></td>
                    </tr>
                </c:forEach>
            </table>
            <input type="hidden" name="action" value="animsInternes" />
            <input type="hidden" name="idActivite" value="${activite.getIdActivite()}" />
            <input type="hidden" name="periode" value="${periode.nomPeriode()}" />
            <input type="hidden" name="nbInscris" value="${nbInscris}" />
            <input type="hidden" name="nbMinAnim" value="${nbMinAnim}" />
            <input type="hidden" name="nbMaxAnim" value="${nbMaxAnim}" />
            <input type="hidden" name="nbAnimsDisp" value="${animateurs.size()}" />
            <button type="submit" class="btn btn-primary" name="action" value="animsInternes">Choisir</button>
        </form>

        <c:if test="${besoinExtern !=null}">
            <div id="modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Animateurs insuffisants</h4>
                        </div>
                        <div class="modal-body">
                            <p>Vous devez choisir au moins ${nbMinAnim} animateur(s)!</p>
                            <c:if test="${besoinExtern == false}">
                                <p>Vous avez encore des animateurs permanents disponibles!</p>
                            </c:if>
                            <c:if test="${besoinExtern == true}">
                                <p>Vous pouvez recruter des animateurs externes à continuation</p>
                            </c:if>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Continuer</button>
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
