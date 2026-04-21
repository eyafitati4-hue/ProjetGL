package projetPFE.example.monProjet.state;

import projetPFE.example.monProjet.model.Demande;

/**
 * Interface définissant le comportement d'un état de demande.
 * GOF - Patron State.
 */
public interface DemandeState {
    void valider(Demande demande);
    void rejeter(Demande demande);
    void annuler(Demande demande);
    void supprimer(Demande demande);
}
