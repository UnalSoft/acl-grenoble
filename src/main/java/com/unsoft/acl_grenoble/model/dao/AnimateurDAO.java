/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Animateur;
import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
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
