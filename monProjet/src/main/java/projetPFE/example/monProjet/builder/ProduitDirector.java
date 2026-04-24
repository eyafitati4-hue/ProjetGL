package projetPFE.example.monProjet.builder;

import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.model.Produit;

/**
 * Director selon le diagramme GoF.
 * Orchestre la construction de l'objet Produit.
 */
public class ProduitDirector {
    private final IProduitBuilder builder;

    public ProduitDirector(IProduitBuilder builder) {
        this.builder = builder;
    }

    /**
     * Correspond à la méthode Construct() du diagramme.
     * Elle définit la séquence de fabrication de l'objet.
     */
    public Produit construct(ProduitDto dto) {
        return builder.buildIdentite(dto)
                      .buildCaracteristiques(dto)
                      .buildFinancesEtStock(dto)
                      .getResult();
    }
}
