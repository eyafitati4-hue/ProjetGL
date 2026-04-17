package projetPFE.example.monProjet.service;


import org.springframework.beans.factory.annotation.Autowired;
import projetPFE.example.monProjet.service.SimulerService;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.DTOmapper.EtatProduitDtoMapper;
import projetPFE.example.monProjet.DTOmapper.ProduitDtoMapper;
import projetPFE.example.monProjet.interfac.ProduitService;
import projetPFE.example.monProjet.model.Etatproduit;
import projetPFE.example.monProjet.repository.EtatproduitRepository;
import projetPFE.example.monProjet.repository.ProduitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProduitServiceImpl implements ProduitService {
    @Autowired
    private ProduitRepository produitRepository;
    private EtatproduitRepository etatproduitRepository;

    @Autowired
    private SimulerService simulerService;

    @Override
    public ProduitDto getById(Integer id) {
        Optional<projetPFE.example.monProjet.model.Produit> optionalProduit = produitRepository.findById(id);
        return optionalProduit.map(ProduitDtoMapper::toDto).orElse(null);
    }

    @Override
    public List<ProduitDto> getAll() {
        List<projetPFE.example.monProjet.model.Produit> produits = produitRepository.findAll();
        return produits.stream()
                .map(ProduitDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProduitDto> getProduitsParMarque(Integer idMarque) {
        List<projetPFE.example.monProjet.model.Produit> produits = produitRepository.findByIdmarqueIdMarque(idMarque);
        List<ProduitDto> produitsDto = new ArrayList<>();
        for (projetPFE.example.monProjet.model.Produit produit : produits) {
            ProduitDto produitDto = ProduitDtoMapper.toDto(produit); // Conversion de Produit en ProduitDto
            produitsDto.add(produitDto);
        }
        return produitsDto;
    }



    @Override
    public ProduitDto ajouterProduit(ProduitDto produitDto) {
        projetPFE.example.monProjet.model.Produit produit = ProduitDtoMapper.toEntity(produitDto);
        double apportPropre = simulerService.calculerApportPropre(produit.getPrixproduit());
        produit.setApportpropre(apportPropre);

        double loyer = simulerService.calculerLoyerMensuel(produit,7,produit.getApportpropre());
        produit.setLoyer(loyer);

        projetPFE.example.monProjet.model.Produit produitEnregistre = produitRepository.save(produit);
        return ProduitDtoMapper.toDto(produitEnregistre);
    }

  @Override
  public ProduitDto modifierProduit(ProduitDto produitDto , Integer id) {
      projetPFE.example.monProjet.model.Produit produit = ProduitDtoMapper.toEntity(produitDto);
      Optional<projetPFE.example.monProjet.model.Produit> existingProductOptional = produitRepository.findById(id);
      if (existingProductOptional.isPresent()) {
          projetPFE.example.monProjet.model.Produit existingProduct = existingProductOptional.get();
          existingProduct.setModele(produit.getModele());
          existingProduct.setKilometrage(produit.getKilometrage());
          existingProduct.setPrixproduit(produit.getPrixproduit());
          existingProduct.setDescription(produit.getDescription());
          double nouvelApportPropre = simulerService.calculerApportPropre(produit.getPrixproduit()
          );
          existingProduct.setApportpropre(nouvelApportPropre);
          double nouveauLoyer = simulerService.modifierLoyerMensuel(7, existingProduct,produit.getApportpropre()); // Utilisez le nombre d'années approprié
          existingProduct = produitRepository.save(existingProduct);
          return ProduitDtoMapper.toDto(existingProduct);
      } else {
          return null;
      }
  }



    @Override
    public void supprimerProduit(Integer id) {
        produitRepository.deleteById(id);
    }

    public ProduitDto modifierEtatProduit(Integer id, ProduitDto newProduitDto) {
        Optional<projetPFE.example.monProjet.model.Produit> oldProduitOptional = produitRepository.findById(id);
        if (oldProduitOptional.isPresent()) {
            projetPFE.example.monProjet.model.Produit oldProduit = oldProduitOptional.get();
            if (newProduitDto.getEtatproduit() != null) {
                oldProduit.setEtatproduit(EtatProduitDtoMapper.toEntity(newProduitDto.getEtatproduit()));

                produitRepository.save(oldProduit);

                return ProduitDtoMapper.toDto(oldProduit);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Integer getCountByEtatProduit(Integer etatId) {
        return produitRepository.getCountByEtatProduit(etatId);
    }

}






