/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Competence;
import com.unsoft.acl_grenoble.model.centre.Periode;
import com.unsoft.acl_grenoble.model.centre.ThemeEnum;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author sparrow
 */
public class ActiviteDAO extends AbstractDataBaseDAO{

    public ActiviteDAO(DataSource dataSource) {
        super(dataSource);
    }

    public List<ThemeEnum> getThemesPossibles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Periode> getPeriodesPossibles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int createActivite(String nom, String descriptif, int nmMaxAnim,
            String nomCentre, String nomTheme){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierCompetences(int idActivite, List<Competence> competences){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean creerPeriode(Date dateDebut, Date dateFin){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean lierEtat(int idActivite, String periode, String etat){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
