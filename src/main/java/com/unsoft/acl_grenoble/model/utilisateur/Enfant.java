/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

/**
 *
 * @author sparrow
 */
public class Enfant {

    private String nomEnfant;
    private String prenomEnfant;
    private int age;
    private ResponsableFamille responsable;

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

}
