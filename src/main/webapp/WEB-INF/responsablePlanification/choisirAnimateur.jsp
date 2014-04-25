<%-- 
    Document   : choisirAnimateur
    Created on : 21 avr. 2014, 15:17:54
    Author     : Edward
--%>

<%@page import="com.unsoft.acl_grenoble.model.centre.Periode"%>
<%@page contentType="text/html charset=UTF-8" pageEncoding="UTF-8"%>
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
                        <li><a>${utilisateur}</a></li>
                        <li><a href="ControleurUtilisateur?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Affecter animateurs à une activité</h1>
        </div>

        <form method="POST" class="form-table" action="ControleurPlanification">
            <div class="form-table">
                <h2>Activite: ${periode.nomPeriode()}</h2>
                <h3>Enfants inscris: ${nbInscris}</h3>
                <h3>Animateurs necessaires =  Min:${nbMinAnim} - Max: ${nbMaxAnim}</h3>
                <h3>Competences requis: 
                    <c:forEach items="${competences}" var="comp">
                        ${comp.getName()},
                    </c:forEach>
                </h3>
                <hr>

                <c:if test="${besoinExtern !=null}">
                    <c:if test="${besoinExtern == true}">
                        <h3 class="text-center">Animateurs Permanents Choisis</h3>
                        <table class="table table-striped">
                            <tr>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th></th>
                            </tr>
                            <%  String[] anims = (String[]) request.getAttribute("animInternes");
                                if (anims != null) {
                                    for (String anim : anims) {
                                        String[] nomPrenom = anim.split(":");
                                        String nom = nomPrenom[0];
                                        String prenom = nomPrenom[1];
                            %>
                            <tr>
                                <td><%=nom%></td>
                                <td><%=prenom%></td>
                                <td><input type="checkbox" name="animateursChoisis" checked="true"  value="<%=nom%>:<%=prenom%>" /></td>
                            </tr>
                            <%}
                                }%>
                        </table>
                    </c:if>
                </c:if>
            </div>
            <c:if test="${besoinExtern == null || besoinExtern == false}">
                <h3 class="text-center">Animateurs Permanents Disponibles</h3>
            </c:if>
            <c:if test="${besoinExtern == true}">
                <h3 class="text-center">Animateurs Externes Disponibles</h3>
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
            <c:if test="${besoinExtern !=null}">
                <c:if test="${besoinExtern == true && impossible != true}">
                    <input type="hidden" name="action" value="animsExternes" />
                </c:if>
                <c:if test="${besoinExtern == false}">
                    <input type="hidden" name="action" value="animsInternes" />
                </c:if>

            </c:if>
            <c:if test="${besoinExtern ==null}">
                <input type="hidden" name="action" value="animsInternes" />
            </c:if>
            <input type="hidden" name="idActivite" value="${activite.getIdActivite()}" />
            <input type="hidden" name="periode" value="${periode.nomPeriode()}" />
            <input type="hidden" name="nbInscris" value="${nbInscris}" />
            <input type="hidden" name="nbMinAnim" value="${nbMinAnim}" />
            <input type="hidden" name="nbMaxAnim" value="${nbMaxAnim}" />
            <input type="hidden" name="nbAnimsDisp" value="${animateurs.size()}" />
            <div class="center-block">
                <c:if test="${impossible != true}">
                    <button type="submit" class="btn btn-primary btn-block" name="submit">Continuer</button>
                </c:if>
                <c:if test="${impossible == true}">
                    <p><button type="submit" class="btn btn-warning btn-block" name="submit">Continuer plus tard</button></p>
                    <p><button type="submit" class="btn btn-danger btn-block" name="action" value="annulerActivite">Annuler Activite</button></p>
                </c:if>
            </div>
        </form>

        <c:if test="${besoinExtern !=null}">
            <div id="modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <c:if test="${depasse == false}">
                                <h4 class="modal-title" id="myModalLabel">Animateurs insuffisants</h4>
                            </c:if>
                            <c:if test="${depasse == true}">
                                <h4 class="modal-title" id="myModalLabel">Trop d'Animateurs</h4>
                            </c:if>
                        </div>
                        <div class="modal-body">
                            <c:if test="${depasse == false}">
                                <p>Vous devez choisir au moins ${nbMinAnim} animateur(s)!</p>
                            </c:if>
                            <c:if test="${depasse == true}">
                                <p>Vous devez choisir au maximum ${nbMaxAnim} animateur(s)!</p>
                            </c:if>
                            <c:if test="${besoinExtern == false && depasse != true}">
                                <p>Vous avez encore des animateurs permanents disponibles!</p>
                            </c:if>
                            <c:if test="${besoinExtern == true && impossible != true && depasse != true}">
                                <p>Vous pouvez recruter des animateurs externes à continuation</p>
                            </c:if>
                            <c:if test="${impossible == true}">
                                <p>Il n'y a pas suffiçants animateurs pour l'activité.</p>
                                <p>Vous pouvez la canceler ou essayer plus tard.</p>
                            </c:if>    
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
