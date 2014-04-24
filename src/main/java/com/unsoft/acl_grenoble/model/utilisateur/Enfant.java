/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

import com.unsoft.acl_grenoble.model.centre.InscriptionActivite;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sparrow
 */
public class Enfant {

    private String nomEnfant;
    private String prenomEnfant;
    private int age;
    private ResponsableFamille responsable;
    private List<InscriptionActivite> inscriptions =  new ArrayList<InscriptionActivite>();

    public Enfant(String nomEnfant, String prenomEnfant, int age) {
        this.nomEnfant = nomEnfant;
        this.prenomEnfant = prenomEnfant;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ResponsableFamille getResponsable() {
        return responsable;
    }  

    public void setResponsable(ResponsableFamille responsable) {
        this.responsable = responsable;
    }

    public List<InscriptionActivite> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<InscriptionActivite> inscriptions) {
        this.inscriptions = inscriptions;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Enfant) {
            Enfant autreResp = (Enfant) obj;
            if (getNomEnfant().equals(autreResp.getNomEnfant())
                    && getPrenomEnfant().equals(autreResp.getPrenomEnfant())) {
                return true;
            }
        }
        return false;
    }

}
