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


    //ajout pour le panier







}
