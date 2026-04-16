package projetPFE.example.monProjet.DTOmapper;

import projetPFE.example.monProjet.DTO.DeviDto;
import projetPFE.example.monProjet.model.Devi;

import java.util.stream.Collectors;

public class DeviDtoMapper {

    public static DeviDto toDto(Devi devi) {
        return DeviDto.builder()
                .idDevis(devi.getIdDevis())
                .datedevis(devi.getDatedevis())
               // .demandes(devi.getDemandes().stream()
                        //.map(DemandeDtoMapper::toDto) // Utilisation correcte de la référence de méthode
                        //.collect(Collectors.toSet()))
                .build();
    }

    public static Devi toEntity(DeviDto deviDto) {
        Devi devi = new Devi();
        devi.setIdDevis(deviDto.getIdDevis());
        devi.setDatedevis(deviDto.getDatedevis());
       // devi.setDemandes(deviDto.getDemandes().stream()
              // .map(DemandeDtoMapper::toEntity) // Utilisation correcte de la référence de méthode
              //  .collect(Collectors.toSet()));
        return devi;
    }
}
