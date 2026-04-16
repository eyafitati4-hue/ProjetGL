package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Panier;

public interface PanierRepository extends JpaRepository<Panier, Integer> {
}