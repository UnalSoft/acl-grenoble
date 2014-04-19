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
    * Obtient un periode a partir de son nom
    *
    * @param nomPeriode
    * @return
    * @throws DAOException
    */
   public Periode getPeriode(String nomPeriode) throws DAOException {
      Periode periode = null;
      Connection conn = null;
      System.out.println("NomPeriode = " + nomPeriode);
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PERIODE WHERE PERIODE= ?");
         stmt.setString(1, nomPeriode);
         ResultSet rset = stmt.executeQuery();
         if (rset.next()) {
            periode = new Periode(rset.getString("PERIODE"), rset.getDate("DATEDEBUT"), rset.getDate("DATEFIN"));
            System.out.println("Periode: " + periode.nomPeriode());
         }
         rset.close();
         stmt.close();
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return periode;
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

   /**
    * Obtient une liste avec les periodes les plus generales
    *
    * @return @throws DAOException
    */
   public List<Periode> getPeriodesDisponibilite() throws DAOException {
      List<Periode> periodes = new ArrayList<Periode>();
      Connection conn = null;
      try {
         conn = getConnection();
         Statement st = conn.createStatement();
         String requeteSQL = "SELECT * FROM PERIODE WHERE SUPERPERIODE IS NULL";
         ResultSet rs = st.executeQuery(requeteSQL);
         while (rs.next()) {
            Periode periode = new Periode(rs.getString("PERIODE"), rs.getDate("DATEDEBUT"), rs.getDate("DATEFIN"));
            periodes.add(periode);
         }
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return periodes;
   }

   /**
    * Inserte un animateur à la base de données
    *
    * @param animateur
    * @throws DAOException
    */
   public void insererAnimateur(Animateur animateur) throws DAOException {
      //TODO Mettre l'animateur
      Connection conn = null;
      try {
         conn = getConnection();
         String requeteSQL = "INSERT INTO ANIMATEUR VALUES(?,?,?,?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         stmt.setString(1, animateur.getNomAnimateur());
         stmt.setString(2, animateur.getPrenomAnimateur());
         stmt.setString(3, animateur.getEmail());
         stmt.setBoolean(4, animateur.estInterne());
         stmt.executeUpdate();
         //TODO Mettre les competences de l'animateur
         insererCompetences(conn, animateur);
         //TODO Mettre les periodes de l'animateur
         insererPeriodes(conn, animateur);

      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }

   }

   /**
    * Inserte les competences pour l'animateur donné
    *
    * @param conn
    * @param animateur
    * @throws DAOException
    */
   private void insererCompetences(Connection conn, Animateur animateur) throws DAOException {
      for (Competence competence : animateur.getCompetences()) {
         try {
            String requeteSQL = "INSERT INTO COMPETENCEANIMATEUR VALUES(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(requeteSQL);
            stmt.setString(1, animateur.getNomAnimateur());
            stmt.setString(2, animateur.getPrenomAnimateur());
            stmt.setString(3, competence.getName());
            stmt.executeUpdate();
         } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
         }
      }
   }

   /**
    * Inserte les periodes de disponibilité pour l'animateur donné
    *
    * @param conn
    * @param animateur
    * @throws DAOException
    */
   private void insererPeriodes(Connection conn, Animateur animateur) throws DAOException {
      for (Periode periode : animateur.getPeriodes()) {
         try {
            String requeteSQL = "INSERT INTO EST_DISPONIBLE "
                    + "VALUES(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(requeteSQL);
            stmt.setString(1, animateur.getNomAnimateur());
            stmt.setString(2, animateur.getPrenomAnimateur());
            System.out.println("Periode = " + periode.nomPeriode());
            stmt.setString(3, periode.nomPeriode());
            stmt.executeUpdate();
         } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
         }
      }
   }
}
