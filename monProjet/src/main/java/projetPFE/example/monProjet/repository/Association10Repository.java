package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Association10Id;

import java.util.List;

public interface Association10Repository extends JpaRepository<Association10, Association10Id> {
    List<Association10> findById_IdPanier(Integer idPanier);
}