package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.EtatutilisateurDto;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.service.EtatUtilisateur;

import java.util.Set;

@RestController
@RequestMapping("/etats-utilisateur")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes CORS depuis http://localhost:4200

public class EtatutilisateurController {

    @Autowired
    private EtatUtilisateur etatutilisateurService;

    @GetMapping("/{id}")
    public ResponseEntity<EtatutilisateurDto> getById(@PathVariable Integer id) {
        EtatutilisateurDto etatUtilisateurDto = etatutilisateurService.getById(id);
        if (etatUtilisateurDto != null) {
            return new ResponseEntity<>(etatUtilisateurDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Set<EtatutilisateurDto>> getAll() {
        Set<EtatutilisateurDto> etatsUtilisateurDto = etatutilisateurService.getAll();
        return new ResponseEntity<>(etatsUtilisateurDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EtatutilisateurDto> ajouterEtatutilisateur(@RequestBody Etatutilisateur etatUtilisateurDto) {
        EtatutilisateurDto newEtatUtilisateurDto = etatutilisateurService.ajouterEtatutilisateur(etatUtilisateurDto);
        return new ResponseEntity<>(newEtatUtilisateurDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EtatutilisateurDto> modifierEtatutilisateur(@PathVariable Integer id, @RequestBody Etatutilisateur etatUtilisateur) {
        etatUtilisateur.setIdEtat(id);
        EtatutilisateurDto updatedEtatUtilisateurDto = etatutilisateurService.modifierEtatutilisateur(etatUtilisateur);
        if (updatedEtatUtilisateurDto != null) {
            return new ResponseEntity<>(updatedEtatUtilisateurDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerEtatutilisateur(@PathVariable Integer id) {
        etatutilisateurService.supprimerEtatutilisateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
