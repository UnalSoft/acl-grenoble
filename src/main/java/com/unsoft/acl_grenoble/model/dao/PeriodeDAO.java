package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Periode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    
    
}
