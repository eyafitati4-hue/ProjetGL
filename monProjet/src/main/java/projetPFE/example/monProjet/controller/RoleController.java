package projetPFE.example.monProjet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.RoleDto;
import projetPFE.example.monProjet.service.Role;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private Role roleService;

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/all")
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @PostMapping("/add")
    public RoleDto ajouterRole(@RequestBody RoleDto roleDto) {
        return roleService.ajouterRole(roleDto);
    }

    @PutMapping("/update/{id}")
    public RoleDto modifierRole(@PathVariable Integer id, @RequestBody RoleDto roleDto) {
        roleDto.setIdRole(id);
        return roleService.modifierRole(roleDto);
    }




    @DeleteMapping("/{id}")
    public void supprimerRole(@PathVariable Integer id) {
        roleService.supprimerRole(id);
    }
}