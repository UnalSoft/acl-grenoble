package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import com.unsoft.acl_grenoble.model.dao.ActiviteDAO;
import com.unsoft.acl_grenoble.model.dao.CentreDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private final static int LONG_NOM = 20;
    private final static int LONG_NOM_PERIODE = 60;
    private final static int LONG_DESCRIPTIF = 200;
    private static final String RECHARGER = "recharger";

    @Resource(name = "jdbc/acl_grenoble")
    private DataSource dataSource;

    private boolean dateCorrect(String dateDebut) {
        boolean correct;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(dateDebut);
            correct = true;
        } catch (ParseException e) {
            correct = false;
        }
        return correct;
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
    private HttpServletRequest listThemes(HttpServletRequest request, HttpServletResponse response, String utilisateur) throws ServletException, IOException {
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

    private Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date theDate = null;
        try {
            theDate = formatter.parse(date);
        } catch (ParseException e) {
            //Valide avant
        }
        return theDate;
    }

    /**
     * Valide que tous les champs soient corrects
     *
     * @param nom
     * @param theme
     * @param descriptif
     * @param periode
     * @param competences
     * @param dateDebut
     * @param dateFin
     * @param request
     * @param response
     * @return
     */
    private boolean validerChamps(String nom, String descriptif, String periode, String nombreAnimateurs, String prix, String[] competences, String dateDebut, String dateFin, HttpServletRequest request, HttpServletResponse response) {
        boolean nomValide = nom != null && nom.length() > 0 && nom.length() < LONG_NOM;
        boolean descriptifValid = descriptif != null && descriptif.length() >= 0 && descriptif.length() < LONG_DESCRIPTIF;
        boolean nAnimateursValide = nombreAnimateurs != null && validerParseInt(nombreAnimateurs);
        boolean prixValide = prix != null && validerParseDouble(prix);
        boolean aPeriodes = periode != null;
        boolean dDebutValide = dateDebut != null && dateCorrect(dateDebut);
        boolean dFinValide = dateFin != null && dateCorrect(dateFin);
        boolean aCompetences = competences != null;

        return nomValide && descriptifValid && aPeriodes && nAnimateursValide
                && prixValide && aCompetences && dDebutValide && dFinValide;
    }

    private boolean validerParseDouble(String number) {
        boolean nAnimateursValide;
        try {
            Double.parseDouble(number);
            nAnimateursValide = true;
        } catch (NullPointerException e) {
            nAnimateursValide = false;
        } catch (NumberFormatException e) {
            nAnimateursValide = false;
        }
        return nAnimateursValide;
    }

    private boolean validerParseInt(String number) {
        boolean nAnimateursValide;
        try {
            Integer.parseInt(number);
            nAnimateursValide = true;
        } catch (NullPointerException e) {
            nAnimateursValide = false;
        } catch (NumberFormatException e) {
            nAnimateursValide = false;
        }
        return nAnimateursValide;
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
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("utilisateur");
        if (userName != null) {
            request = listThemes(request, response, userName);
            request = listCompetences(request);
            request = listPeriodes(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/responsableCentre/ajouterActivite.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("utilisateur");
        if (userName != null) {
            String action = request.getParameter("action");
            if (action != null) {
                String nom = request.getParameter("nom");
                String theme = request.getParameterValues("theme")[0];
                String descriptif = request.getParameter("descriptif");
                String periode = request.getParameterValues("periode")[0];
                String dDebut = request.getParameter("dateDebut");
                String dFin = request.getParameter("dateFin");
                String nombreAnimateurs = request.getParameter("nombreAnimateurs");
                String prix = request.getParameter("prix");
                String[] competences = request.getParameterValues("competences");
                boolean valide = validerChamps(nom, descriptif, periode,
                        nombreAnimateurs, prix, competences, dDebut, dFin, request, response);

                // Obtenir periode pere
                Periode periodePere = new Periode(null, new Date(), new Date());
                try {
                    periodePere = new ResponsableDAO(dataSource).getPeriode(periode);
                } catch (DAOException ex) {
                    request.setAttribute("message", ex.getMessage());
                    getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                }

                Date dateDebut = parseDate(dDebut);
                Date dateFin = parseDate(dFin);
                String nomP = periode + " " + dDebut + " " + dFin;
                boolean dateReelle = dateDebut.after(new Date()) && dateDebut.before(dateFin);
                boolean periodeValide = dateDebut.after(periodePere.getDateDebut()) && dateFin.before(periodePere.getDateFin());
                boolean nomPeriodeValide = nomP.length() < LONG_NOM_PERIODE;
                if (valide && dateReelle && periodeValide
                        && Integer.parseInt(nombreAnimateurs) > 0
                        && Float.parseFloat(prix) >= 0 && nomPeriodeValide) {
                    try {
                        // Creer l'activite
                        ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
                        CentreDeLoisirs centre = new CentreDeLoisirs(new CentreDAO(dataSource).getCentreParUtilisateur(userName));
                        Theme leTheme = new Theme(ThemeEnum.getTheme(theme), centre);
                        Activite activite = new Activite(-1, leTheme, nom, descriptif, Integer.parseInt(nombreAnimateurs), Float.parseFloat(prix));
                        int idActivite = activiteDAO.createActivite(activite);

                        // Lier ses competences
                        List<Competence> lesCompetences = listCompetences(competences);
                        activiteDAO.lierCompetences(idActivite, lesCompetences);

                        // Creer un periode et lier l'etat
                        Periode lePeriode = new Periode(nomP, dateDebut, dateFin, periodePere.nomPeriode());
                        activiteDAO.creerPeriode(lePeriode);
                        activiteDAO.lierEtat(idActivite, periode, EtatEnum.OUVERTE.getName());
                        request.setAttribute("creationReussi", true);
                    } catch (DAOException ex) {
                        request.setAttribute("message", ex.getMessage());
                        getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("creationReussi", false);
                }
                request.setAttribute("action", RECHARGER);
                request = listThemes(request, response, userName);
                request = listCompetences(request);
                request = listPeriodes(request, response);
                getServletContext().getRequestDispatcher("/WEB-INF/responsableCentre/ajouterActivite.jsp").forward(request, response);
            } else {
                request = listThemes(request, response, userName);
                request = listCompetences(request);
                request = listPeriodes(request, response);
                getServletContext().getRequestDispatcher("/WEB-INF/responsableCentre/ajouterActivite.jsp").forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
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
