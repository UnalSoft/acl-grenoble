package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.Enfant;
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
 * @author CANTORAA
 */
public class EnfantDAO extends AbstractDataBaseDAO{

    public EnfantDAO(DataSource ds) {
        super(ds);
    }
    
    public void addEnfant(String prenomE,String nomE,String prenomR,String nomR,
            int ageE) throws DAOException{
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement  stmt = conn.prepareStatement("INSERT INTO ENFANT"
                    + " VALUES(?,?,?,?,?)");
            stmt.setString(1, prenomE);
            stmt.setString(2, nomE);
            stmt.setString(3, nomR);
            stmt.setString(4, prenomR);
            stmt.setInt(5, ageE);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new DAOException("BD erreur " + ex.getMessage(), ex);
        }
        finally{
            closeConnection(conn);
        }
    }
    
     public void effacerEnfant(String nomResponsable, String prenomResponsable) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM enfant "
                    + "WHERE nomFamille = ? AND prenom = ?");
            stmt.setString(1, nomResponsable);
            stmt.setString(2, prenomResponsable);
            stmt.executeQuery();

            stmt.close();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }
     
     public List<Enfant> getListeDEnfantsParRFamille(String nomResponsable, String prenomResponsable) throws DAOException {
        List<Enfant> listEnfants = new ArrayList<Enfant>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Enfant "
                    + "WHERE nomfamille = ? AND prenom = ?");
            
            st.setString(1, nomResponsable);
            st.setString(2, prenomResponsable);
            ResultSet rs = st.executeQuery();
                        
            while (rs.next()) {
                Enfant enfant = new Enfant(rs.getString("prenomEnfant"), rs.getString("nomFamillEnfant"), rs.getInt("age"));
                listEnfants.add(enfant);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return listEnfants;
    }
     
    
}
