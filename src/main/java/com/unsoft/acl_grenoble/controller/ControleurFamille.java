/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.Etat;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.dao.ActiviteDAO;
import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.EnfantDAO;
import com.unsoft.acl_grenoble.model.dao.EtatDAO;
import com.unsoft.acl_grenoble.model.dao.PeriodeDAO;
import com.unsoft.acl_grenoble.model.dao.RFamilleDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Enfant;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author LEONARDO
 */
@WebServlet(name = "ControleurFamille", urlPatterns = {"/ControleurFamille"})
public class ControleurFamille extends HttpServlet {

    @Resource(name = "jdbc/acl_grenoble")
    private DataSource ds;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");

        response.setContentType("text/html; charset=UTF-8");
        
        
        try {

            if (action.equals("demanderCompte")) {
                //request.setCharacterEncoding("UTF-8");
                getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/demanderCompte.jsp").forward(request, response);
            } else if (action.equals("inscrire")) {

                afficherActivites(request, response);

            } else if (action.equals("gestion")) {

                gestionEnfant(request, response);

            } else if (action.equals("periodes")) {

                selectionPeriodes(request, response);

            } else if (action.equals("verifInscrire")) {

                inscrireEnfant(request, response);

            } else if (action.equals("verifEffacer")) {

                desInscrireEnfant(request, response);
            }
        } catch (Exception ex) {
            //request.setCharacterEncoding("UTF-8");
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equals("demander1")) {
                    actionDemander1(request, response);
                } else if (action.equals("demander2")) {
                    RFamilleDAO rDao = new RFamilleDAO(ds);
                    EnfantDAO eDao = new EnfantDAO(ds);
                    CompteDAO cDao = new CompteDAO(ds);
                    actionDemander2(request, response, rDao, eDao, cDao);
                }
            } else {
                HttpSession session = request.getSession();
                String nomUtilisateur = (String) session.getAttribute("utilisateur");

                // Le responsable est d'une famille
                RFamilleDAO rFamilleDAO = new RFamilleDAO(ds);
                EnfantDAO enfantDAO = new EnfantDAO(ds);
                ResponsableFamille rFamille = rFamilleDAO.getResponsable(nomUtilisateur);

                rFamille.setEnfants(enfantDAO.getListeDEnfantsParRFamille(rFamille.getNomFamille(), rFamille.getPrenom()));

                //On doit faire que la liste soit visible chez web
                remplirListeEnfantsEtActivites(request, response, rFamille.getEnfants());

            }

        } catch (DAOException e) {
            //request.setCharacterEncoding("UTF-8");
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").
                    forward(request, response);
        }
    }

    private void actionDemander2(HttpServletRequest request,
            HttpServletResponse response, RFamilleDAO rDao, EnfantDAO eDao,
            CompteDAO cDao) throws DAOException, ServletException, IOException {

        //request.setCharacterEncoding("UTF-8");

        String prenomR = request.getParameter("prenomR");
        String nomR = request.getParameter("nomR");
        String emailR = request.getParameter("emailR");
        String nomF = request.getParameter("nomF");
        Double revenuF = Double.parseDouble(request.getParameter("revenuF"));
        int nombreEnfants = Integer.parseInt(request.getParameter("nomEnfants"));

        //Compte
        String utilisateur = prenomR + "." + nomR;
        String motPasse = nomR.charAt(0) + prenomR.charAt(prenomR.length() - 1)
                + nomR.charAt(nomR.length() - 1) + prenomR.charAt(0) + "";

        cDao.addCompte(utilisateur, motPasse, 0);

        //Utilisateur && Responsable
        rDao.addResponsableFamille(nomR, prenomR, utilisateur, emailR, revenuF);

        //Enfants
        String prenomi;
        String nomi;
        int agei;
        for (int i = 1; i <= nombreEnfants; i++) {
            prenomi = request.getParameter("PrenomE" + i);
            nomi = request.getParameter("NomE" + i);
            agei = Integer.parseInt(request.getParameter("AgeE" + i));
            eDao.addEnfant(prenomi, nomi, prenomR, nomR, agei);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/demand_success.jsp").include(request, response);

    }

    /**
     * @param Http request
     * @param Http response
     * @throws ServletException
     * @throws IOException treats the first demand from the view
     */
    private void actionDemander1(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
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

    private void remplirListeEnfantsEtActivites(HttpServletRequest request, HttpServletResponse response, List<Enfant> listEnfants) throws IOException, ServletException {
        try {
            //request.setCharacterEncoding("UTF-8");

            request.setAttribute("enfants", listEnfants);

            int max = 0;

            for (Enfant each : listEnfants) {

                max++;

                EnfantDAO enfantDao = new EnfantDAO(ds);
                List<InscriptionActivite> listeActivites = enfantDao.getListeDInscriptionsParEnfant(
                        each.getNomEnfant(), each.getPrenomEnfant());

                request.setAttribute("activites" + max, listeActivites);

            }

            request.setAttribute("max", max);
            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/inscrireEnfant.jsp").forward(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void afficherActivites(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        try {
            //request.setCharacterEncoding("UTF-8");
            ActiviteDAO activiteDAO = new ActiviteDAO(ds);
            List<Activite> listeActivites = activiteDAO.getAllActivites();
            EtatDAO etatDAO = new EtatDAO(ds);
            List<Etat> listeEtat = etatDAO.getActivitesParEtat((EtatEnum.OUVERTE).getName());
            EnfantDAO enfantDAO = new EnfantDAO(ds);
            List<InscriptionActivite> listeInscris = enfantDAO.getListeDInscriptionsParEnfant(request.getParameter("nom"), request.getParameter("prenom"));

            List<Activite> listePropre = activiteDAO.purifyListActivites(listeActivites, listeEtat, listeInscris);

            request.setAttribute("listePropre", listePropre);
            request.setAttribute("nom", request.getParameter("nom"));
            request.setAttribute("prenom", request.getParameter("prenom"));

            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/inscrireEnfant2.jsp").include(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void gestionEnfant(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException {
        try {

            //request.setCharacterEncoding("UTF-8");
            request.setAttribute("nom", request.getParameter("nom"));
            request.setAttribute("prenom", request.getParameter("prenom"));
            EnfantDAO enfantDAO = new EnfantDAO(ds);

            List<InscriptionActivite> listeActivites = enfantDAO.getListeDInscriptionsParEnfant(request.getParameter("nom"), request.getParameter("prenom"));

            request.setAttribute("listeActivites", listeActivites);

            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/gestionEnfant.jsp").include(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void inscrireEnfant(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException {
        try {
            //request.setCharacterEncoding("UTF-8");
            EnfantDAO enfantDAO = new EnfantDAO(ds);
            enfantDAO.inscrireEnfant(request.getParameter("prenom"), request.getParameter("nom"), Integer.parseInt(request.getParameter("activite")), request.getParameter("periode"));

            ActiviteDAO activiteDAO = new ActiviteDAO(ds);
            int courents = activiteDAO.getnbInscris(Integer.parseInt(request.getParameter("activite")), request.getParameter("periode"));
            int nbMaxAnim = activiteDAO.getActivite(Integer.parseInt(request.getParameter("activite"))).getNbMaxAnimateurs();

            if (courents == nbMaxAnim * 10) {
                activiteDAO.changerEtat(Integer.parseInt(request.getParameter("activite")), request.getParameter("periode"), EtatEnum.FERMEE);
            }

            request.setAttribute("message", "Success");
            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/gestionEnfant.jsp").include(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void desInscrireEnfant(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException {
        try {
            //request.setCharacterEncoding("UTF-8");


            EnfantDAO enfantDAO = new EnfantDAO(ds);
            enfantDAO.desInscrireEnfant(request.getParameter("prenom"), request.getParameter("nom"), Integer.parseInt(request.getParameter("activite")), request.getParameter("periode"));

            ActiviteDAO activiteDAO = new ActiviteDAO(ds);

            //Por ahora... necesito probar
            activiteDAO.changerEtat(Integer.parseInt(request.getParameter("activite")), request.getParameter("periode"), EtatEnum.OUVERTE);

            request.setAttribute("message", "Success");
            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/gestionEnfant.jsp").include(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }

    }

    private void selectionPeriodes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DAOException {
        try {
            //request.setCharacterEncoding("UTF-8");
            PeriodeDAO periodeDAO = new PeriodeDAO(ds);
            List<Periode> listePeriodes = periodeDAO.getAllPeriodesOuvertParActivite(Integer.parseInt(request.getParameter("idActivite")));

            request.setAttribute("nom", request.getParameter("nom"));
            request.setAttribute("prenom", request.getParameter("prenom"));
            request.setAttribute("listePeriodes", listePeriodes);
            request.setAttribute("activite", request.getParameter("activite"));
            request.setAttribute("idActivite", request.getParameter("idActivite"));

            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/inscrireEnfant3.jsp").include(request, response);
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }
}
