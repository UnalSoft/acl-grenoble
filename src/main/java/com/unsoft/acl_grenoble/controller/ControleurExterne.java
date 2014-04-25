/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.dao.AnimateurDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.PeriodeDAO;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
@WebServlet(name = "ControleurExterne", urlPatterns = {"/ControleurExterne"})
public class ControleurExterne extends HttpServlet {

   @Resource(name = "jdbc/acl_grenoble")
   private DataSource dataSource;
   private final static int LONG_NOM = 20;
   private final static int LONG_MAIL = 60;
   private final static boolean ANIMATEUR_INTERNE = false;

   /**
    * Insertion d'un animateur
    *
    * @param animateur
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException
    */
   private void insererAnimateur(Animateur animateur, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
          AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
          animateurDAO.addAnimateur(animateur.getNomAnimateur(), animateur.getPrenomAnimateur(), animateur.getEmail(), animateur.estInterne());
          animateurDAO.lierCompetences(animateur.getNomAnimateur(), animateur.getPrenomAnimateur(), animateur.getCompetences());
          animateurDAO.lierPeriodes(animateur.getNomAnimateur(), animateur.getPrenomAnimateur(), animateur.getPeriodes());
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
   }

   /**
    * Obtient une liste de periodes a partir d'une liste de noms de periodes
    *
    * @param periodes
    * @return
    */
   private List<Periode> listPeriodes(String[] periodes, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<Periode> thePeriodes = new ArrayList<Periode>(periodes.length);
        PeriodeDAO periodeDAO = new PeriodeDAO(dataSource);
        for (String periode : periodes) {
            try {
                thePeriodes.add(periodeDAO.getPeriode(periode));
            } catch (DAOException ex) {
                getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                        .forward(request, response);
            }
        }
        return thePeriodes;
   }
   /**
    * Obtient une liste de competences pour les montrer dans la page
    * 
    * @param request
    * @param response
    * @return
    * @throws IOException
    * @throws ServletException 
    */
   private HttpServletRequest listPeriodes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      List<Periode> periodes = null;
      try {
         periodes = new PeriodeDAO(dataSource).getPeriodesDisponibilite();
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      request.setAttribute("periodes", periodes);
      return request;
   }

   /**
    * Obtient une liste de competences a partir d'une liste de noms de
    * competences
    *
    * @param competences
    * @return
    */
   private List<Competence> listCompetences(String[] competences) {
      List<Competence> theCompetences = new ArrayList<Competence>(competences.length);
      for (String competence : competences) {
         theCompetences.add(Competence.valueOf(competence));
      }
      return theCompetences;
   }

   /**
    * Obtient une liste de competences pour les montrer dans la page
    *
    * @param request
    * @return
    */
   private HttpServletRequest listCompetences(HttpServletRequest request) {
      List<Competence> competences = Arrays.asList(Competence.values());
      request.setAttribute("competences", competences);
      return request;
   }

   /**
    * Valide si mettre un animateur a la bd est possible
    *
    * @param prenom
    * @param nom
    * @param email
    * @param competences
    * @param periodes
    * @param request
    * @param response
    * @return
    * @throws ServletException
    * @throws IOException
    */
   private boolean validerChamps(String prenom, String nom, String email, String[] competences, String[] periodes, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      boolean prenomValide = prenom.length() > 0 && prenom.length() < LONG_NOM;
      boolean nomValide = nom.length() > 0 && nom.length() < LONG_NOM;
      boolean emailValide = email.length() > 0 && email.length() < LONG_MAIL;
      boolean aCompetences = competences != null;
      boolean aDisponibilite = periodes != null;
      boolean animateurExist = true;
      try {
         animateurExist = new ResponsableDAO(dataSource).animateurExist(nom, prenom);
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      return prenomValide && nomValide && emailValide
              && aCompetences && aDisponibilite && !animateurExist;
   }

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
      request.setCharacterEncoding("UTF-8");
      request=listCompetences(request);
      request=listPeriodes(request, response);
      getServletContext().getRequestDispatcher("/WEB-INF/animateurExterne/sInscrire.jsp").
              forward(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      String prenom = request.getParameter("prenom");
      String nom = request.getParameter("nom");
      String email = request.getParameter("email");
      String[] competences = request.getParameterValues("competences");
      String[] periodes = request.getParameterValues("periodes");

      boolean valide = validerChamps(prenom, nom, email, competences, periodes, request, response);
      if (valide) {
         Animateur animateur = new Animateur(nom, prenom, email, ANIMATEUR_INTERNE, listCompetences(competences), listPeriodes(periodes, request, response));
         insererAnimateur(animateur, request, response);
         request.setAttribute("creationReussi", true);
      } else {
         request.setAttribute("creationReussi", false);
      }
      request = listCompetences(request);
      request = listPeriodes(request, response);
      getServletContext().getRequestDispatcher("/WEB-INF/animateurExterne/sInscrire.jsp").forward(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Short description";
   }// </editor-fold>

}
