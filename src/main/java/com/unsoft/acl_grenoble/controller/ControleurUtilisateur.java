package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.RFamilleDAO;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import com.unsoft.acl_grenoble.model.utilisateur.RoleEnum;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author juanmanuelmartinezromero
 */
@WebServlet(name = "ControleurUtilisateur", urlPatterns = {"/controleurUtilisateur"})
public class ControleurUtilisateur extends HttpServlet {

   private static final String ERROR_LOGIN = "Mot de passe incorrect ou compte inexistant";
   private static final String COMPTE_INACTIF = "Le compte n'est pas encore actif";
   @Resource(name = "jdbc/acl_grenoble")
   private DataSource dataSource;

   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      String name = request.getParameter("user");
      String mdp = request.getParameter("motDePass");

      CompteDAO compteDAO = new CompteDAO(dataSource);
      ResponsableDAO responsableDAO = new ResponsableDAO(dataSource);
      try {
         Compte compte = compteDAO.getCompte(name, mdp);
         verifierCompte(compte, responsableDAO, request, response);
      } catch (DAOException ex) {
         request.setAttribute("message", ex.getMessage());
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
      }
   }

   private void verifierCompte(Compte compte, ResponsableDAO DAOresponsable,
           HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
      if (compte != null && compte.isActif()) {
         Responsable responsable = DAOresponsable.getResponsable(compte);
         verifierUtilisateur(responsable, compte.getNomUtilisateur(), request, response);
      } else {
         String message;
         if (compte == null) {
            message = ERROR_LOGIN;
         } else {
            message = COMPTE_INACTIF;
         }
         request.setAttribute("message", message);
         getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
      }
   }

   private void verifierUtilisateur(Responsable responsable, String nomUtilisateur,
           HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
      if (responsable != null) {
         //TODO faire la difference entre type d'utilisateur
         RoleEnum role = responsable.getRole();
         switch (role) {
            case R_ASSOCIATION:
               //TODO Retourner vers la page de R. Asso (et verifier les parametres)
               getServletContext().getRequestDispatcher("/WEB-INF/responsableAssociation/accueilRespAsso.jsp").forward(request, response);
               break;
            case R_CENTRE:
               //TODO Retourner vers la page de R. Centre (et verifier les parametres)
               getServletContext().getRequestDispatcher("/WEB-INF/responsableCentre/accueilRespCentre.jsp").forward(request, response);
               break;
            case R_PLANIFICATION:
               //TODO Retourner vers la page de R. Plan (et verifier les parametres)
               getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/accueilRespPlan.jsp").forward(request, response);
               break;
            default:
               //TODO Erreur interne BD (Page Erreur BD) (et verifier les parametres)
               getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
               break;
         }
      } else {
         // Le responsable est d'une famille
         RFamilleDAO rFamilleDAO = new RFamilleDAO(dataSource);
         ResponsableFamille rFamille = rFamilleDAO.getResponsable(nomUtilisateur);
         //TODO Rediger vers la page de famille   
         getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/accueilRespFamille.jsp").forward(request, response);
      }
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Servlet Gerant Utilisateur Fonctions";
   }// </editor-fold>
}
