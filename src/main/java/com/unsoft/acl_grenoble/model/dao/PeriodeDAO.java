package com.unsoft.acl_grenoble.model.dao;

import com.unsoft.acl_grenoble.model.centre.Periode;
import javax.sql.DataSource;

/**
 *
 * @author Edward
 */
public class PeriodeDAO extends AbstractDataBaseDAO {

    public PeriodeDAO(DataSource ds) {
        super(ds);
    }

    public Periode getPeriode(String periode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
