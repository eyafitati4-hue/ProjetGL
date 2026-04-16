package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Devi;

public interface DeviRepository extends JpaRepository<Devi, Integer> {
}