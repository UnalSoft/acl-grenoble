package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
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
 * @author sparrow
 */
public class AnimateurDAO extends AbstractDataBaseDAO {

    public AnimateurDAO(DataSource dataSource) {
        super(dataSource);
    }

    public List<Competence> getCompetences(String nomAnim, String prenomAnim) throws DAOException {
        List<Competence> competences = new ArrayList<Competence>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT COMPETENCE FROM COMPETENCEANIMATEUR\n"
                    + "WHERE NOMANIMATEUR = ?\n"
                    + "AND PRENOMANIMATEUR = ?");
            stmt.setString(1, nomAnim);
            stmt.setString(2, prenomAnim);
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

    public List<Periode> getPeriodesDisp(String nomAnim, String prenomAnim) throws DAOException {
        List<Periode> periodes = new ArrayList<Periode>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT P.PERIODE, P.DATEDEBUT, P.DATEFIN "
                    + "FROM EST_DISPONIBLE E, PERIODE P\n"
                    + "WHERE E.PERIODE = P.PERIODE\n"
                    + "AND E.NOMANIMATEUR = ? AND E.PRENOMANIMATEUR = ?");
            stmt.setString(1, nomAnim);
            stmt.setString(2, prenomAnim);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Periode periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"));
                periodes.add(periode);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return periodes;
    }
    
    public List<Periode> getPeriodesDispExtern(String nomAnim, String prenomAnim) throws DAOException {
        List<Periode> periodes = new ArrayList<Periode>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT P.PERIODE, P.DATEDEBUT, P.DATEFIN "
                    + "FROM EST_DISPONIBLE E, PERIODE P, ANIMATEUR A\n"
                    + "WHERE E.PERIODE = P.PERIODE\n"
                    + "AND E.NOMANIMATEUR = A.NOMANIMATEUR\n"
                    + "AND E.PRENOMANIMATEUR = A.PRENOMANIMATEUR\n"
                    + "AND E.NOMANIMATEUR = ? AND E.PRENOMANIMATEUR = ?\n"
                    + "AND A.ESTINTERNE = 0");
            stmt.setString(1, nomAnim);
            stmt.setString(2, prenomAnim);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Periode periode = new Periode(rs.getString("periode"), rs.getDate("dateDebut"), rs.getDate("dateFin"));
                periodes.add(periode);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return periodes;
    }
    
    public List<Animateur> getAnimateursDisp(String periode, boolean estInterne) throws DAOException {
        List<Animateur> animateurs = new ArrayList<Animateur>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT A.NOMANIMATEUR, A.PRENOMANIMATEUR, "
                    + "A.EMAIL, A.ESTINTERNE "
                    + "FROM EST_DISPONIBLE E, ANIMATEUR A "
                    + "WHERE E.NOMANIMATEUR = A.NOMANIMATEUR "
                    + "AND E.PRENOMANIMATEUR = A.PRENOMANIMATEUR "
                    + "AND A.ESTINTERNE = ? "
                    + "AND E.PERIODE = ?");
            stmt.setBoolean(1, estInterne);
            stmt.setString(2, periode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Animateur animateur = new Animateur(rs.getString("nomAnimateur"), rs.getString("prenomAnimateur"),
                rs.getString("email"), rs.getBoolean("estInterne"));
                animateurs.add(animateur);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return animateurs;
    }

    public Animateur getAnimateur(String nomAnimateur, String prenomAnimateur) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Animateur addAnimateur(String nomAnimateur, String prenomAnimateur, String email, boolean isIntern) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean makeIntern(String nomAnimateur, String prenomAnimateur) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Competence> getAllCompetences() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierCompetences(String nomAnimateur, String prenomAnimateur, List<Competence> competences) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Periode> getAllDefaultPeriodes() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierPeriodes(String nomAnimateur, String prenomAnimateur, Date dateDebut, Date dateFin) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
