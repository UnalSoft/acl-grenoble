package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author juanmanuelmartinezromero
 */
public enum Competence {

    CHARISME("Charisme"),
    LEADERSHIP("Leadership");
    
    private final String competence;

    private Competence(String competence) {
        this.competence = competence;
    }

    public String getName() {
        return competence;
    }

    public Competence getCompetence(String competence) {
        for (Competence comp : values()) {
            if (competence.equals(comp.getName())){
                return comp;
            }
        }
        return null;
    }

}
