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
        return Marque.builder()
                .idMarque(marqueDto.getIdMarque())
                .nommarque(marqueDto.getNommarque())
                .logomarque(marqueDto.getLogomarque())
                .etatmarque(marqueDto.getEtatmarque())
                .build();
    }

}
