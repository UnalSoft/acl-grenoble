package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Etat;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.dao.ActiviteDAO;
import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.EtatDAO;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.RoleEnum;
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
@WebServlet(name = "ControleurUtilisateur", urlPatterns = {"/ControleurUtilisateur"})
public class ControleurUtilisateur extends HttpServlet {

    private static final String ERROR_LOGIN = "Mot de passe incorrect ou compte inexistant";
    private static final String COMPTE_INACTIF = "Le compte n'est pas encore actif";
    private static final String LOGOUT = "logout";
    @Resource(name = "jdbc/acl_grenoble")
    private DataSource dataSource;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String name = request.getParameter("user");
        String mdp = request.getParameter("motDePass");

        CompteDAO compteDAO = new CompteDAO(dataSource);
        ResponsableDAO responsableDAO = new ResponsableDAO(dataSource);
        try {
            Compte compte = compteDAO.getCompte(name, mdp);
            verifierCompte(compte, responsableDAO, request, response);
            verifierActivitesFinies();
        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void verifierCompte(Compte compte, ResponsableDAO DAOresponsable,
            HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        if (compte != null && compte.isActif()) {
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", compte.getNomUtilisateur());
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
                    getServletContext().getRequestDispatcher("/WEB-INF/responsableAssociation/accueilRespAsso.jsp").forward(request, response);
                    break;
                case R_CENTRE:
                    getServletContext().getRequestDispatcher("/ControleurCentre").forward(request, response);
                    break;
                case R_PLANIFICATION:
                    getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/accueilRespPlan.jsp").forward(request, response);
                    break;
                default:
                    getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                    break;
            }
        } else {   
            getServletContext().getRequestDispatcher("/WEB-INF/responsableFamille/accueilFamille.jsp").forward(request, response);
        }
    }

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
        String action = request.getParameter("action");
        if (action != null && action.equals(LOGOUT)) {
            HttpSession session = request.getSession();
            session.removeAttribute("utilisateur");
            session.invalidate();
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    
    private void verifierActivitesFinies() throws DAOException {
        List<Etat> activitesPourFinir = new EtatDAO(dataSource).getActivitesPourFinir();
        final ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
        for (Etat etat : activitesPourFinir) {
            activiteDAO.changerEtat(etat.getActivite().getIdActivite(), etat.getPeriode().nomPeriode(), EtatEnum.FINIE);
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
