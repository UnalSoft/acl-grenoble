<%-- 
    Document   : choisirAnimateur
    Created on : 21 avr. 2014, 15:17:54
    Author     : Edward
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <li><a href="ControleurPlanification?action=logout">Fermer la session</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Affecter animateurs à une activité</h1>
        </div>
        <div class="jumbotron">
            <h2>Activite: ${activite.getNomActivite()}</h2>
            <p>Periode: ${periode.getDateDebut()} - ${periode.getDateFin()}</p>
        </div>
        <form method="POST" class="form-table" action="ControleurAssociation">
            <table class="table table-striped">
                <tr>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th></th>
                </tr>
                <c:forEach items="${animateurs}" var="anim">
                    <tr>
                        <td>${anim.getNomFamille()}</td>
                        <td>${anim.getPrenom()}</td>
                        <td><input type="checkbox" name="anims" value="${anim.getNomFamille()}+${anim.getPrenom()}" /></td>
                    </tr>
                </c:forEach>
            </table>
            <button type="submit" class="btn btn-primary" name="action" value="animsInternes">Choisir</button>
        </form>
    </body>
</html>
