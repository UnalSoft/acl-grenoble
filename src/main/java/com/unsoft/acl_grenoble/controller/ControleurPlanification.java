/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Etat;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.dao.ActiviteDAO;
import com.unsoft.acl_grenoble.model.dao.AnimateurDAO;
import com.unsoft.acl_grenoble.model.dao.AsignationDAO;
import com.unsoft.acl_grenoble.model.dao.CentreDAO;
import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.EtatDAO;
import com.unsoft.acl_grenoble.model.dao.PeriodeDAO;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.util.GestionMail;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.MessagingException;
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
    private final String AFFECTER = "affecter";
    private final String ANIMSINTERNES = "animsInternes";
    private final String ANIMSEXTERNES = "animsExternes";
    private static final String ANNULER_ACTIVITE = "annulerActivite";
    private final int MIN_ENFANT_ANIMATEUR = 3;
    private final int MAX_ENFANT_ANIMATEUR = 4;
    private final String SUCCES = "Animateurs asignés à l'activivité avec succès!\nActivité Confirmée!";

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
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals(AFFECTER)) {                    
                    listerAnimateursDisponibles(request, response, true);
                }
            } else {
                listerActivitesPreconfirmes(request, response);
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
            if (action != null) {
                if (action.equals(ANIMSINTERNES) || action.equals(ANIMSEXTERNES)) {
                    request.setCharacterEncoding("UTF-8");
                    String[] anims = request.getParameterValues("animateurs");
                    int nbAnimateurs = 0;
                    if (anims != null) {
                        nbAnimateurs = anims.length;
                    }
                    int nbMinAnim = Integer.parseInt(request.getParameter("nbMinAnim"));
                    int nbMaxAnim = Integer.parseInt(request.getParameter("nbMaxAnim"));
                    int nbAnimsDisp = Integer.parseInt(request.getParameter("nbAnimsDisp"));

                    String[] animsChoisis = request.getParameterValues("animateursChoisis");
                    if (action.equals(ANIMSEXTERNES)) {
                        if (animsChoisis != null) {
                            nbAnimsDisp += animsChoisis.length;
                            nbAnimateurs += animsChoisis.length;
                        }
                    }

                    boolean besoinExtern = false;
                    boolean succes = false;
                    boolean impossible = false;
                    boolean depasse = false;
                    if (nbAnimateurs >= nbMinAnim && nbAnimateurs <= nbMaxAnim) {
                        succes = true;
                    } else {
                        if (action.equals(ANIMSINTERNES)) {
                            if (nbAnimateurs < nbMinAnim && nbAnimateurs < nbAnimsDisp) {
                                besoinExtern = false;
                            } else if (nbAnimateurs < nbMinAnim && nbAnimsDisp < nbMinAnim) {
                                besoinExtern = true;
                            }
                        } else {
                            besoinExtern = true;
                            if (nbAnimateurs < nbMinAnim && nbAnimsDisp < nbMinAnim) {
                                impossible = true;
                            }
                        }
                        if (nbAnimateurs > nbMaxAnim) {
                            depasse = true;
                        }
                    }

                    if (!succes) {
                        if (action.equals(ANIMSINTERNES)) {
                            if (besoinExtern && anims != null) {
                                request.setAttribute("animInternes", anims);
                            }
                        } else {
                            if (besoinExtern) {
                                request.setAttribute("animInternes", animsChoisis);
                                request.setAttribute("impossible", impossible);
                            }
                        }
                        request.setAttribute("depasse", depasse);
                        request.setAttribute("besoinExtern", besoinExtern);
                        listerAnimateursDisponibles(request, response, !besoinExtern);
                    } else {
                        int idActivite = Integer.parseInt(request.getParameter("idActivite"));
                        String nomPeriode = request.getParameter("periode");
                        try {
                            if (nbMinAnim > 0) {
                                if (action.equals(ANIMSINTERNES)) {
                                    affecterAnimateurs(anims, idActivite, nomPeriode, userName);
                                } else {
                                    affecterAnimateurs(anims, idActivite, nomPeriode, userName);
                                    affecterAnimateurs(animsChoisis, idActivite, nomPeriode, userName);
                                }
                            }
                            new ActiviteDAO(dataSource).changerEtat(idActivite, nomPeriode, EtatEnum.CONFIRMEE);
                            request.setAttribute("message", SUCCES);
                            listerActivitesPreconfirmes(request, response);
                        } catch (Exception ex) {
                            request.setAttribute("message", ex.getMessage());
                            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                        }
                    }
                } else if (action.equals(ANNULER_ACTIVITE)) {
                    int idActivite = Integer.parseInt(request.getParameter("idActivite"));
                    String nomPeriode = request.getParameter("periode");
                    try {
                        new ActiviteDAO(dataSource).changerEtat(idActivite, nomPeriode, EtatEnum.ANNULEE);
                        request.setAttribute("message", "Activité Annulée");
                        listerActivitesPreconfirmes(request, response);
                    } catch (DAOException ex) {
                        request.setAttribute("message", ex.getMessage());
                        getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                    }
                }
            } else {
                listerActivitesPreconfirmes(request, response);
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void listerAnimateursDisponibles(HttpServletRequest request, HttpServletResponse response, boolean estInterne) throws ServletException, NumberFormatException, IOException {
        String idActivite = request.getParameter("idActivite");
        String nomPeriode = request.getParameter("periode");
        try {
            final ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
            Activite activite = activiteDAO.getActivite(Integer.parseInt(idActivite));
            Periode periode = new PeriodeDAO(dataSource).getPeriode(nomPeriode);
            List<Animateur> animateursDisponibles = obtenirAnimateursDisponibles(periode, activite, estInterne);
            int nbInscris = activiteDAO.getnbInscris(activite.getIdActivite(), periode.nomPeriode());
            int nbMinAnim = (int) Math.ceil((double) nbInscris / MAX_ENFANT_ANIMATEUR);
            int nbMaxAnim = nbInscris / MIN_ENFANT_ANIMATEUR;
            request.setAttribute("nbMinAnim", nbMinAnim);
            request.setAttribute("nbMaxAnim", nbMaxAnim);
            request.setAttribute("nbInscris", nbInscris);
            request.setAttribute("activite", activite);
            request.setAttribute("periode", periode);
            request.setAttribute("animateurs", animateursDisponibles);
            getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/choisirAnimateur.jsp").forward(request, response);
        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    List<Animateur> obtenirAnimateursDisponibles(Periode periode, Activite activite, boolean estInterne) throws DAOException {
        AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
        //Animateurs disponibles dans le superperiode de l'activite
        List<Animateur> animateurs = animateurDAO.getAnimateursDisp(periode.getSuperPeriode(), estInterne);
        for (int i = 0; i < animateurs.size(); i++) {
            //Obtenir periodes qui on comme superperiode le superperiode de l'activite
            //dont l'animateur a été déjè asigné 
            Animateur animateur = animateurs.get(i);
            AsignationDAO asignationDAO = new AsignationDAO(dataSource);
            List<Periode> periodesAsignes = asignationDAO.getPeriodesAnimateurParSuperPeriode(animateur.getNomAnimateur(), animateur.getPrenomAnimateur(), periode.getSuperPeriode());
            for (Periode p : periodesAsignes) {
                //Verifier intersection dates
                if (!(periode.getDateFin().before(p.getDateDebut())) || (periode.getDateDebut().after(p.getDateFin()))) {
                    animateurs.remove(animateur);
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < animateurs.size(); i++) {
            Animateur animateur = animateurs.get(i);
            boolean valide = validerCompetences(animateur, activite);
            if (!valide) {
                animateurs.remove(animateur);
                i--;
            }
        }
        return animateurs;
    }

    private boolean validerCompetences(Animateur animateur, Activite activite) throws DAOException {
        AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
        ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
        List<Competence> competencesAnimateur = animateurDAO.getCompetences(animateur.getNomAnimateur(), animateur.getPrenomAnimateur());
        List<Competence> competencesActivite = activiteDAO.getCompetences(activite.getIdActivite());
        for (Competence compActiv : competencesActivite) {
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

    private void listerActivitesPreconfirmes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String utilisateur = (String) request.getSession().getAttribute("utilisateur");
        try {
            String centre = new CentreDAO(dataSource).getCentreParUtilisateur(utilisateur);
            List<Etat> etatsPreconfirmes = new EtatDAO(dataSource).getActivitesPreConfirmees(centre);
            request.setAttribute("etatsPreconfirmes", etatsPreconfirmes);
            getServletContext().getRequestDispatcher("/WEB-INF/responsablePlanification/affecterAnimateur.jsp").forward(request, response);
        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
        }
    }

    private void affecterAnimateurs(String[] anims, int idActivite, String nomPeriode, String responsable) throws DAOException, MessagingException {
        AsignationDAO asignationDAO = new AsignationDAO(dataSource);
        for (String anim : anims) {
            String[] nomPrenom = anim.split(":");
            String nom = nomPrenom[0];
            String prenom = nomPrenom[1];
            asignationDAO.assignerAnimateur(nom, prenom, nomPeriode, idActivite);
            envoyerMail(nom, prenom, idActivite, nomPeriode, responsable);
        }
    }

    private void envoyerMail(String nomAnimateur, String prenomAnimateur, int idActivite, 
            String nomPeriode, String responsable) throws DAOException, MessagingException {
        AnimateurDAO animateurDAO = new AnimateurDAO(dataSource);
        ActiviteDAO activiteDAO = new ActiviteDAO(dataSource);
        PeriodeDAO periodeDAO = new PeriodeDAO(dataSource);
        CompteDAO compteDAO = new CompteDAO(dataSource);
        Animateur animateur = animateurDAO.getAnimateur(nomAnimateur, prenomAnimateur);
        Activite activite = activiteDAO.getActivite(idActivite);
        Periode periode = periodeDAO.getPeriode(nomPeriode);
        Responsable respAsso = new ResponsableDAO(dataSource).getResponsable(compteDAO.getCompte(responsable));
        String message = "Bonjour " + nomAnimateur + " " + prenomAnimateur + ",\n\n"
                + "L’Association des Centres de Loisirs (ACL) de Grenoble vous informe "
                + "que vous avez été asigné comme animateur de l'activité : \n"
                + activite.getNomActivite() + " pour le periode " + periode.nomPeriode() + "."
                +"\n\nCordialement,\n"
                + respAsso.getNomFamille() + " " + respAsso.getPrenom() 
                + "\nResponsable de Panification \n"
                + "Association des Centres de Loisirs (ACL) Grenoble";
    
        
        new GestionMail().envoyerMail(animateur.getEmail(), "ACL Grenoble - Asignation Activité", message, null);
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
