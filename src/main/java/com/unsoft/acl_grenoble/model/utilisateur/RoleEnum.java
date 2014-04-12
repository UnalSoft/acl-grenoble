/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.utilisateur;

/**
 *
 * @author martijua
 */
public enum RoleEnum {

   R_ASSOCIATION("R_ASSOCIATION"),
   R_CENTRE("R_CENTRE"),
   R_PLANIFICATION("R_PLANIFICATION");

   private final String responsable;
   private RoleEnum(String responsable) {
      this.responsable=responsable;
   }
}
