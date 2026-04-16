package projetPFE.example.monProjet.DTOmapper;
import projetPFE.example.monProjet.model.Association10Id;
import projetPFE.example.monProjet.DTO.Association10IdDto;
public class Association10IdDtoDtoMapper {

    public static Association10IdDto toDto(Association10Id association10Id) {
        if (association10Id == null) {
            return null;
        }

        return new Association10IdDto(
                association10Id.getIdProduit(),
                association10Id.getIdPanier()
        );
    }

    public static Association10Id toEntity(Association10IdDto association10IdDto) {
        if (association10IdDto == null) {
            return null;
        }

        Association10Id association10Id = new Association10Id();
        association10Id.setIdProduit(association10IdDto.getIdProduit());
        association10Id.setIdPanier(association10IdDto.getIdPanier());

        return association10Id;
    }
}
