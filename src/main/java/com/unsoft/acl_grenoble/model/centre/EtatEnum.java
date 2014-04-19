package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author juanmanuelmartinezromero
 */
public enum EtatEnum {

    OUVERTE("OUVERTE"),
    FERMEE("FERMEE"),
    PRE_CONFIRMEE("PRE_CONFIRMEE"),
    CONFIRMEE("CONFIRMEE"),
    FINIE("FINIE");

    private final String etat;

    private EtatEnum(String etat) {
        this.etat = etat;
    }

    public String getName() {
        return etat;
    }

    public static EtatEnum getEtat(String etat) {
        for (EtatEnum et : values()) {
            if (etat.equals(et.getName())){
                return et;
            }
        }
        return null;
    }

}
