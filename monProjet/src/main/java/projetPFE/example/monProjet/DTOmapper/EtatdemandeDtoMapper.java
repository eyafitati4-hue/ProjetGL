package projetPFE.example.monProjet.DTOmapper;

import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.EtatdemandeDto;
import projetPFE.example.monProjet.model.Etatdemande;
@Component
public class EtatdemandeDtoMapper {
    public static EtatdemandeDto toDto(Etatdemande etatdemande) {
        return EtatdemandeDto.builder()
                .idetatdemande(etatdemande.getIdetatdemande())
                .labelleetatdemande(etatdemande.getLabelleetatdemande())
                // Vous pouvez ajouter des attributs supplémentaires ici si nécessaire
                .build();
    }

    public static Etatdemande toEntity(EtatdemandeDto etatdemandeDto) {
        Etatdemande etatdemande = new Etatdemande();
        etatdemande.setIdetatdemande(etatdemandeDto.getIdetatdemande());
        etatdemande.setLabelleetatdemande(etatdemandeDto.getLabelleetatdemande());
        // Vous pouvez ajouter d'autres attributs de l'entité ici si nécessaire
        return etatdemande;
    }
}
