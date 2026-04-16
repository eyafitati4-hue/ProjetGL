package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.model.Etatdemande;

import java.util.List;
public interface EtatdemandeInter {
    Etatdemande getById(Integer id);
    List<Etatdemande> getAll();
    Etatdemande ajouterEtatdemande(Etatdemande etatdemande);
    Etatdemande modifierEtatdemande(Etatdemande etatdemande);
    void supprimerEtatdemande(Integer id);
}
