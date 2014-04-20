package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Periode;
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
 * @author Edward
 */
public class AsignationDAO extends AbstractDataBaseDAO {

    public AsignationDAO(DataSource ds) {
        super(ds);
    }

    public List<Periode> getPeriodesAnimateur(String nomAnim, String prenomAnim) throws DAOException {
        List<Periode> periodes = new ArrayList<Periode>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT P.PERIODE, P.DATEDEBUT, P.DATEFIN "
                    + "FROM ASIGNATION A, PERIODE P\n"
                    + "WHERE A.PERIODE = P.PERIODE"
                    + "AND NOMANIMATEUR = ?\n"
                    + "AND PRENOMANIMATEUR = ?");
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
    
    public List<Periode> getPeriodesAnimateurParSuperPeriode(String nomAnim, String prenomAnim, String superPeriode) throws DAOException {
        List<Periode> periodes = new ArrayList<Periode>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement st = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement("SELECT P.PERIODE, P.DATEDEBUT, P.DATEFIN "
                    + "FROM ASIGNATION, PERIODE P\n"
                    + "WHERE A.PERIODE = P.PERIODE"
                    + "AND NOMANIMATEUR = ?\n"
                    + "AND PRENOMANIMATEUR = ?\n"
                    + "AND P.SUPERPERIODE ?");
            stmt.setString(1, nomAnim);
            stmt.setString(2, prenomAnim);
            stmt.setString(3, superPeriode);
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

    public void assignerAnimateur(Animateur animateur, String periode, int idActivite) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO ASIGNATION "
                    + "VALUES(?, ?, ?, ?)");
            stmt.setString(1, animateur.getNomAnimateur());
            stmt.setString(2, animateur.getPrenomAnimateur());
            stmt.setString(3, periode);
            stmt.setInt(4, idActivite);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        
    }

}
