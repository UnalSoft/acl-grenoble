package com.unsoft.acl_grenoble.model.utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martijua
 */
public class ResponsableFamille extends Utilisateur {

    private float ressources;
    //Implementation de composition
    private List<Enfant> enfants = new ArrayList<Enfant>();

    public ResponsableFamille(String nomFamille, String prenom, String mail, float ressources) {
        super(nomFamille, prenom, mail);
        this.ressources = ressources;
    }

    public float getRessources() {
        return ressources;
    }

    public List<Enfant> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Enfant> enfants) {
        this.enfants = enfants;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResponsableFamille) {
            ResponsableFamille autreResp = (ResponsableFamille) obj;
            if (getNomFamille().equals(autreResp.getNomFamille())
                    && getPrenom().equals(autreResp.getPrenom())) {
                return true;
            }
        }
        return false;
    }

}
