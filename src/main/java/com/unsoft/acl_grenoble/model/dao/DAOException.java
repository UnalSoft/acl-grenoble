
package com.unsoft.acl_grenoble.model.dao;

/**
 *
 * @author Philippe.Genoud@imag.fr
 */
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message,Throwable cause) {
        super(message,cause);
    }

}
