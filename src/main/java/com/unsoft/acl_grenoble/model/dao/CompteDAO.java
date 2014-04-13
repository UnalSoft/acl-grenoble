package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.Compte;
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
 * @author juanmanuelmartinezromero
 */
public class CompteDAO extends AbstractDataBaseDAO {

    public CompteDAO(DataSource dataSource) {
        super(dataSource);
    }
    
    public void addCompte(String nomUtilisateur,String motPass,int actif) throws DAOException{
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Compte"
                    + " VALUES (?,?,?)");
            stmt.setString(1, nomUtilisateur);
            stmt.setString(2, motPass);
            stmt.setInt(3, actif);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(),ex);
        }
        finally{
            closeConnection(conn);
        }
        
    }

    public Compte getCompte(String name, String mdp) throws DAOException {
        Compte compte;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Compte "
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

    public List<Compte> getComptesInactifs() throws DAOException {
        List<Compte> comptes = new ArrayList<Compte>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            String requeteSQL = "SELECT * FROM Compte "
                    + "WHERE actif = 0";
            ResultSet rs = st.executeQuery(requeteSQL);

            while (rs.next()) {
                Compte compte = new Compte(rs.getString("nomUtilisateur"), rs.getString("motDePass"), rs.getBoolean("actif"));
                comptes.add(compte);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return comptes;
    }

    public boolean activerCompte(String nomUtilisateur) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Compte SET actif = 1"
                    + "WHERE nomUtilisateur = ?");
            stmt.setString(1, nomUtilisateur);
            ResultSet rset = stmt.executeQuery();

            rset.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return true;
    }
    
    public void effacerCompte(String nomUtilisateur) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Compte "
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
