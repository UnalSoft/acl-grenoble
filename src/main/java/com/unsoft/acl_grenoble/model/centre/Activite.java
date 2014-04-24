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
   private final String descriptif;
   private final String nomActivite;
   private List<Competence> competences;
   private final Theme theme;
   private float prixParJour;

    public Activite(int idActivite, Theme theme, String nomActivite, String descriptif, int nbMaxAnimateurs, float prixParJour) {
        this.idActivite = idActivite;
        this.nbMaxAnimateurs = nbMaxAnimateurs;
        this.descriptif = descriptif;
        this.nomActivite = nomActivite;
        this.theme = theme;
        this.prixParJour = prixParJour;
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

    public float getPrixParJour() {
        return prixParJour;
    }
    
}
