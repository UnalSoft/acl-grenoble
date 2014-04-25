package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.utilisateur.Compte;
import com.unsoft.acl_grenoble.model.utilisateur.Responsable;
import com.unsoft.acl_grenoble.model.utilisateur.RoleEnum;
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
 * @author juanmanuelmartinezromero
 */
public class ResponsableDAO extends AbstractDataBaseDAO {

   public ResponsableDAO(DataSource dataSource) {
      super(dataSource);
   }

   /**
    * Determine si un animateur existe dans la Base de données
    *
    * @param nom
    * @param prenom
    * @return
    * @throws DAOException
    */
   public boolean animateurExist(String nom, String prenom) throws DAOException {
      boolean exist = false;
      Connection conn = null;
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ANIMATEUR "
                 + "WHERE NOMANIMATEUR = ? AND PRENOMANIMATEUR = ?");
         stmt.setString(1, nom);
         stmt.setString(2, prenom);
         ResultSet rset = stmt.executeQuery();
         if (rset.next())
            exist = true;
         rset.close();
         stmt.close();
      } catch (SQLException ex) {
         throw new DAOException("Erreur BD " + ex.getMessage(), ex);
      } finally {
         closeConnection(conn);
      }
      return exist;
   }

   /**
    * Obtient un responsable a partir de sa compte
    *
    * @param compte
    * @return
    * @throws DAOException
    */
   public Responsable getResponsable(Compte compte) throws DAOException {
      Responsable responsable = null;
      Connection conn = null;
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Responsable "
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
