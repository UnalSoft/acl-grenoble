/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.centre;

import com.unsoft.acl_grenoble.model.utilisateur.Enfant;

/**
 *
 * @author martijua
 */
public class InscriptionActivite {

    Enfant enfant;
    Activite activite;
    Periode periode;
    float prixParJour;

    public InscriptionActivite(Enfant enfant, Activite activite, Periode periode, float prixParJour) {
        this.enfant = enfant;
        this.activite = activite;
        this.periode = periode;
        this.prixParJour = prixParJour;
    }

    public Activite getActivite() {
        return activite;
    }

    public Enfant getEnfant() {
        return enfant;
    }

    public Periode getPeriode() {
        return periode;
    }

    public float getPrixParJour() {
        return prixParJour;
    }
    
    
}
