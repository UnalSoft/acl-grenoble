package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.dao.CentreDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import java.io.IOException;
import java.util.Arrays;
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
 * @author sparrow
 */
@WebServlet(name = "ControleurCentre", urlPatterns = {"/ControleurCentre"})
public class ControleurCentre extends HttpServlet {

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
      System.out.println("Get");
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
      System.out.println("Post");
      HttpSession session = request.getSession();
      String userName = (String) session.getAttribute("utilisateur");
      if (userName != null) {
         String action = request.getParameter("action");
         if (action != null) {
            request.setCharacterEncoding("UTF-8");
         } else {
            String utilisateur = (String) session.getAttribute("utilisateur");
            request = listThemes(request, response, utilisateur);
            request = listCompetences(request);
            request = listPeriodes(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/responsableCentre/ajouterActivite.jsp").forward(request, response);
         }
      }
   }

   private HttpServletRequest listCompetences(HttpServletRequest request) {
      List<Competence> competences = Arrays.asList(Competence.values());
      request.setAttribute("competences", competences);

      return request;
   }

   private HttpServletRequest listPeriodes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      List<Periode> periodes = null;
      try {
         periodes = new ResponsableDAO(dataSource).getPeriodesDisponibilite();
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      request.setAttribute("periodes", periodes);
      return request;
   }

   /**
    * Obtenir les thèmes d'un centre
    *
    * @param request
    * @param utilisateur
    * @return
    */
   private HttpServletRequest listThemes(HttpServletRequest request,HttpServletResponse response, String utilisateur) throws ServletException, IOException {
      List<Theme> themes = null;
      try {
         themes = new CentreDAO(dataSource).getThemesParUtilisateur(utilisateur);
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      request.setAttribute("themes", themes);
      return request;
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Servlet gerant les fonctions du responsable du centre";
   }// </editor-fold>

}
