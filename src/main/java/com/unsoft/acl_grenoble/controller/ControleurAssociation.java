package com.unsoft.acl_grenoble.controller;

import com.unsoft.acl_grenoble.model.dao.CompteDAO;
import com.unsoft.acl_grenoble.model.dao.DAOException;
import com.unsoft.acl_grenoble.model.dao.EnfantDAO;
import com.unsoft.acl_grenoble.model.dao.RFamilleDAO;
import com.unsoft.acl_grenoble.model.dao.ResponsableDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "ControleurAssociation", urlPatterns = {"/ControleurAssociation"})
public class ControleurAssociation extends HttpServlet {

    @Resource(name = "jdbc/acl_grenoble")
    private DataSource dataSource;
    private static final String ACCEPTER = "accepter";
    private static final String REFUSER = "refuser";

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
                if (action.equals(ACCEPTER)) {
                    CompteDAO compteDAO = new CompteDAO(dataSource);
                    String resp = request.getParameter("resp");
                    String message = request.getParameter("message");
                    try {
                        compteDAO.activerCompte(resp);
                        //envoyerEMail(true, message, resp);
                    } catch (Exception ex) {
                        request.setAttribute("message", ex.getMessage());
                        getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                    }

                } else if (action.equals(REFUSER)) {
                    CompteDAO compteDAO = new CompteDAO(dataSource);
                    String resp = request.getParameter("resp");
                    String message = request.getParameter("message");
                    try {
                        RFamilleDAO rFamilleDAO = new RFamilleDAO(dataSource);
                        ResponsableFamille responsable = rFamilleDAO.getResponsable(resp);
                        new EnfantDAO(dataSource).effacerEnfant(responsable.getNomFamille(), responsable.getPrenom());
                        rFamilleDAO.effacerResponsable(resp);
                        compteDAO.effacerCompte(resp);
                        //envoyerEMail(true, message, resp);
                    } catch (DAOException ex) {
                        request.setAttribute("message", ex.getMessage());
                        getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                    }
                }
            }
            remplirListe(request, response);

        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void remplirListe(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<Compte> comptesInactif = new CompteDAO(dataSource).getComptesInactifs();
            List<ResponsableFamille> familles = new ArrayList<ResponsableFamille>();
            RFamilleDAO rFamilleDAO = new RFamilleDAO(dataSource);
            for (Compte c : comptesInactif) {
                ResponsableFamille responsable = rFamilleDAO.getResponsable(c.getNomUtilisateur());
                if (responsable != null) {
                    responsable.setCompte(c);
                    familles.add(responsable);
                }
            }
            request.setAttribute("familles", familles);
            getServletContext().getRequestDispatcher("/WEB-INF/responsableAssociation/administrerCompte.jsp").forward(request, response);
        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("utilisateur");
        if (action != null) {
            if (action.equals(ACCEPTER)) {
                try {
                    String resp = request.getParameter("resp");
                    CompteDAO compteDAO = new CompteDAO(dataSource);
                    Compte compte = compteDAO.getCompte(resp);
                    Responsable respAsso = new ResponsableDAO(dataSource).getResponsable(compteDAO.getCompte(userName));
                    ResponsableFamille responsable = new RFamilleDAO(dataSource).getResponsable(resp);
                    if (responsable != null) {
                        responsable.setCompte(compte);
                        request.setAttribute("rFamille", responsable);
                        request.setAttribute("demande", true);
                        request.setAttribute("message", "Bonjour " + responsable.getNomFamille() + responsable.getPrenom() + ",\n\n"
                                + "Votre demande de création de compte dans L’Association des Centres de Loisirs (ACL) de Grenoble a été ACEPTÉE.\n"
                                + "Vous pouvez des maintenant y acceder avec les suivats données:\n\n"
                                + "Nom d'utilisateur: " + responsable.getCompte().getNomUtilisateur() + "\n"
                                + "Mot de Pass: " + responsable.getCompte().getMotdePass() + "\n\n"
                                + "Merci pour votre confiance.\n\n"
                                + "Cordialement,\n"
                                + respAsso.getNomFamille() + respAsso.getPrenom() + "\nResponsable Association des Centres de Loisirs (ACL) Grenoble");
                    } else {
                        throw new DAOException("Responsable non trouvé.");
                    }
                } catch (DAOException ex) {
                    request.setAttribute("message", ex.getMessage());
                    getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                }
            } else if (action.equals(REFUSER)) {
                try {
                    String resp = request.getParameter("resp");
                    CompteDAO compteDAO = new CompteDAO(dataSource);
                    Compte compte = compteDAO.getCompte(resp);
                    ResponsableFamille responsable = new RFamilleDAO(dataSource).getResponsable(resp);
                    Responsable respAsso = new ResponsableDAO(dataSource).getResponsable(compteDAO.getCompte(userName));
                    if (responsable != null) {
                        responsable.setCompte(compte);
                        request.setAttribute("rFamille", responsable);
                        request.setAttribute("demande", false);
                        request.setAttribute("message", "Bonjour " + responsable.getNomFamille() + responsable.getPrenom() + ",\n\n"
                                + "Votre demande de création de compte dans L’Association des Centres de Loisirs (ACL) de Grenoble a été REFUSÉE.\n"
                                + "\n\n"
                                + "Plus d'information: "+ respAsso.getMail() + "\n\n"
                                + "Cordialement,\n"
                                + respAsso.getNomFamille() + respAsso.getPrenom() + "\nResponsable Association des Centres de Loisirs (ACL) Grenoble");
                    } else {
                        throw new DAOException("Responsable non trouvé.");
                    }
                } catch (DAOException ex) {
                    request.setAttribute("message", ex.getMessage());
                    getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp").forward(request, response);
                }
            }
        }
        remplirListe(request, response);
    }

    /**
     * Sert a envoyer un message à l'Utilisateur
     *
     * @param reponsePositive Evalué à vrai si la reponse est positive.
     * @param content Mot de passe si est un reponse positive, Justification au
     * cas contraire
     * @param email email de l'utilisateur
     * @return
     */
    private boolean envoyerEMail(boolean reponsePositive, String content, String email) throws MessagingException {
        try {
            // Conection Properties
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", email);
            props.setProperty("mail.smtp.auth", "true");

            // Prepare session
            Session session = Session.getDefaultInstance(props);

            // Build Message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("centre.loisirs.grenoble@gmail.com"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(email));
            if (reponsePositive) {
                message.setSubject("ACL Grenoble - Demande Acceptée");
            } else {
                message.setSubject("ACL Grenoble - Demande Refusée");
            }
            message.setText(content);

            // Send Message
            Transport t = session.getTransport("smtp");
            t.connect("centre.loisirs.grenoble@gmail.com", "webprojet");
            t.sendMessage(message, message.getAllRecipients());

            // Close
            t.close();
        } catch (MessagingException ex) {
            throw ex;
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
        return "Short description";
    }

}
