/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.dao.DAOCompte;
import com.unsoft.acl_grenoble.model.dao.DAOResponsable;
import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.RoleEnum;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class ControlUtilisateur extends HttpServlet {

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
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      String name = request.getParameter("user");
      String mdp = request.getParameter("motDePass");

      DAOCompte DAOcompte = new DAOCompte(dataSource);
      DAOResponsable DAOresponsable = new DAOResponsable(dataSource);
      Compte compte = DAOcompte.getCompte(name, mdp);
      verifierCompte(compte, DAOresponsable);      
   }

   private void verifierCompte(Compte compte, DAOResponsable DAOresponsable) {
      if (compte != null && compte.isActif()) {
         //TODO Verifier le type d'utilisateur
         Responsable responsable = DAOresponsable.getResponsable(compte);
         verifierUtilisateur(responsable);
      } else {
         //TODO Page d'erreur (La compte n'existe pas ou n'est pas actif)
      }
   }

   private void verifierUtilisateur(Responsable responsable) {
      if (responsable != null) {
         //TODO faire la difference entre type d'utilisateur
         RoleEnum role = responsable.getRole();
         switch (role) {
            case R_ASSOCIATION:
               //TODO Retourner vers la page de R. Asso
               break;
            case R_CENTRE:
               //TODO Retourner vers la page de R. Centre
               break;
            case R_PLANIFICATION:
               //TODO Retourner vers la page de R. Plan
               break;
            default:
               //TODO Erreur interne BD (Page Erreur BD)
               break;
         }
      } else {
         // Le responsable est d'une famille
         //TODO Rediger vers la page de famille
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
