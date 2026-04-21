package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.model.Devi;
import java.util.List;

public interface DeviInter {
    Devi getById(Integer id);
    List<Devi> getAll();
    Devi ajouterDevi(Devi devis);
    Devi modifierDevi(Devi devis);
    void supprimerDevi(Integer id);
    Devi ajouterDeviAvecOptions(Devi devi, boolean assurance, boolean frais); // ← ajouter
}