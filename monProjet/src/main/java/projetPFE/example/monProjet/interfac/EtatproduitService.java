package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.model.Etatproduit;

import java.util.List;

public interface EtatproduitService {
    Etatproduit getById(Integer id);
    List<Etatproduit> getAll();
    Etatproduit ajouterEtatproduit(Etatproduit etatproduit);
    Etatproduit modifierEtatproduit(Etatproduit etatproduit);
    void supprimerEtatproduit(Integer id);
}