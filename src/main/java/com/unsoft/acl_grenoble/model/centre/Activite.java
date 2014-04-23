/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.centre;

import java.util.List;

/**
 *
 * @author martijua
 */
public class Activite {

   private final int idActivite;
   private final int nbMaxAnimateurs;
   private final float prix;
   private final String descriptif;
   private final String nomActivite;
   private List<Competence> competences;
   private final Theme theme;

   public Activite(int idActivite, Theme theme, String nomActivite, String descriptif, int nbMaxAnimateurs, float prix) {
      this.idActivite = idActivite;
      this.nbMaxAnimateurs = nbMaxAnimateurs;
      this.descriptif = descriptif;
      this.nomActivite = nomActivite;
      this.theme = theme;
      this.prix = prix;
   }

   public int getIdActivite() {
      return idActivite;
   }

   public int getNbMaxAnimateurs() {
      return nbMaxAnimateurs;
   }

   public String getDescriptif() {
      return descriptif;
   }

   public String getNomActivite() {
      return nomActivite;
   }

   public List<Competence> getCompetences() {
      return competences;
   }

   public Theme getTheme() {
      return theme;
   }

   public float getPrix() {
      return prix;
   }

   public void setPrix(float prix) {
      this.prix = prix;
   }
}
