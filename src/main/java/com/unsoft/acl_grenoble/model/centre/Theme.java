package com.unsoft.acl_grenoble.model.centre;

/**
 *
 * @author Edward
 */
public class Theme {
    
    private ThemeEnum theme;
    private CentreDeLoisirs centre;

    public Theme(ThemeEnum theme, CentreDeLoisirs centre) {
        this.theme = theme;
        this.centre = centre;
    }

    public CentreDeLoisirs getCentre() {
        return centre;
    }

    public ThemeEnum getTheme() {
        return theme;
    }
    
}
