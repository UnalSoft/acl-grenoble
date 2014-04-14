package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.RoleEnum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class ResponsableDAO extends AbstractDataBaseDAO {

    public ResponsableDAO(DataSource dataSource) {
        super(dataSource);
    }

    public Responsable getResponsable(Compte compte) throws DAOException {
        Responsable responsable = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Responsable"
                    + "WHERE nomUtilisateur = ?");
            stmt.setString(1, compte.getNomUtilisateur());
            ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                responsable = new Responsable(rset.getString("nomFamille"), rset.getString("prenom"), rset.getString("mail"),
                        RoleEnum.getRole(rset.getString("role")));
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
}
