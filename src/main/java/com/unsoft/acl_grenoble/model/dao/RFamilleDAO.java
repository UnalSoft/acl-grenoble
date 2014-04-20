package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.Compte;
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
public class RFamilleDAO extends AbstractDataBaseDAO {

    public RFamilleDAO(DataSource ds) {
        super(ds);
    }

    public void addResponsableFamille(String nomFamille, String prenom,
            String nomUtilisateur, String email, Double resources) throws DAOException {

        creerUtilisateur(nomFamille, prenom, nomUtilisateur, email);

        Connection conn = null;
        try {

            conn = getConnection();

            //Du coup, on doit creer l'utilisateur
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RFamille"
                    + " VALUES(?,?,?,?,?)");
            stmt.setString(1, nomFamille);
            stmt.setString(2, prenom);
            stmt.setString(3, nomUtilisateur);
            stmt.setString(4, email);
            stmt.setDouble(5, resources);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void creerUtilisateur(String nomFamille, String prenom, String nomUtilisateur, String email) throws DAOException {
        //On assume une connection déjà crée
        Connection conn = null;

        try {
            conn = getConnection();

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO UTILISATEUR"
                    + " VALUES(?,?,?,?)");
            stmt.setString(1, nomFamille);
            stmt.setString(2, prenom);
            stmt.setString(3, nomUtilisateur);
            stmt.setString(4, email);
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }

    }

    public ResponsableFamille getResponsable(String nomUtilisateur) throws DAOException {
        ResponsableFamille responsable = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RFamille "
                    + "WHERE nomUtilisateur = ?");
            stmt.setString(1, nomUtilisateur);
            ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                responsable = new ResponsableFamille(rset.getString("nomFamille"), rset.getString("prenom"), rset.getString("mail"),
                        rset.getFloat("ressources"));
            }

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

            stmt = conn.prepareStatement("DELETE FROM utilisateur "
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
