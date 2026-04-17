package projetPFE.example.monProjet.DTOmapper;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Etatproduit;
import projetPFE.example.monProjet.model.Produit;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class ProduitDtoMapper {


    public static ProduitDto toDto(Produit produit) {
        return  ProduitDto.builder()
                .idProduit(produit.getIdProduit())
                .modele(produit.getModele())
                .kilometrage(produit.getKilometrage())
                .prixproduit(produit.getPrixproduit())
                .description(produit.getDescription())
                .etatproduit(EtatProduitDtoMapper.toDto(produit.getEtatproduit()))
                .idmarque(MarqueDtoMapper.toDto(produit.getIdmarque()))
                .idutilisateur(UtilisateurDtoMapper.toDto(produit.getUtilisateur()))
                .image(produit.getImage())
                .quantite(produit.getQuantite())
                .nomproduit(produit.getNomproduit())
                .apportpropre(produit.getApportpropre())
                .loyer(produit.getLoyer())
                .nbplaces(produit.getNbplaces())
                .carrosserie((produit.getCarrosserie()))
                .nbportes(produit.getNbportes())
                .disponibilite(produit.getDisponibilite())
                .garantie(produit.getGarantie())
                .build();

    }



    public static Produit toEntity(ProduitDto produitDto) {
        if (produitDto == null) {
            return null;
        }
        return Produit.builder()
                .idProduit(produitDto.getIdProduit())
                .idmarque(MarqueDtoMapper.toEntity(produitDto.getIdmarque()))
                .etatproduit(EtatProduitDtoMapper.toEntity(produitDto.getEtatproduit()))
                .utilisateur(UtilisateurDtoMapper.toEntity(produitDto.getIdutilisateur()))
                .modele(produitDto.getModele())
                .kilometrage(produitDto.getKilometrage())
                .prixproduit(produitDto.getPrixproduit())
                .description(produitDto.getDescription())
                .image(produitDto.getImage())
                .quantite(produitDto.getQuantite())
                .nomproduit(produitDto.getNomproduit())
                .apportpropre(produitDto.getApportpropre())
                .loyer(produitDto.getLoyer())
                .nbplaces(produitDto.getNbplaces())
                .carrosserie(produitDto.getCarrosserie())
                .nbportes(produitDto.getNbportes())
                .disponibilite(produitDto.getDisponibilite())
                .garantie(produitDto.getGarantie())
                .build();
    }


    public static Set<ProduitDto> toDtoSet(Set<Produit> produits) {
        Set<ProduitDto> dtoSet = new HashSet<>();
        for (Produit produit : produits) {
            dtoSet.add(toDto(produit));
        }
        return dtoSet;
    }
    public static Set<Produit> toEntiySet(Set<ProduitDto> produits) {
        Set<Produit> entitySet = new HashSet<>();
        for (ProduitDto produit : produits) {
            entitySet.add(toEntity(produit));
        }
        return entitySet;
    }


}
