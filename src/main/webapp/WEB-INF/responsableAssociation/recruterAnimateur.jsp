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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Recruter animateur permanent</h1>
        <form method="POST" action="ControleurAssociation">
            <ul>
                <li> Prenom : <input type="text" name="prenom" required/></li>
                <li> Nom : <input type="text" name="nom" required/></li>
                <li> e-Mail : <input type="text" name="email" required</li>
                <li><h2>Competences</h2></li>
                <div>
                    <c:forEach items="${competences}" var="competence">
                        <input type="checkbox" name="competences" value="${competence.toString()}" />${competence.getName()}<br>    
                    </c:forEach>
                </div>  

                <li><h2>Periodes de disponiblité</h2></li>
                <div>
                    <c:forEach items="${periodes}" var="periode">
                        <input type="checkbox" name="periodes" value="${periode.nomPeriode()}" />${periode.nomPeriode()}<br>    
                    </c:forEach>
                </div>
            </ul>
            <input type="submit" name="action" value="Recruter"/>
            <input type="reset" value="Reinitialiser" name="reinitialiser" />
        </form>
    </body>
</html>
