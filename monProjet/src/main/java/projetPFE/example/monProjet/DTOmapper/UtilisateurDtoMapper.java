package projetPFE.example.monProjet.DTOmapper;


import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Component
@Builder
public class UtilisateurDtoMapper {




     public static UtilisateurDto toDto(Utilisateur utilisateur){
      //   Role role = utilisateur.getIdRole(); // Charger explicitement l'objet Role
        return UtilisateurDto.builder()
                .idutilisateur(utilisateur.getIdutilisateur())
                .nomutilisateur(utilisateur.getNomutilisateur())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .motdepasse(utilisateur.getMotdepasse())
                .adresse(utilisateur.getAdresse())
                .ville(utilisateur.getVille())
                .codepostal(utilisateur.getCodepostal())
                .datenaissance(utilisateur.getDatenaissance())
                .idRole(RoleDtoMapper.toDto(utilisateur.getIdRole()))
                .idEtat(EtatUtilisateurDtoMapper.toDto(utilisateur.getIdEtat()))
                //.produits(utilisateur.getProduits())
                .build();

    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdutilisateur(utilisateurDto.getIdutilisateur());
        utilisateur.setNomutilisateur(utilisateurDto.getNomutilisateur());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setMotdepasse(utilisateurDto.getMotdepasse());
        utilisateur.setAdresse(utilisateurDto.getAdresse());
        utilisateur.setVille(utilisateurDto.getVille());
        utilisateur.setCodepostal(utilisateurDto.getCodepostal());
        utilisateur.setDatenaissance(utilisateurDto.getDatenaissance());
        utilisateur.setIdEtat(EtatUtilisateurDtoMapper.toEntity(utilisateurDto.getIdEtat())); // Mapper idRole
       // utilisateur.setProduits(utilisateurDto.getProduits());
        utilisateur.setIdRole(RoleDtoMapper.toEntity(utilisateurDto.getIdRole())); // Mapper idRole
        return utilisateur;
    }

    public static Set<UtilisateurDto> toDtoList(HashSet<Utilisateur> utilisateurs) {
        Set<UtilisateurDto> dtoSet = new HashSet<>();
        for (Utilisateur utilisateur : utilisateurs) {
            dtoSet.add(toDto(utilisateur));
        }
        return dtoSet;
    }



}

