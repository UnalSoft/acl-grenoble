package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Theme;
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
public class CentreDAO extends AbstractDataBaseDAO {

   public CentreDAO(DataSource dataSource) {
      super(dataSource);
   }

   /**
    * Obtient le nom du centre a partir du nom du responsable
    * @param responsable
    * @return
    * @throws DAOException
    */
   public String getCentreParUtilisateur(String responsable) throws DAOException {
      String centre = null;
      Connection conn = null;
      try {
         conn = getConnection();
         Statement st = conn.createStatement();
         PreparedStatement stmt = conn.prepareStatement("SELECT NOMCENTRE "
                 + "FROM RESPONSABLE WHERE NOMUTILISATEUR=?");
         stmt.setString(1, responsable);
         ResultSet rs = stmt.executeQuery();
         if (rs.next())
            centre = rs.getString("NOMCENTRE");
      } catch (SQLException ex) {
         throw new DAOException("Erreur BD " + ex.getMessage(), ex);
      } finally {
         closeConnection(conn);
      }
      return centre;
   }

   public List<Theme> getThemesParUtilisateur(String utilisateur) throws DAOException {
      List<Theme> themes = new ArrayList<Theme>();
      Connection conn = null;
      try {
         conn = getConnection();
         Statement st = conn.createStatement();
         
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      }
      return themes;
   }
}