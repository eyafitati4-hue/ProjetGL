package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Etatdemande;

public interface EtatdemandeRepository extends JpaRepository<Etatdemande, Integer> {
}