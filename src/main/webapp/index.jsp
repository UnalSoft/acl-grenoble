<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACL Grenoble</title>
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
                  <a class="navbar-brand" href="#">ACL Grenoble</a>
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
            <h1 class="text-center">ACL Grenoble</h1>
        </div>
        <div class="container">
            <form method="POST" action="ControleurUtilisateur" class="form-signin" role="form">
                <h2>Connexion</h2>
                <c:if test="${message != null}">
                    <div class="alert alert-danger">${message}</div>
                </c:if>
                <div class="form-group">
                    <label for="user">Nom d'utilisateur :</label> 
                    <input type="text" name="user" id="user" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="motDePass">Mot de passe : </label> 
                    <input type="password" name="motDePass" id="motDePass" class="form-control" required/>
                </div>
                <input type="submit" name="Connexion" value="Connexion" class="btn btn-primary btn-lg btn-block"/>
            </form>
        </div>
    </body>
</html>
