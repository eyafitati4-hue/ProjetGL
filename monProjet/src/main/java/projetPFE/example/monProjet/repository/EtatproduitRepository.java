package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Etatproduit;

public interface EtatproduitRepository extends JpaRepository<Etatproduit, Integer> {
}