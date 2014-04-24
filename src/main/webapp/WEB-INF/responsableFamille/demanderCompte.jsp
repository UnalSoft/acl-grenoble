<%-- 
    Document   : demanderCompte
    Created on : 11 avr. 2014, 20:30:19
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACL Grenoble - Demander Compte</title>
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
                    <a class="navbar-brand" href="index.jsp">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                    <ul class="nav navbar-nav">
                        <li><a href="ControleurFamille?action=demanderCompte">Demander un compte</a></li>
                        <li><a href="ControleurExterne?action=sInscrire.jsp">S’inscrire comme animateur externe</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="page-header">
            <h1 class="text-center">Demander Compte</h1>
        </div>
        <div class="container">
            <form method="POST" class="form-horizontal" action="ControleurFamille">
                <input type="hidden" name="action" value="demander1" />
                <h2>Information du responsable du famille</h2>
                <div class="form-group">
                    <label for="prenomR" class="col-sm-1 control-label">Prenom:</label>
                    <div class="col-sm-4">
                        <input type="text" name="prenomR" class="form-control" value="" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="nomR" class="col-sm-1 control-label">Nom:</label>
                    <div class="col-sm-4">
                        <input type="text" name="nomR" class="form-control" value="" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="emailR" class="col-sm-1 control-label">Email:</label>
                    <div class="col-sm-4">
                        <input type="text" name="emailR" class="form-control"value="" />
                    </div>
                </div>
                <div class="form-group">
                    <h2>Information de la famille</h2>
                    Revenu Annuel (EUR €): <input type="text" name="revenuF" value="" /><br/> 

                </div>
                <div class="form-group">
                    <h2>Information des enfants</h2>
                    Nombre d'enfants: <input type="text" name="nombreE" value="" /><br/>
                </div>
                <input type="submit" value="Submit" class="btn btn-primary btn-lg btn-block" />
            </form>
        </div>
    </body>
</html>
