package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class CompteDAO extends AbstractDataBaseDAO {

    public CompteDAO(DataSource dataSource) {
        super(dataSource);
    }
    
    public Compte getCompte(String name, String mdp) throws DAOException {
        Compte compte;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Compte"
                    + "WHERE nomUtilisateur = ? AND motDePass = ?");
            stmt.setString(1, name);
            stmt.setString(2, mdp);
            ResultSet rset = stmt.executeQuery();

            rset.next();
            compte = new Compte(rset.getString("nomUtilisateur"), rset.getString("motDePass"), rset.getBoolean("actif"));

            rset.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return compte;
    }
    
    public List<Compte> getComptesInactifs(){
       throw new UnsupportedOperationException("Not supported yet.");
   }
   
   public boolean activerCompte(String name){
       throw new UnsupportedOperationException("Not supported yet.");
   }
    
}
