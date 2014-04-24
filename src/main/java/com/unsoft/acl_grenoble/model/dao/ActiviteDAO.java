package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Etat;
import com.unsoft.acl_grenoble.model.centre.EtatEnum;
import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import com.unsoft.acl_grenoble.model.centre.Periode;
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

    public String getNomParId(int idActivite) throws DAOException {
        String nomActivite = "InvalidName";
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT nom FROM ACTIVITE "
                    + "WHERE idActivite = ?");
            stmt.setInt(1, idActivite);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nomActivite = rs.getString("nom");
            }
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return nomActivite;
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
         stmt.setFloat(6, activite.getPrixParJour());
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

         String requeteSQL = "SELECT * FROM COMPETENCEACTIVITE WHERE IDACTIVITE=? AND COMPETENCE =?";
         String requeteSQL2 = "INSERT INTO COMPETENCEACTIVITE VALUES (?,?)";
         PreparedStatement stmt = conn.prepareStatement(requeteSQL);
         PreparedStatement stmt2 = conn.prepareStatement(requeteSQL2);
         stmt.setInt(1, idActivite);
         stmt2.setInt(1, idActivite);
         for (Competence competence : competences) {
            stmt.setString(2, competence.getName());
            stmt2.setString(2, competence.getName());
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
               stmt2.executeUpdate();
            }
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

         String requeteSQL0 = "SELECT PERIODE FROM PERIODE WHERE PERIODE=?";
         PreparedStatement stmt0 = conn.prepareStatement(requeteSQL0);
         stmt0.setString(1, periode.nomPeriode());
         ResultSet rs = stmt0.executeQuery();
         if (!rs.next()) {
            String requeteSQL = "INSERT INTO PERIODE (PERIODE, SUPERPERIODE, DATEDEBUT, DATEFIN)"
                    + " VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(requeteSQL);
            stmt.setString(1, periode.nomPeriode());
            stmt.setString(2, periode.getSuperPeriode());
            stmt.setDate(3, new java.sql.Date(periode.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(periode.getDateFin().getTime()));
            stmt.executeUpdate();
         }
         cree = true;
         rs.close();
         stmt0.close();
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
         stmt.setString(3, etat);
         stmt.executeUpdate();
         lie = true;
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return lie;
   }

   public Activite getActivite(int idActivite) throws DAOException {
      Activite activite = null;
      Connection conn = null;
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Activite "
                 + "WHERE idActivite = ?");
         stmt.setInt(1, idActivite);
         ResultSet rset = stmt.executeQuery();

         if (rset.next()) {
            activite = new Activite(rset.getInt("idActivite"),
                    new Theme(ThemeEnum.getTheme(rset.getString("nomTheme")), new CentreDeLoisirs(rset.getString("nomCentre"))),
                    rset.getString("nom"), rset.getString("descriptif"), rset.getInt("nbMaxAnim"), rset.getFloat("prixParJour"));
         }
         rset.close();
         stmt.close();
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return activite;
   }

   public int getnbInscris(int idActivite, String nomPeriode) throws DAOException {
      int inscris = 0;
      Connection conn = null;
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Inscription "
                 + "WHERE idActivite = ? AND periode = ?");
         stmt.setInt(1, idActivite);
         stmt.setString(2, nomPeriode);
         ResultSet rset = stmt.executeQuery();

         if (rset.next()) {
            inscris = rset.getInt(1);
         }
         rset.close();
         stmt.close();
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
      return inscris;
   }

   public void changerEtat(int idActivite, String nomPeriode, EtatEnum etatEnum) throws DAOException {
      Connection conn = null;
      try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement("UPDATE Etat "
                 + "SET ETAT = ? "
                 + "WHERE idActivite = ? AND periode = ?");
         stmt.setString(1, etatEnum.getName());
         stmt.setInt(2, idActivite);
         stmt.setString(3, nomPeriode);
         ResultSet rset = stmt.executeQuery();

         rset.close();
         stmt.close();
      } catch (SQLException e) {
         throw new DAOException("Erreur BD " + e.getMessage(), e);
      } finally {
         closeConnection(conn);
      }
   }

    public List<Activite> getAllActivites() throws DAOException {
        List<Activite> activites = new ArrayList<Activite>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Activite");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Activite activite = new Activite(rs.getInt("idActivite"),
                        new Theme(ThemeEnum.getTheme(rs.getString("nomTheme")), new CentreDeLoisirs(rs.getString("nomCentre"))),
                        rs.getString("nom"), rs.getString("descriptif"), rs.getInt("nbMaxAnim"), rs.getFloat("prixParJour"));
                activites.add(activite);

            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return activites;
    }

    public List<Activite> purifyListActivites(List<Activite> listeBrut, List<Etat> listeActivitesOuverts, List<InscriptionActivite> listeInscriptionsEnfant) {
        List<Activite> listePropre = new ArrayList<Activite>();
        
        for (Activite each : listeBrut) {
            listePropre.add(each);
        }
        for (Activite each : listeBrut) {
            boolean ouvert = false;
            for (Etat each2 : listeActivitesOuverts) {
                if (each.getIdActivite() == each2.getActivite().getIdActivite()) {
                    ouvert = true;
                }
            }
            if (!ouvert) {
                listePropre.remove(each);
            } else {
                boolean dejaInscrit = false;
                for (InscriptionActivite each2 : listeInscriptionsEnfant) {
                    if (each.getIdActivite() == each2.getActivite().getIdActivite()) {
                        dejaInscrit = true;
                    }
                }
                if (dejaInscrit) {
                    listePropre.remove(each);
                }
            }
        }
        return listePropre;
    }
}
