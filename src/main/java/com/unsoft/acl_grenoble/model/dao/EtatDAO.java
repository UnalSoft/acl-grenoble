package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.Etat;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Edward
 */
public class EtatDAO extends AbstractDataBaseDAO {

    public EtatDAO(DataSource ds) {
        super(ds);
    }

    public List<Etat> getActivitesPreConfirmees(String nomCentre) throws DAOException {
        List<Etat> etats = new ArrayList<Etat>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT A.IDACTIVITE, A.NOMCENTRE, A.NOMTHEME, A.NOM, A.DESCRIPTIF, A.NBMAXANIM, A.PRIXPARJOUR, "
                    + "P.PERIODE,  P.SUPERPERIODE, P.DATEDEBUT, P.DATEFIN, E.ETAT\n"
                    + "FROM ACTIVITE A, ETAT E, PERIODE P\n"
                    + "WHERE A.IDACTIVITE = E.IDACTIVITE\n"
                    + "AND P.PERIODE = E.PERIODE\n"
                    + "AND A.NOMCENTRE = ?\n"
                    + "AND E.ETAT = ?");
            stmt.setString(1, nomCentre);
            stmt.setString(2, EtatEnum.PRE_CONFIRMEE.getName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Activite activite = new Activite(rs.getInt("idActivite"),
                        new Theme(ThemeEnum.getTheme(rs.getString("nomTheme")), new CentreDeLoisirs(rs.getString("nomCentre"))),
                        rs.getString("nom"), rs.getString("descriptif"), rs.getInt("nbMaxAnim"), rs.getFloat("prixParJour"));
                Periode periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("superperiode"));
                Etat etat = new Etat(activite, periode, EtatEnum.getEtat(rs.getString("etat")));
                etats.add(etat);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return etats;
    }

    public List<Etat> getActivitesParEtat(String etat) throws DAOException {
        List<Etat> etats = new ArrayList<Etat>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT A.IDACTIVITE, A.NOMCENTRE, A.NOMTHEME, A.NOM, A.DESCRIPTIF, A.NBMAXANIM, "
                    + "P.PERIODE,  P.SUPERPERIODE, P.DATEDEBUT, P.DATEFIN, E.ETAT\n"
                    + "FROM ACTIVITE A, ETAT E, PERIODE P\n"
                    + "WHERE A.IDACTIVITE = E.IDACTIVITE\n"
                    + "AND P.PERIODE = E.PERIODE\n"
                    + "AND E.ETAT = ?");
            stmt.setString(1, etat);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Activite activite = new Activite(rs.getInt("idActivite"),
                        new Theme(ThemeEnum.getTheme(rs.getString("nomTheme")), new CentreDeLoisirs(rs.getString("nomCentre"))),
                        rs.getString("nom"), rs.getString("descriptif"), rs.getInt("nbMaxAnim"), rs.getFloat("prixParJour"));
                Periode periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("superperiode"));
                Etat ett = new Etat(activite, periode, EtatEnum.getEtat(etat));
                etats.add(ett);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return etats;
    }

}
