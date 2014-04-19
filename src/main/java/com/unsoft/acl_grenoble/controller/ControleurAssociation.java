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
      listCompetences(request);
      listPeriodes(request, response);
      getServletContext().getRequestDispatcher("/WEB-INF/responsableAssociation/recruterAnimateur.jsp").forward(request, response);
   }

   private void listPeriodes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      List<Periode> periodes = null;
      try {
         periodes = new ResponsableDAO(ds).getPeriodesDisponibilite();
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      request.setAttribute("periodes", periodes);
   }

   private void listCompetences(HttpServletRequest request) {
      List<Competence> competences = Arrays.asList(Competence.values());
      request.setAttribute("competences", competences);
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
         //TODO Mettre l'animateur dans la bd
         Animateur animateur = new Animateur(nom, prenom, email, valide, listCompetences(competences), listPeriodes(periodes, request, response));
         insererAnimateur(animateur, request, response);
      } else {
         //TODO Show failed message in a modal way
      }

   }
   /**
    * Insertion d'un animateur
    * @param animateur
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException 
    */
   private void insererAnimateur(Animateur animateur, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         new ResponsableDAO(ds).insererAnimateur(animateur);
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      //TODO Informer de la creation
   }

   /**
    * Obtient une liste de periodes a partir d'une liste de noms de periodes
    *
    * @param periodes
    * @return
    */
   private List<Periode> listPeriodes(String[] periodes, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<Periode> thePeriodes = new ArrayList<Periode>(periodes.length);
      ResponsableDAO responsableDAO = new ResponsableDAO(ds);
      for (String periode : periodes) {
         try {
            thePeriodes.add(responsableDAO.getPeriode(periode));
         } catch (DAOException ex) {
            getServletContext().getRequestDispatcher("/WEB-INF/erreur/erreurBD.jsp")
                    .forward(request, response);
         }
      }
      return thePeriodes;
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
    * @throws IOException
    * @throws ServletException
    */
   private boolean validerChamps(String prenom, String nom, String email, String[] competences, String[] periodes, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      boolean prenomValide = prenom.length() > 0 && prenom.length() < LONG_NOM;
      boolean nomValide = nom.length() > 0 && nom.length() < LONG_NOM;
      boolean emailValide = email.length() > 0 && email.length() < LONG_MAIL;
      boolean aCompetences = competences != null;
      boolean aDisponibilite = periodes != null;
      boolean animateurExist = true;
      try {
         animateurExist = new ResponsableDAO(ds).animateurExist(nom, prenom);
      } catch (DAOException ex) {
         getServletContext().getRequestDispatcher("WEB-INF/erreur/erreurBD.jsp")
                 .forward(request, response);
      }
      return prenomValide && nomValide && emailValide
              && aCompetences && aDisponibilite && !animateurExist;
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Controleur de l'association";
   }// </editor-fold>

}
