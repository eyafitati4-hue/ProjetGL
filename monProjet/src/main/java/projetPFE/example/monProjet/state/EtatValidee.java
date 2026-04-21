package projetPFE.example.monProjet.state;

import projetPFE.example.monProjet.model.Demande;

public class EtatValidee implements DemandeState {
    @Override
    public void valider(Demande demande) {
        throw new IllegalStateException("La demande est déjà validée.");
    }

    @Override
    public void rejeter(Demande demande) {
        throw new IllegalStateException("Impossible de rejeter une demande déjà validée.");
    }

    @Override
    public void annuler(Demande demande) {
        throw new IllegalStateException("Impossible d'annuler une demande déjà validée.");
    }

    @Override
    public void supprimer(Demande demande) {
        throw new IllegalStateException("Interdiction : Une demande validée ne peut plus être supprimée.");
    }
}
