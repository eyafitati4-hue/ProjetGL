package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPFE.example.monProjet.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}