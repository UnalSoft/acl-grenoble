/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

import com.unsoft.acl_grenoble.model.centre.CentreDeLoisirs;

/**
 *
 * @author martijua
 */
public class Responsable extends Utilisateur {

   private RoleEnum role;
   private CentreDeLoisirs centre;

    public Responsable(String nomFamille, String prenom, String mail, RoleEnum role) {
        super(nomFamille, prenom, mail);
        this.role = role;
    }

   public RoleEnum getRole() {
      return role;
   }

    public CentreDeLoisirs getCentre() {
        return centre;
    }

    public void setCentre(CentreDeLoisirs centre) {
        this.centre = centre;
    }
 
}
