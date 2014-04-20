/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juanmanuelmartinezromero
 */
@WebServlet(name = "ControleurPlanification", urlPatterns = {"/ControleurPlanification"})
public class ControleurPlanification extends HttpServlet {

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
