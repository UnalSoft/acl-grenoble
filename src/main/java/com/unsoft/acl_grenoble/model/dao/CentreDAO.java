package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
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
    *
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
         PreparedStatement stmt = conn.prepareStatement(
                 "SELECT THEME.NOMTHEME,THEME.NOMCENTRE FROM THEME,RESPONSABLE \n"
                 + "WHERE THEME.NOMCENTRE=RESPONSABLE.NOMCENTRE \n"
                 + "AND RESPONSABLE.NOMUTILISATEUR=?");
         stmt.setString(1, utilisateur);
         ResultSet rs =stmt.executeQuery();
         while(rs.next()){
            Theme theme = new Theme(
                    ThemeEnum.getTheme(rs.getString(1)), 
                    new CentreDeLoisirs(rs.getString(2)));
            themes.add(theme);
         }
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      }finally{
         closeConnection(conn);
      }
      return themes;
   }
}
