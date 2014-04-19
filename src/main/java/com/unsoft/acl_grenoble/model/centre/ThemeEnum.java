package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author juanmanuelmartinezromero
 */
public enum ThemeEnum {
    
    DECOUVERTE_NATURE("Découverte de la nature"),
    INITIATION_ENVIRONNEMENT("Initiation à l’environnement"),
    EXPRESSION_ARTISTIQUE("Expression artistique"),
    SPORT_COLLECTIV("Sport collectif"),
    SPORT_EXTREME("Sport extreme"),
    SPORT_PRECISION("Sport de précision");
    
    private final String theme;

    private ThemeEnum(String theme) {
        this.theme = theme;
    }

    public String getName() {
        return theme;
    }

    public static ThemeEnum getTheme(String theme) {
        for (ThemeEnum th : values()) {
            if (theme.equals(th.getName())){
                return th;
            }
        }
        return null;
    }
}
