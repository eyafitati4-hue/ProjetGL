package projetPFE.example.monProjet.builder;

import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.model.Produit;

/**
 * Interface Builder selon le diagramme GoF.
 * Définit les étapes de construction (BuildPart).
 */
public interface IProduitBuilder {
    IProduitBuilder buildIdentite(ProduitDto dto);
    IProduitBuilder buildCaracteristiques(ProduitDto dto);
    IProduitBuilder buildFinancesEtStock(ProduitDto dto);
    Produit getResult(); // Équivalent à GetResult() dans le diagramme
}
