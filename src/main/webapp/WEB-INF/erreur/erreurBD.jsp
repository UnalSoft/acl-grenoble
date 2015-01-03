<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur Base de Données</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="./index.jsp">ACL Grenoble</a>
            </div>
        </div>
    </nav>

    <h1 class="text-center">Capitaine le navire coule!</h1>
    <div class="alert alert-danger">ACL Grenoble connaît actuellement quelques difficultés
        avec sa base de données. S'il vous plaît revenez plus tard.
        <p>${message}</p>
    </div>
</body>
</html>
