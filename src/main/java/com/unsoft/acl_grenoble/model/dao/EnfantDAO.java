package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Activite;
import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;
import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.Theme;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import com.unsoft.acl_grenoble.model.utilisateur.Enfant;
import com.unsoft.acl_grenoble.model.utilisateur.ResponsableFamille;
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
 * @author CANTORAA
 */
public class EnfantDAO extends AbstractDataBaseDAO {

    public EnfantDAO(DataSource ds) {
        super(ds);
    }

    public void addEnfant(String prenomE, String nomE, String prenomR, String nomR,
            int ageE) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO ENFANT"
                    + " VALUES(?,?,?,?,?)");
            stmt.setString(1, prenomE);
            stmt.setString(2, nomE);
            stmt.setString(3, nomR);
            stmt.setString(4, prenomR);
            stmt.setInt(5, ageE);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new DAOException("BD erreur " + ex.getMessage(), ex);
        } finally {
            closeConnection(conn);
        }
    }

    public void effacerEnfant(String nomResponsable, String prenomResponsable) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM enfant "
                    + "WHERE nomFamille = ? AND prenom = ?");
            stmt.setString(1, nomResponsable);
            stmt.setString(2, prenomResponsable);
            stmt.executeQuery();

            stmt.close();

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public List<Enfant> getListeDEnfantsParRFamille(String nomResponsable, String prenomResponsable) throws DAOException {
        List<Enfant> listEnfants = new ArrayList<Enfant>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Enfant "
                    + "WHERE nomfamille = ? AND prenom = ?");

            st.setString(1, nomResponsable);
            st.setString(2, prenomResponsable);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Enfant enfant = new Enfant(rs.getString("nomFamillEnfant"), rs.getString("prenomEnfant"), rs.getInt("age"));
                listEnfants.add(enfant);
            }

            st.close();

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return listEnfants;
    }

    public List<Enfant> getEnfantsInscrisActivite(int idActivite, String nomPeriode) throws DAOException {
        List<Enfant> listEnfants = new ArrayList<Enfant>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT E.nomFamillEnfant, E.prenomEnfant, E.age, R.nomFamille, R.prenom, R.mail, R.ressources "
                    + "FROM Inscription I, Enfant E, RFamille R "
                    + "WHERE I.nomFamillEnfant = E.nomFamillEnfant AND I.prenomenfant = E.prenomenfant "
                    + "AND E.nomFamille = R.nomFamille AND E.prenom = R.prenom "
                    + "AND I.idActivite = ? AND periode = ?");

            st.setInt(1, idActivite);
            st.setString(2, nomPeriode);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ResponsableFamille resp = new ResponsableFamille(rs.getString("nomFamille"), rs.getString("prenom"), rs.getString("mail"), rs.getFloat("ressources"));
                Enfant enfant = new Enfant(rs.getString("prenomEnfant"), rs.getString("nomFamillEnfant"), rs.getInt("age"));
                enfant.setResponsable(resp);
                listEnfants.add(enfant);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return listEnfants;
    }

    public List<InscriptionActivite> getListeDInscriptionsParEnfant(String nomEnfant, String prenomEnfant) throws DAOException {
        List<InscriptionActivite> listeActivites = new ArrayList<InscriptionActivite>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT E.nomFamillEnfant, E.prenomEnfant, E.age, R.nomFamille, R.prenom, R.mail, R.ressources, "
                    + "I.prixParJour, A.idActivite, A.nomCentre, A.nomTheme, A.nom, A.descriptif, A.nbMaxAnim, A.prixParJour, "
                    + "P.periode, P.dateDebut, P.dateFin, P.superPeriode "
                    + "FROM Inscription I, Enfant E, RFamille R, Periode P, Activite A "
                    + "WHERE I.nomFamillEnfant = E.nomFamillEnfant AND I.prenomenfant = E.prenomenfant "
                    + "AND E.nomFamille = R.nomFamille AND E.prenom = R.prenom "
                    + "AND P.periode = I.periode "
                    + "AND I.idActivite = A.idActivite "
                    + "AND E.nomFamillEnfant = ? AND E.prenomenfant = ?");

            st.setString(1, nomEnfant);
            st.setString(2, prenomEnfant);
            ResultSet rset = st.executeQuery();

            while (rset.next()) {
                ResponsableFamille resp = new ResponsableFamille(rset.getString("nomFamille"), rset.getString("prenom"), rset.getString("mail"), rset.getFloat("ressources"));
                Enfant enfant = new Enfant(rset.getString("prenomEnfant"), rset.getString("nomFamillEnfant"), rset.getInt("age"));
                enfant.setResponsable(resp);
                Activite activite = new Activite(rset.getInt("idActivite"),
                        new Theme(ThemeEnum.getTheme(rset.getString("nomTheme")), new CentreDeLoisirs(rset.getString("nomCentre"))),
                        rset.getString("nom"), rset.getString("descriptif"), rset.getInt("nbMaxAnim"), rset.getFloat("prixParJour"));
                Periode periode = new Periode(rset.getString("periode"), rset.getDate("dateDebut"), rset.getDate("dateFin"), rset.getString("superPeriode"));

                InscriptionActivite inscription = new InscriptionActivite(enfant, activite, periode, rset.getFloat("prixParJour"));

                listeActivites.add(inscription);
            }

            st.close();

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
        return listeActivites;
    }

    public void inscrireEnfant(String prenomE, String nomE, int idActivite, String periode, float prix) throws DAOException {
        Connection conn = null;
        try {

            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Inscription"
                    + " VALUES(?,?,?,?,?)");
            stmt.setInt(1, idActivite);
            stmt.setString(2, prenomE);
            stmt.setString(3, nomE);
            stmt.setString(4, periode);
            stmt.setFloat(5, prix);

            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new DAOException("BD erreur " + ex.getMessage(), ex);
        } finally {
            closeConnection(conn);
        }
    }

    public void desInscrireEnfant(String prenomE, String nomE, int idActivite, String periode) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Inscription"
                    + " WHERE idActivite = ? AND prenomEnfant = ? AND nomFamillEnfant = ? AND periode = ?");
            stmt.setInt(1, idActivite);
            stmt.setString(2, prenomE);
            stmt.setString(3, nomE);
            stmt.setString(4, periode);

            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new DAOException("BD erreur " + ex.getMessage(), ex);
        } finally {
            closeConnection(conn);
        }
    }
}
