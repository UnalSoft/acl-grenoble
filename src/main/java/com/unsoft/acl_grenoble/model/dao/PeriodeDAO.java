package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.Periode;
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
public class PeriodeDAO extends AbstractDataBaseDAO {

    public PeriodeDAO(DataSource ds) {
        super(ds);
    }

    public Periode getPeriode(String nomPeriode) throws DAOException {
        Periode periode = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Periode "
                    + "WHERE periode = ?");
            stmt.setString(1, nomPeriode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("superPeriode"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return periode;
    }

    public List<Periode> getAllPeriodesOuvertParActivite(int idActivite) throws DAOException {
        List<Periode> periodes = new ArrayList<Periode>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT E.IDACTIVITE, E.ETAT,"
                    + "P.PERIODE,  P.SUPERPERIODE, P.DATEDEBUT, P.DATEFIN, E.ETAT\n"
                    + "FROM ETAT E, PERIODE P\n"
                    + "WHERE E.PERIODE = P.PERIODE\n"
                    + "AND E.IDACTIVITE = ?\n"
                    + "AND E.ETAT = ?");
            stmt.setInt(1, idActivite);
            stmt.setString(2, EtatEnum.OUVERTE.getName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Periode periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("superperiode"));
                periodes.add(periode);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return periodes;
    }

}
