package projetPFE.example.monProjet.interfac;

import org.springframework.web.multipart.MultipartFile;
import projetPFE.example.monProjet.DTO.DocumentDto;

import java.util.List;

public interface DocumentInter {
    DocumentDto getById(Integer id);

    List<DocumentDto> getAll();

   // DocumentDto ajouterDocument(MultipartFile file, DocumentDto documentDto);
   DocumentDto ajouterDocument(MultipartFile file, DocumentDto documentDto);

    DocumentDto modifierDocument(DocumentDto documentDto);

    void supprimerDocument(Integer id);
}
