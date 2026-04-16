package projetPFE.example.monProjet.DTOmapper;

import projetPFE.example.monProjet.DTO.Association10Dto;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Panier;
import projetPFE.example.monProjet.DTO.PanierDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
public class PanierDtoMapper {

    public static PanierDto toDto(Panier panier) {
        if (panier == null) {
            return null;
        }
        return new PanierDto(
                panier.getIdPanier()
        );
    }

    public static Panier toEntity(PanierDto panierDto) {
        if (panierDto == null) {
            return null;
        }
        Panier panier = new Panier();
        panier.setIdPanier(panierDto.getIdPanier());
        return panier;
    }

}
