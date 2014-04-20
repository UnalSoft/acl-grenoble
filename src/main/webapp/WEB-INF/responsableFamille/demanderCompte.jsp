<%-- 
    Document   : demanderCompte
    Created on : 11 avr. 2014, 20:30:19
    Author     : juanmanuelmartinezromero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACL Grenoble - Demander Compte</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">ACL Grenoble</a>
                </div>
                <div class="collapse navbar-collapse">
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
            <form method="POST" action="ControleurFamille">
                <input type="hidden" name="action" value="demander1" />
                <div class="form-group">
                    <h2>Information du responsable du famille</h2>
                    Prenom: <input type="text" name="prenomR" value="" /><br/>         
                    Nom: <input type="text" name="nomR" value="" /><br/>
                    Email: <input type="text" name="emailR" value="" /><br/>
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
