package projetPFE.example.monProjet.DTOmapper;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.EtatutilisateurDto;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.service.EtatUtilisateur;

import java.util.HashSet;
import java.util.Set;

@Component
public class EtatUtilisateurDtoMapper {


    public static EtatutilisateurDto toDto(Etatutilisateur etatUtilisateur) {
        if (etatUtilisateur == null) {
            return null;
        }
        EtatutilisateurDto etatutilisateurDto = new EtatutilisateurDto();
        etatutilisateurDto.setIdEtat(etatUtilisateur.getIdEtat());
        etatutilisateurDto.setLabelleetatutilisateur(etatUtilisateur.getLabelleetatutilisateur());
        // Ne pas mapper la liste des utilisateurs pour éviter une boucle infinie dans la sérialisation JSON
        return etatutilisateurDto;
    }

    public static Etatutilisateur toEntity(EtatutilisateurDto etatUtilisateurDto) {
        if (etatUtilisateurDto == null) {
            return null;
        }
        Etatutilisateur etatutilisateur = new Etatutilisateur();
        etatutilisateur.setIdEtat(etatUtilisateurDto.getIdEtat());
        etatutilisateur.setLabelleetatutilisateur(etatUtilisateurDto.getLabelleetatutilisateur());
        // Ne pas mapper la liste des utilisateurs pour éviter une boucle infinie dans la désérialisation JSON
        return etatutilisateur;
    }


    public static Set<EtatutilisateurDto> toDtoList(Set<Etatutilisateur> entityList) {
        Set<EtatutilisateurDto> dtoList = new HashSet<>();
        for (Etatutilisateur entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }
}
