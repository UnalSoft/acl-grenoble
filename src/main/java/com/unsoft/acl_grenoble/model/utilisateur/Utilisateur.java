/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

/**
 *
 * @author martijua
 */
public abstract class Utilisateur {
    protected String nomFamille;
    protected String prenom;
    protected String mail;
    protected Compte compte;

    public Utilisateur(String nomFamille, String prenom, String mail) {
        this.nomFamille = nomFamille;
        this.prenom = prenom;
        this.mail = mail;
    }

    public String getNomFamille() {
        return nomFamille;
    }

    public String getPrenom() {
        return prenom;
    }
    
    public String getMail() {
        return mail;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
   
}
