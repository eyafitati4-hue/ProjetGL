package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Etatutilisateur;

public interface EtatutilisateurRepository extends JpaRepository<Etatutilisateur, Integer> {
}