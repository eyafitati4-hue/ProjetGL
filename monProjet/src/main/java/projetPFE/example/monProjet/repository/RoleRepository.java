package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}