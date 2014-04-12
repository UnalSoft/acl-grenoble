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
        this.responsable = responsable;
    }

    public String getName() {
        return responsable;
    }
    
    public static RoleEnum getRole(String role) {
        if (role.equals(R_ASSOCIATION.getName())){
            return R_ASSOCIATION;
        }
        if (role.equals(R_CENTRE.getName())){
            return R_CENTRE;
        }
        if (role.equals(R_PLANIFICATION.getName())) {
            return R_PLANIFICATION;
        }
        return null;
    }
    
}
