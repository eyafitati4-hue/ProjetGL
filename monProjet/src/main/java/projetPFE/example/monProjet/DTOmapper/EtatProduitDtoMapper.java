package projetPFE.example.monProjet.DTOmapper;

import projetPFE.example.monProjet.DTO.EtatproduitDto;
import projetPFE.example.monProjet.model.Etatproduit;

public class EtatProduitDtoMapper {
    public static EtatproduitDto toDto (Etatproduit etatproduit){
        return EtatproduitDto.builder()
                .idetatproduit(etatproduit.getIdetatproduit())
                .labelleetatproduit(etatproduit.getLabelleetatproduit())
                .build();
    }
    public static Etatproduit toEntity (EtatproduitDto etatproduit){
        return Etatproduit.builder()
                .idetatproduit(etatproduit.getIdetatproduit())
                .labelleetatproduit(etatproduit.getLabelleetatproduit())
                .build();
    }
}
