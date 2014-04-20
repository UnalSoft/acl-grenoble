/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.dao.ActiviteDAO;
import com.unsoft.acl_grenoble.model.dao.AnimateurDAO;
import com.unsoft.acl_grenoble.model.dao.AsignationDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author juanmanuelmartinezromero
 */
@WebServlet(name = "ControleurPlanification", urlPatterns = {"/ControleurPlanification"})
public class ControleurPlanification extends HttpServlet {
    
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
      HttpSession session = request.getSession();
      String userName = (String) session.getAttribute("utilisateur");
      if (userName != null) {
         String action = request.getParameter("action");
         if (action!=null) {
            
         } else {
            getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/affecterAnimateur.jsp").forward(request, response);
         }
      } else {
         response.sendRedirect("index.jsp");
      }
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
      HttpSession session = request.getSession();
      String userName = (String) session.getAttribute("utilisateur");
      if (userName != null) {
         String action = request.getParameter("action");
         if (action!=null) {
            
         } else {
            getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/affecterAnimateur.jsp").forward(request, response);
         }
      } else {
         response.sendRedirect("index.jsp");
      }
   }
   
   List<Animateur> obtenirAnimateursDisponibles(Periode periode, Activite activite, boolean estInterne) throws DAOException {
       AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
       //Animateurs disponibles dans le superperiode de l'activite
       List<Animateur> animateurs = animateurDAO.getAnimateursDisp(periode.getSuperPeriode(), estInterne);
       for (Animateur animateur : animateurs) {
           //Obtenir periodes qui on comme superperiode le superperiode de l'activite
           //dont l'animateur a été déjè asigné 
           AsignationDAO asignationDAO = new AsignationDAO(dataSource);
           List<Periode> periodesAsignes = asignationDAO.getPeriodesAnimateurParSuperPeriode(animateur.getNomAnimateur(), animateur.getPrenomAnimateur(), periode.getSuperPeriode());
           for (Periode p : periodesAsignes) {
               //Verifier intersection dates
               if (!(periode.getDatefin().before(p.getDateDebut())) || (periode.getDateDebut().after(p.getDatefin()))) {
                   animateurs.remove(animateur);
                   break;
               }
           }
       }
       for (Animateur animateur : animateurs) {
           boolean valide = validerCompetences(animateur, activite);
           if (!valide) {
               animateurs.remove(animateur);
           }
       }
       return animateurs;
   }
   
   private boolean validerCompetences(Animateur animateur, Activite activite) throws DAOException {
       AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
       ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
        List<Competence> competencesAnimateur = animateurDAO.getCompetences(animateur.getNomAnimateur(), animateur.getPrenomAnimateur());
        List<Competence> competencesActivite = activiteDAO.getCompetences(activite.getIdActivite());
        for (Competence compActiv : competencesActivite){
            boolean present = false;
            for (Competence compAnim : competencesAnimateur) {
                if (compActiv.equals(compAnim)) {
                    present = true;
                    break;
                }
            }
            if (present == false) {
                return false;
            }
        }
       return true;
    }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Servlet gerant les functions du responsable de planification";
   }// </editor-fold>

}
