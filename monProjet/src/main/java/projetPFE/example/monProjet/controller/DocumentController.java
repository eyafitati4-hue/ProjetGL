package projetPFE.example.monProjet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projetPFE.example.monProjet.DTO.DocumentDto;
import projetPFE.example.monProjet.interfac.DocumentInter;
import projetPFE.example.monProjet.model.Document;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "*") // Autoriser les requêtes CORS depuis http://localhost:4200

public class DocumentController {

    @Autowired
    private DocumentInter documentService;

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getById(@PathVariable Integer id) {
        DocumentDto document = documentService.getById(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DocumentDto>> getAll() {
        List<DocumentDto> documents = documentService.getAll();
        return ResponseEntity.ok(documents);
    }

    /*@PostMapping("/add")
    public ResponseEntity<DocumentDto> ajouterDocument(@RequestBody DocumentDto documentDto) {
        DocumentDto newDocument = documentService.ajouterDocument(documentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDocument);
    }*/
    @PostMapping("/add")
    public ResponseEntity<DocumentDto> ajouterDocument(@RequestParam("file") MultipartFile file, @ModelAttribute DocumentDto documentDto) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Récupérez les données binaires du fichier
            byte[] fichierBytes = file.getBytes();

            // Ajoutez les données binaires du fichier au DTO
            documentDto.setFichier(fichierBytes);

            // Appelez le service pour ajouter le document
            DocumentDto newDocument = documentService.ajouterDocument(file, documentDto);

            // Retournez une réponse appropriée
            return ResponseEntity.status(HttpStatus.CREATED).body(newDocument);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentDto> modifierDocument(@PathVariable Integer id, @RequestBody DocumentDto documentDto) {
        documentDto.setIdDocument(id);
        DocumentDto updatedDocument = documentService.modifierDocument(documentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerDocument(@PathVariable Integer id) {
        documentService.supprimerDocument(id);
        return ResponseEntity.noContent().build();
    }
}
