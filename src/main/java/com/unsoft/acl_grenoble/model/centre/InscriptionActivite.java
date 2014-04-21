/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.centre;

import com.unsoft.acl_grenoble.model.utilisateur.Enfant;

/**
 *
 * @author sparrow
 */
public class InscriptionActivite {

    String nomEnfant;
    String prenomEnfant;
    int idActivite;
    String nomActivite;
    String periode;

    public InscriptionActivite(String nomEnfant, String prenomEnfant, int idActivite, String nomActivite, String periode) {
        this.nomEnfant = nomEnfant;
        this.prenomEnfant = prenomEnfant;
        this.idActivite = idActivite;
        this.nomActivite = nomActivite;
        this.periode = periode;
    }

    public InscriptionActivite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNomEnfant() {
        return nomEnfant;
    }

    public void setNomEnfant(String nomEnfant) {
        this.nomEnfant = nomEnfant;
    }

    public String getPrenomEnfant() {
        return prenomEnfant;
    }

    public void setPrenomEnfant(String prenomEnfant) {
        this.prenomEnfant = prenomEnfant;
    }

    public int getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(int idActivite) {
        this.idActivite = idActivite;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

}
