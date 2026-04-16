package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.DTO.RoleDto;

import java.util.List;

public interface RoleInter {
    RoleDto getById(Integer id);

    List<RoleDto> getAll();

    RoleDto ajouterRole(RoleDto roleDto);

    RoleDto modifierRole(RoleDto roleDto);

    void supprimerRole(Integer id);
}

