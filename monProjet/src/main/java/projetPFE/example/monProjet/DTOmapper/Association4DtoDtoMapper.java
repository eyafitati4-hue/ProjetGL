package projetPFE.example.monProjet.DTOmapper;

import projetPFE.example.monProjet.DTO.Association4IdDto;
import projetPFE.example.monProjet.model.Association4Id;

public class Association4DtoDtoMapper {
    public static Association4IdDto toDto(Association4Id association4Id) {
        if (association4Id == null) {
            return null;
        }

        return new Association4IdDto(
                association4Id.getIddocument(),
                association4Id.getIddemande()
        );
    }

    public static Association4Id toEntity(Association4IdDto association4IdDto) {
        if (association4IdDto == null) {
            return null;
        }

        Association4Id association4Id = new Association4Id();
        association4Id.setIddocument(association4IdDto.getIdDocument());
        association4Id.setIddemande(association4IdDto.getIdDemande());

        return association4Id;
    }



}
