package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.PanierDto;
import projetPFE.example.monProjet.DTOmapper.PanierDtoMapper;
import projetPFE.example.monProjet.interfac.PanierInter;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Association10Id;
import projetPFE.example.monProjet.model.Produit;
import projetPFE.example.monProjet.repository.Association10Repository;
import projetPFE.example.monProjet.repository.PanierRepository;
import projetPFE.example.monProjet.repository.ProduitRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Panier  implements PanierInter {

    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    Association10Repository  associationRepository;

    @Override
    public PanierDto getById(Integer id) {
        Optional<projetPFE.example.monProjet.model.Panier> optionalPanier = panierRepository.findById(id);
        return optionalPanier.map(PanierDtoMapper::toDto).orElse(null);
    }

    @Override
    public List<PanierDto> getAll() {
        List<projetPFE.example.monProjet.model.Panier> paniers = panierRepository.findAll();
        return paniers.stream()
                .map(PanierDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PanierDto ajouterPanier(PanierDto panierDto) {
        projetPFE.example.monProjet.model.Panier panier = PanierDtoMapper.toEntity(panierDto);
        return PanierDtoMapper.toDto(panierRepository.save(panier));
    }

    @Override
    public PanierDto modifierPanier(PanierDto panierDto) {
        projetPFE.example.monProjet.model.Panier panier = PanierDtoMapper.toEntity(panierDto);
        return PanierDtoMapper.toDto(panierRepository.save(panier));
    }

    @Override
    public void supprimerPanier(Integer id) {
        panierRepository.deleteById(id);
    }

    // --- Implémentation GRASP: Fabrication Pure ---
    // Regroupement de la logique de gestion du panier et calcul des totaux

    @Override
    public void ajouterProduitAuPanier(Integer idProduit, Integer idPanier) {
        // On vérifie l'existence du produit et du panier
        if (!produitRepository.existsById(idProduit)) {
            throw new RuntimeException("Produit non trouvé : " + idProduit);
        }
        if (!panierRepository.existsById(idPanier)) {
            throw new RuntimeException("Panier non trouvé : " + idPanier);
        }

        Association10Id idAssoc = new Association10Id();
        idAssoc.setIdPanier(idPanier);
        idAssoc.setIdProduit(idProduit);

        if (!associationRepository.existsById(idAssoc)) {
            Association10 association = new Association10();
            association.setId(idAssoc);
            associationRepository.save(association);
        }
    }

    @Override
    public double calculerTotalHT(Integer idPanier) {
        // On récupère toutes les lignes d'association pour ce panier
        List<Association10> lignesPanier = associationRepository.findById_IdPanier(idPanier);
        
        double total = 0.0;
        for (Association10 ligne : lignesPanier) {
            // On récupère le prix du produit associé
            Optional<projetPFE.example.monProjet.model.Produit> produit = produitRepository.findById(ligne.getId().getIdProduit());
            if (produit.isPresent()) {
                total += produit.get().getPrixproduit();
            }
        }
        return total;
    }
}
