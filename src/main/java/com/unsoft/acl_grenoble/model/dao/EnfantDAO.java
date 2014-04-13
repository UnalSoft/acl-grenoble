/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unsoft.acl_grenoble.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author CANTORAA
 */
public class EnfantDAO extends AbstractDataBaseDAO{

    public EnfantDAO(DataSource ds) {
        super(ds);
    }
    
    public void addEnfant(String prenomE,String nomE,String prenomR,String nomR,
            int ageE) throws DAOException{
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement  stmt = conn.prepareStatement("INSERT INTO ENFANT"
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
        }
        finally{
            closeConnection(conn);
        }
    }
}
