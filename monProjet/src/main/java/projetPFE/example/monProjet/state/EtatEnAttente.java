package projetPFE.example.monProjet.state;

import projetPFE.example.monProjet.model.Demande;

public class EtatEnAttente implements DemandeState {
    @Override
    public void valider(Demande demande) {
        demande.setState(new EtatEnCours());
        System.out.println("Transition : En Attente -> En Cours de Validation");
    }

    @Override
    public void rejeter(Demande demande) {
        throw new IllegalStateException("Une demande en attente ne peut pas être rejetée directement. Elle doit être examinée.");
    }

    @Override
    public void annuler(Demande demande) {
        demande.setState(new EtatRejetee()); // On considère l'annulation comme un rejet par l'utilisateur
        System.out.println("Transition : En Attente -> Annulée/Rejetée");
    }

    @Override
    public void supprimer(Demande demande) {
        System.out.println("Action : Demande supprimée.");
    }
}
