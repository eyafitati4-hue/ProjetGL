package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByLabelrole(String labelrole);
}