package com.unsoft.acl_grenoble.util;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import com.unsoft.acl_grenoble.model.dao.InscriptionDAO;
import com.unsoft.acl_grenoble.model.utilisateur.Enfant;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.sql.DataSource;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Edward
 */
public class GestionFactures {

    public void genererFactures(String nomSuperPeriode, DataSource dataSource) throws Exception {
        InscriptionDAO inscriptionDAO = new InscriptionDAO(dataSource);
        List<InscriptionActivite> inscriptions = inscriptionDAO.getInscriptionsParPeriode(nomSuperPeriode);
        List<Enfant> enfantsInscris = new ArrayList<Enfant>();
        for (InscriptionActivite inscription : inscriptions) {
            if (!enfantsInscris.contains(inscription.getEnfant())) {
                inscription.getEnfant().getInscriptions().add(inscription);
                enfantsInscris.add(inscription.getEnfant());
            } else {
                int index = enfantsInscris.indexOf(inscription.getEnfant());
                enfantsInscris.get(index).getInscriptions().add(inscription);
            }
        }
        List<ResponsableFamille> responsables = new ArrayList<ResponsableFamille>();
        for (Enfant enfant : enfantsInscris) {
            if (!responsables.contains(enfant.getResponsable())) {
                enfant.getResponsable().getEnfants().add(enfant);
                responsables.add(enfant.getResponsable());
            } else {
                int index = responsables.indexOf(enfant.getResponsable());
                responsables.get(index).getEnfants().add(enfant);
            }
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        File folderFact = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            folderFact = fileChooser.getSelectedFile().getAbsoluteFile();
        }
        
        for (ResponsableFamille resp : responsables) {
            genererPDF(resp, nomSuperPeriode, folderFact);
        }
    }

    private void genererPDF(ResponsableFamille resp, String nomSuperPeriode, File folderFact) throws Exception {
        final int milisecPerDay = 24 * 60 * 60 * 1000;
        float total = 0;

        Document document = new Document(PageSize.A4);
        final String routeFile = folderFact.getAbsolutePath() + "\\" + resp.getNomFamille() + resp.getPrenom() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(routeFile));
        document.open();

        Paragraph preface = new Paragraph();
        preface.add(new Paragraph(" "));
        preface.setAlignment(Element.ALIGN_CENTER);
        preface.add(new Paragraph("ACL Grenoble - Facture"));
        preface.add(new Paragraph(" "));
        preface.add(new Paragraph("Monsieur / Madamme : " + resp.getNomFamille() + " " + resp.getPrenom()));
        preface.add(new Paragraph("Periode: " + nomSuperPeriode));
        preface.add(new Paragraph(" "));
        document.add(preface);

        PdfPTable table = new PdfPTable(6);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Nom Enfant");
        table.addCell("Prenom Enfant");
        table.addCell("Activité");
        table.addCell("Prix Par Jour");
        table.addCell("Nb Jours");
        table.addCell("Prix Total");

        for (Enfant enfant : resp.getEnfants()) {
            for (InscriptionActivite inscription : enfant.getInscriptions()) {
                float nbJours = (inscription.getPeriode().getDatefin().getTime() - inscription.getPeriode().getDateDebut().getTime()) / (milisecPerDay);
                float prixActivite = inscription.getPrixParJour() * nbJours;
                total += prixActivite;
                table.addCell(enfant.getNomEnfant());
                table.addCell(enfant.getPrenomEnfant());
                table.addCell(inscription.getActivite().getNomActivite());
                table.addCell(String.valueOf(inscription.getPrixParJour()));
                table.addCell(String.valueOf(nbJours));
                table.addCell(String.valueOf(prixActivite));
            }
        }
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("TOTAL");
        table.addCell(String.valueOf(total));
        
        document.add(table);
        document.close();
        String[] attach = {routeFile};
        envoyerMail(resp, nomSuperPeriode, attach);
    }

    private void envoyerMail(ResponsableFamille resp, String nomSuperPeriode, String[] files) throws MessagingException {
        String message = "Bonjour " + resp.getNomFamille() + " " + resp.getPrenom() + ",\n\n"
                + "L’Association des Centres de Loisirs (ACL) de Grenoble vous informe "
                + "que votre facture pour le periode : " + nomSuperPeriode  + " a été generée, vous la trouverez ci-joint.\n"
                +"\n\nCordialement,\n"
                + "Responsable Association des Centres de Loisirs (ACL) Grenoble";
        new GestionMail().envoyerMail(resp.getMail(), "ACL Grenoble - Asignation Activité", message, files);
    }

}
