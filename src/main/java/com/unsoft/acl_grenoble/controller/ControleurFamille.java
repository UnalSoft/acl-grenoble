/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.EnfantDAO;
import com.unsoft.acl_grenoble.model.dao.RFamilleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author LEONARDO
 */
@WebServlet(name = "ControleurFamille", urlPatterns = {"/ControleurFamille"})
public class ControleurFamille extends HttpServlet {

    @Resource(name = "jdbc/acl_grenoble")
    private DataSource ds;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControleurFamille</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControleurFamille at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attribute = request.getParameter("action");
        if (attribute.equals("demanderCompte")) {
            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/demanderCompte.jsp").forward(request, response);
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
        //processRequest(request, response);     
        String action = request.getParameter("action");
        try {
            if (action.equals("demander1")) {
                actionDemander1(request, response);
            } else if (action.equals("demander2")) {
                RFamilleDAO rDao = new RFamilleDAO(ds);
                EnfantDAO eDao = new EnfantDAO(ds);
                CompteDAO cDao = new CompteDAO(ds);
                actionDemander2(request, response, rDao, eDao, cDao);
            } else if (action.equals("Inscrire un Enfant")) {

            }

        } catch (DAOException e) {
            getServletContext().getRequestDispatcher("WEB-INF/erreur/erreurBD.jsp").
                    forward(request, response);
        }
    }

    private void actionDemander2(HttpServletRequest request,
            HttpServletResponse response, RFamilleDAO rDao, EnfantDAO eDao,
            CompteDAO cDao) throws DAOException {

        
        String prenomR = request.getParameter("prenomR");
        String nomR = request.getParameter("nomR");
        String emailR = request.getParameter("emailR");
        String nomF = request.getParameter("nomF");
        Double revenuF = Double.parseDouble(request.getParameter("revenuF"));
        int nombreEnfants = Integer.parseInt(request.getParameter("nombreE"));
        String prenomi;
        int agei;
        for (int i = 1; i <= nombreEnfants; i++) {
            prenomi = request.getParameter("PrenomE" + i);
            agei = Integer.parseInt(request.getParameter("AgeE" + i));
            eDao.addEnfant(prenomi, nomF, prenomR, nomR, agei);
        }
        String utilisateur = prenomR + "." + nomR;
        String motPasse = nomR.charAt(0) + prenomR.charAt(prenomR.length() - 1)
                + nomR.charAt(nomR.length() - 1) + prenomR.charAt(0) + "";
        rDao.addResponsable(nomR, prenomR, utilisateur, emailR, revenuF);
        cDao.addCompte(utilisateur, motPasse, 0);
    }

    /**
     * @param Http request
     * @param Http response
     * @throws ServletException
     * @throws IOException treats the first demand from the view
     */
    private void actionDemander1(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/demanderCompte2.jsp").include(request, response);
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
