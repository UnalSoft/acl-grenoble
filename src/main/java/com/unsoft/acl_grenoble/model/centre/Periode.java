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
    private String superPeriode;

   public Periode(String periode, Date dateDebut, Date datefin) {
      this.periode = periode;
      this.dateDebut = dateDebut;
      this.datefin = datefin;
   }

    public Periode(String periode, Date dateDebut, Date datefin, String superPeriode) {
        this.periode = periode;
        this.dateDebut = dateDebut;
        this.datefin = datefin;
        this.superPeriode = superPeriode;
    }

   public String nomPeriode() {
      return periode;
   }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return datefin;
    }

    public String getSuperPeriode() {
        return superPeriode;
    }
   
}
