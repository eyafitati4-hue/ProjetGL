package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projetPFE.example.monProjet.model.Association4;
import projetPFE.example.monProjet.model.Association4Id;
import projetPFE.example.monProjet.service.Associationdd;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/associationdd")
@CrossOrigin(origins = "*")
public class AssociationddController {

    @Autowired
    private Associationdd associationdd;


    @PostMapping("/ajouterDocumentALaDemande/{idDocument}/{idDemande}")
    public ResponseEntity<Void> ajouterDocument(@PathVariable("idDocument") Integer idDocument,
                                                @PathVariable("idDemande") Integer idDemande,
                                                @RequestParam("file") MultipartFile file) {
        associationdd.ajouterDocumentALaDemande(idDocument, idDemande, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @GetMapping("/associationsByDemandeId/{idDemande}")
    public ResponseEntity<List<Association4>> getAssociationsByDemandeId(@PathVariable Integer idDemande) {
        try {
            List<Association4> associations = associationdd.getAssociationsByDemandeId(idDemande);
            return ResponseEntity.ok(associations);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
