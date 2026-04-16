package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.List;
import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur>findByEmail(String email);
    List<Utilisateur> findByIdRole(Role idRole);

   // long countByIdRole(int roleId);
  // long countByIdRole(int idRole);

   // long countByIdRole(Role role);
 //  long countByIdRole(Integer idRole);
    long countByIdRole_IdRole(Integer idRole);


}