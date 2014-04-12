/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

/**
 *
 * @author martijua
 */
public class Compte {

   private String nomUtilisateur;
   private String motdePass;
   private boolean actif;

   public Compte(String nomUtilisateur, String motdePass, boolean actif) {
      this.nomUtilisateur = nomUtilisateur;
      this.motdePass = motdePass;
      this.actif = actif;
   }

   public boolean isActif() {
      return actif;
   }

   public void setActif(boolean actif) {
      this.actif = actif;
   }
}
