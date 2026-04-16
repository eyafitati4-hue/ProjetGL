package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Marque;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {
}