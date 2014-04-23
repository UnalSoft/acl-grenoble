package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
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
 * @author sparrow
 */
public class ActiviteDAO extends AbstractDataBaseDAO {

   public ActiviteDAO(DataSource dataSource) {
      super(dataSource);
   }

   public List<Competence> getCompetences(int idActivite) throws DAOException {
      List<Competence> competences = new ArrayList<Competence>();
      Connection conn = null;
      try {
         conn = getConnection();
         Statement st = conn.createStatement();
         PreparedStatement stmt = conn.prepareStatement("SELECT COMPETENCE FROM COMPETENCEACTIVITE\n"
                 + "WHERE IDACTIVITE = ?");
         stmt.setInt(1, idActivite);
         ResultSet rs = stmt.executeQuery();

         while (rs.next()) {
            Competence comp = Competence.getCompetence(rs.getString("competence"));
            if (comp != null) {
               competences.add(comp);
            }
         }
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return competences;
   }

   /*public Theme getTheme(String nomTheme, String centreParUtilisateur) {
    Theme theme = null;
    Connection conn = null;
    try {
    conn = getConnection();
    Statement st = conn.createStatement();
    PreparedStatement stmt = conn.prepareStatement(
    "SELECT THEME.NOMTHEME,THEME.NOMCENTRE FROM THEME,RESPONSABLE \n"
    + "WHERE THEME.NOMCENTRE=RESPONSABLE.NOMCENTRE \n"
    + "AND RESPONSABLE.NOMUTILISATEUR=?");
    stmt.setString(1, utilisateur);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
    Theme theme = new Theme(
    ThemeEnum.getTheme(rs.getString(1)),
    new CentreDeLoisirs(rs.getString(2)));
    theme.add(theme);
    }
    } catch (SQLException e) {
    throw new DAOException("Erreur BD " + e.getMessage(), e);
    } finally {
    closeConnection(conn);
    }
    return theme;
    }*/
   public List<ThemeEnum> getThemesPossibles() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public List<Periode> getPeriodesPossibles() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public int createActivite(Activite activite) throws DAOException {
      Connection conn = null;
      int idActivite = -1;
      try {
         conn = getConnection();
         String requeteSQL = "INSERT INTO ACTIVITE "
                 + "(NOMCENTRE, NOMTHEME, NOM, DESCRIPTIF, NBMAXANIM, PRIXPARJOUR)"
                 + "	VALUES (?,?,?,?,?,?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         stmt.setString(1, activite.getTheme().getCentre().getNomCentre());
         stmt.setString(2, activite.getTheme().getTheme().getName());
         stmt.setString(3, activite.getNomActivite());
         stmt.setString(4, activite.getDescriptif());
         stmt.setInt(5, activite.getNbMaxAnimateurs());
         stmt.setFloat(6, activite.getPrix());
         stmt.executeUpdate();

         requeteSQL = "SELECT idActivite_seq.currval FROM dual";
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(requeteSQL);
         if (rs.next()) {
            idActivite = (rs.getInt("CURRVAL"));
         }
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return idActivite;
   }

   public boolean lierCompetences(int idActivite, List<Competence> competences) throws DAOException {
      Connection conn = null;
      boolean liees = false;
      try {
         conn = getConnection();
         String requeteSQL = "INSERT INTO COMPETENCEACTIVITE "
                 + "	VALUES (?,?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         stmt.setInt(1, idActivite);
         for (Competence competence : competences) {
            stmt.setString(2, competence.getName());
            stmt.executeUpdate();
         }
         liees = true;
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return liees;
   }

   public boolean creerPeriode(Periode periode) throws DAOException {
      Connection conn = null;
      boolean cree = false;
      try {
         conn = getConnection();
         String requeteSQL = "INSERT INTO PERIODE (PERIODE, SUPERPERIODE, DATEDEBUT, DATEFIN)"
                 + " VALUES (?, ?, ?, ?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         stmt.setString(1, periode.nomPeriode());
         stmt.setString(2, periode.getSuperPeriode());
         stmt.setDate(3, new java.sql.Date(periode.getDateDebut().getTime()));
         stmt.setDate(4, new java.sql.Date(periode.getDateFin().getTime()));
         stmt.executeUpdate();
         cree = true;
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return cree;
   }

   public boolean lierEtat(int idActivite, String periode, String etat) throws DAOException {
      Connection conn = null;
      boolean lie = false;
      try {
         conn = getConnection();
         String requeteSQL = "INSERT INTO ETAT(IDACTIVITE, PERIODE, ETAT)"
                 + "VALUES (?, ?, ?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         stmt.setInt(1, idActivite);
         stmt.setString(2, periode);
         stmt.setString(3,etat);
         stmt.executeUpdate();
         lie = true;
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return lie;
   }

}
