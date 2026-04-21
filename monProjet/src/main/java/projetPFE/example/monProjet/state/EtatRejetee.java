package projetPFE.example.monProjet.state;

import projetPFE.example.monProjet.model.Demande;

public class EtatRejetee implements DemandeState {
    @Override
    public void valider(Demande demande) {
        throw new IllegalStateException("Impossible de valider une demande rejetée ou annulée.");
    }

    @Override
    public void rejeter(Demande demande) {
        throw new IllegalStateException("La demande est déjà rejetée.");
    }

    @Override
    public void annuler(Demande demande) {
        throw new IllegalStateException("La demande est déjà annulée.");
    }

    @Override
    public void supprimer(Demande demande) {
        System.out.println("Action : Demande rejetée supprimée.");
    }
}
