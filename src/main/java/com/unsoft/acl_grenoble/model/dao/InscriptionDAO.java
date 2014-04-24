package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import com.unsoft.acl_grenoble.model.utilisateur.Enfant;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Edward
 */
public class InscriptionDAO extends AbstractDataBaseDAO {

    public InscriptionDAO(DataSource ds) {
        super(ds);
    }

    public List<InscriptionActivite> getInscriptionsParPeriode(String nomSuperPeriode) throws DAOException {
        List<InscriptionActivite> listInscriptions = new ArrayList<InscriptionActivite>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT E.nomFamillEnfant, E.prenomEnfant, E.age, R.nomFamille, R.prenom, R.mail, R.ressources, "
                    + "I.prixParJour, A.idActivite, A.nomCentre, A.nomTheme, A.nom, A.descriptif, A.nbMaxAnim, A.prixParJour, "
                    + "P.periode, P.dateDebut, P.dateFin, P.superPeriode "
                    + "FROM Inscription I, Enfant E, RFamille R, Periode P, Activite A "
                    + "WHERE I.nomFamillEnfant = E.nomFamillEnfant AND I.prenomenfant = E.prenomenfant "
                    + "AND E.nomFamille = R.nomFamille AND E.prenom = R.prenom "
                    + "AND P.periode = I.periode "
                    + "AND I.idActivite = A.idActivite "
                    + "AND P.superPeriode = ?");

            st.setString(1, nomSuperPeriode);
            ResultSet rset = st.executeQuery();

            while (rset.next()) {
                ResponsableFamille resp = new ResponsableFamille(rset.getString("nomFamille"), rset.getString("prenom"), rset.getString("mail"), rset.getFloat("ressources"));
                Enfant enfant = new Enfant(rset.getString("prenomEnfant"), rset.getString("nomFamillEnfant"), rset.getInt("age"));
                enfant.setResponsable(resp);
                Activite activite = new Activite(rset.getInt("idActivite"),
                        new Theme(ThemeEnum.getTheme(rset.getString("nomTheme")), new CentreDeLoisirs(rset.getString("nomCentre"))),
                        rset.getString("nom"), rset.getString("descriptif"), rset.getInt("nbMaxAnim"), rset.getFloat("prixParJour"));
                Periode periode = new Periode(rset.getString("periode"), rset.getDate("dateDebut"), rset.getDate("dateFin"), rset.getString("superPeriode"));

                InscriptionActivite inscription = new InscriptionActivite(enfant, activite, periode, rset.getFloat("prixParJour"));

                listInscriptions.add(inscription);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return listInscriptions;
    }

}
