package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.RoleDto;
import projetPFE.example.monProjet.DTOmapper.RoleDtoMapper;
import projetPFE.example.monProjet.interfac.RoleInter;
import projetPFE.example.monProjet.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Role implements RoleInter {

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public RoleDto getById(Integer id) {
        Optional<projetPFE.example.monProjet.model.Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(RoleDtoMapper::toDto).orElse(null);
    }

    @Override
    public List<RoleDto> getAll() {
        List<projetPFE.example.monProjet.model.Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto ajouterRole(RoleDto roleDto) {
        projetPFE.example.monProjet.model.Role role = RoleDtoMapper.toEntity(roleDto);
        if (role.getIdRole() != null && roleRepository.existsById(role.getIdRole())) {
            // Si le rôle avec cet ID existe déjà, vous pouvez renvoyer une erreur ou gérer ce cas selon vos besoins
            throw new IllegalArgumentException("Un rôle avec cet ID existe déjà.");
        }
        return RoleDtoMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleDto modifierRole(RoleDto roleDto) {
        projetPFE.example.monProjet.model.Role role = RoleDtoMapper.toEntity(roleDto);
        if (roleRepository.existsById(role.getIdRole())) {
            return RoleDtoMapper.toDto(roleRepository.save(role));
        } else {
            // Si le rôle n'existe pas, vous pouvez renvoyer une erreur ou gérer ce cas selon vos besoins
            throw new IllegalArgumentException("Le rôle que vous essayez de modifier n'existe pas.");
        }
    }

    @Override
    public void supprimerRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
