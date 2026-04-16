package projetPFE.example.monProjet.DTOmapper;

import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.MarqueDto;
import projetPFE.example.monProjet.model.Marque;

import java.util.stream.Collectors;

@Component
public class MarqueDtoMapper {
    public static MarqueDto toDto(Marque marque) {
        if (marque == null) {
            return null;
        }
        return new MarqueDto(
                marque.getIdMarque(),
                marque.getNommarque(),
                marque.getLogomarque(),
                marque.getEtatmarque(),
               // marque.getProduits().stream().map(ProduitDtoMapper::toDto).collect(Collectors.toSet())
                null

        );
    }

    public static Marque toEntity(MarqueDto marqueDto) {
        if (marqueDto == null) {
            return null;
        }
        Marque marque = new Marque();
        marque.setIdMarque(marqueDto.getIdMarque());
        marque.setNommarque(marqueDto.getNommarque());
        marque.setLogomarque(marqueDto.getLogomarque());
        marque.setEtatmarque(marqueDto.getEtatmarque());
        // Vous devrez ajouter la logique pour mapper les produits ici si nécessaire
        return marque;
    }
}
