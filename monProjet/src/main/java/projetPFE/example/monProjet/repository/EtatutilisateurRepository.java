package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Etatutilisateur;

import java.util.Optional;

public interface EtatutilisateurRepository extends JpaRepository<Etatutilisateur, Integer> {
    Optional<Etatutilisateur> findByLabelleetatutilisateur(String labelleetatutilisateur);
}