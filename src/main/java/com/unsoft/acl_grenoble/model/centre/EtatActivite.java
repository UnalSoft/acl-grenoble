package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author juanmanuelmartinezromero
 */
public enum EtatActivite {

    OUVERTE("OUVERTE"),
    FERMEE("FERMEE"),
    PRE_CONFIRMEE("PRE_CONFIRMEE"),
    CONFIRMEE("CONFIRMEE"),
    FINIE("FINIE");

    private final String etat;

    private EtatActivite(String etat) {
        this.etat = etat;
    }

    public String getName() {
        return etat;
    }

    public EtatActivite getEtat(String etat) {
        for (EtatActivite et : values()) {
            if (etat.equals(et.getName())){
                return et;
            }
        }
        return null;
    }

}
