/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.centre;

import java.util.List;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class Animateur {

   private final String nomAnimateur;
   private final String prenomAnimateur;
   private final String email;
   private final boolean interne;
   private final List<Competence> competences;
   private final List<Periode> periodes;

   public Animateur(String nomAnimateur, String prenomAnimateur, String email, boolean estInterne, List<Competence> competences, List<Periode> periodes) {
      this.nomAnimateur = nomAnimateur;
      this.prenomAnimateur = prenomAnimateur;
      this.email = email;
      this.interne = estInterne;
      this.competences = competences;
      this.periodes = periodes;
   }

   public String getNomAnimateur() {
      return nomAnimateur;
   }

   public String getPrenomAnimateur() {
      return prenomAnimateur;
   }

   public String getEmail() {
      return email;
   }

   public boolean estInterne() {
      return interne;
   }

   public List<Competence> getCompetences() {
      return competences;
   }

   public List<Periode> getPeriodes() {
      return periodes;
   }
}
