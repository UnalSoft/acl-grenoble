package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Edward
 */
public class RFamilleDAO extends AbstractDataBaseDAO{

    public RFamilleDAO(DataSource ds) {
        super(ds);
    }
    
    public ResponsableFamille getResponsable(String nomUtilisateur) throws DAOException {
        ResponsableFamille responsable;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RFamille"
                    + "WHERE nomUtilisateur = ?");
            stmt.setString(1, nomUtilisateur);
            ResultSet rset = stmt.executeQuery();

            rset.next();
            responsable = new ResponsableFamille(rset.getString("nomFamille"), rset.getString("prenom"), rset.getString("mail"), 
                    rset.getFloat("ressources"));

            rset.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return responsable;
    }
    
    public void effacerResponsable(String nomUtilisateur) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM rFamille "
                    + "WHERE nomUtilisateur = ?");
            stmt.setString(1, nomUtilisateur);
            stmt.executeQuery();

            stmt.close();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }
    
}
