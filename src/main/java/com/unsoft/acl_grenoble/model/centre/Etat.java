package com.unsoft.acl_grenoble.model.centre;

/**
 * Map de la table état
 *
 * @author juanmanuelmartinezromero
 */
public class Etat {

   Activite activite;
   Periode periode;
   EtatEnum etat;

    public Etat(Activite activite, Periode periode, EtatEnum etat) {
        this.activite = activite;
        this.periode = periode;
        this.etat = etat;
    }

    public EtatEnum getEtat() {
        return etat;
    }

    public Periode getPeriode() {
        return periode;
    }

    public Activite getActivite() {
        return activite;
    }

}
