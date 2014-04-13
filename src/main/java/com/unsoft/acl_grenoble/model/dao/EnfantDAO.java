package com.unsoft.acl_grenoble.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Edward
 */
public class EnfantDAO extends AbstractDataBaseDAO {

    public EnfantDAO(DataSource ds) {
        super(ds);
    }
    
    public void effacerEnfant(String nomResponsable, String prenomResponsable) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM enfant "
                    + "WHERE nomFamille = ? AND prenom = ?");
            stmt.setString(1, nomResponsable);
            stmt.executeQuery();

            stmt.close();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }
    
}
