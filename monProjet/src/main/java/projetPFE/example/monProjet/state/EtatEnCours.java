package projetPFE.example.monProjet.state;

import projetPFE.example.monProjet.model.Demande;

public class EtatEnCours implements DemandeState {
    @Override
    public void valider(Demande demande) {
        demande.setState(new EtatValidee());
        System.out.println("Transition : En Cours -> Validée");
    }

    @Override
    public void rejeter(Demande demande) {
        demande.setState(new EtatRejetee());
        System.out.println("Transition : En Cours -> Rejetée");
    }

    @Override
    public void annuler(Demande demande) {
        demande.setState(new EtatRejetee());
        System.out.println("Transition : En Cours -> Annulée/Rejetée");
    }

    @Override
    public void supprimer(Demande demande) {
        throw new IllegalStateException("Impossible de supprimer une demande en cours de validation finale.");
    }
}
