package projetPFE.example.monProjet.DTOmapper;

import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.RoleDto;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class RoleDtoMapper {


    public static RoleDto toDto(Role role) {
        if (role == null) {
        return null;
    }
        RoleDto roleDto = new RoleDto();
        roleDto.setIdRole(role.getIdRole());
        roleDto.setLabelrole(role.getLabelrole()); // Mapper idRole
        return roleDto;
    }


    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        Role role = new Role();
        role.setIdRole(roleDto.getIdRole());
        role.setLabelrole(roleDto.getLabelrole()); // Mapper idRole
        return role;
    }

}




