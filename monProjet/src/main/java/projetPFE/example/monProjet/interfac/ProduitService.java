package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.model.Produit;

import java.util.List;
public interface ProduitService {
    ProduitDto getById(Integer id);

    List<ProduitDto> getAll();
    List<ProduitDto> getProduitsParMarque(Integer idMarque);
    ProduitDto modifierEtatProduit(Integer id, ProduitDto newProduitDto);

    ProduitDto ajouterProduit(ProduitDto produitDto);


    ProduitDto modifierProduit(ProduitDto produitDto, Integer id);

    void supprimerProduit(Integer id);
    Integer getCountByEtatProduit(Integer etatId);

}
