/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unsoft.acl_grenoble.model.centre;

import java.util.Date;
import java.util.List;

/**
 *
 * @author martijua
 */
public class Periode {
    private final String periode;
    private final Date dateDebut;
    private final Date datefin;
    private List<Periode> periodes;

   public Periode(String periode, Date dateDebut, Date datefin) {
      this.periode = periode;
      this.dateDebut = dateDebut;
      this.datefin = datefin;
   }

   public String nomPeriode() {
      return periode;
   }   
}
