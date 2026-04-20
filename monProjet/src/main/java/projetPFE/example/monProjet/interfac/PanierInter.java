package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.DTO.PanierDto;
import projetPFE.example.monProjet.model.Panier;

import java.util.List;
public interface PanierInter {
    PanierDto getById(Integer id);

    List<PanierDto> getAll();

    PanierDto ajouterPanier(PanierDto panierDto);

    PanierDto modifierPanier(PanierDto panierDto);

    void supprimerPanier(Integer id);

    // Méthodes de Fabrication Pure pour regrouper la logique
    void ajouterProduitAuPanier(Integer idProduit, Integer idPanier);
    double calculerTotalHT(Integer idPanier);
}
