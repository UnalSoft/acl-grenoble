package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author sparrow
 */
public class ActiviteDAO extends AbstractDataBaseDAO {

    public ActiviteDAO(DataSource dataSource) {
        super(dataSource);
    }

    public List<Competence> getCompetences(int idActivite) throws DAOException {
        List<Competence> competences = new ArrayList<Competence>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT COMPETENCE FROM COMPETENCEACTIVITE\n"
                    + "WHERE IDACTIVITE = ?");
            stmt.setInt(1, idActivite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Competence comp = Competence.getCompetence(rs.getString("competence"));
                if (comp != null) {
                    competences.add(comp);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return competences;
    }

    public List<ThemeEnum> getThemesPossibles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Periode> getPeriodesPossibles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int createActivite(String nom, String descriptif, int nmMaxAnim,
            String nomCentre, String nomTheme) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierCompetences(int idActivite, List<Competence> competences) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean creerPeriode(Date dateDebut, Date dateFin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierEtat(int idActivite, String periode, String etat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Activite getActivite(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
