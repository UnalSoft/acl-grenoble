package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author juanmanuelmartinezromero
 */
public enum Competence {

    AISANCE_ORATOIRE("Aisance oratoire"),
    APTITUDE_COMUNICATION("Aptitude à la communication"),
    CHARISME("Charisme"),
    CAPACITE_SUPERVISER("Capacité à superviser"),
    COLLABORATEUR("Collaborateur"),
    COMPETITIF("Compétitif"),
    CREATIF("Créatif"),
    DYNAMIQUE("Dynamique"),
    DROLE("Drôle"),
    ESPRIT_EQUIPE("Esprit d’équipe"),
    HABILITE_GERER_CONFLITS("Habileté à gérer des conflits"),
    IMAGINATIF("Imaginatif"),
    LEADERSHIP("Leadership"),
    MOTIVATEUR("Motivateur"),
    MOTIVE("Motivé"),
    PEDAGOGUE("Pédagogue"),
    PONCTUEL("Ponctuel"),
    SENS_HUMOUR("Sens de l’humour"),
    SOCIABLE("Sociable"),
    STRATEGIQUE("Stratégique"),
    SPONTANE("Spontané"),
    VERSATILE("Versatile");

    private final String competence;

    private Competence(String competence) {
        this.competence = competence;
    }

    public String getName() {
        return competence;
    }

    public static Competence getCompetence(String competence) {
        for (Competence comp : values()) {
            if (competence.equals(comp.getName())) {
                return comp;
            }
        }
        return null;
    }

}
