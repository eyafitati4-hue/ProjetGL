package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.interfac.EtatdemandeInter;
import projetPFE.example.monProjet.model.Etatdemande;

import java.util.List;

@RestController
@RequestMapping("/etat-demande")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes CORS depuis http://localhost:4200

public class EtatdemandeController {

    @Autowired
    private EtatdemandeInter etatDemandeService;

    @GetMapping("/{id}")
    public ResponseEntity<Etatdemande> getById(@PathVariable Integer id) {
        Etatdemande etatDemande = etatDemandeService.getById(id);
        return ResponseEntity.ok(etatDemande);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Etatdemande>> getAll() {
        List<Etatdemande> etatsDemande = etatDemandeService.getAll();
        return ResponseEntity.ok(etatsDemande);
    }

    @PostMapping("/add")
    public ResponseEntity<Etatdemande> ajouterEtatDemande(@RequestBody Etatdemande etatDemande) {
        Etatdemande newEtatDemande = etatDemandeService.ajouterEtatdemande(etatDemande);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEtatDemande);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Etatdemande> modifierEtatDemande(@PathVariable Integer id, @RequestBody Etatdemande etatDemande) {
        etatDemande.setIdetatdemande(id);
        Etatdemande updatedEtatDemande = etatDemandeService.modifierEtatdemande(etatDemande);
        return ResponseEntity.ok(updatedEtatDemande);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerEtatDemande(@PathVariable Integer id) {
        etatDemandeService.supprimerEtatdemande(id);
        return ResponseEntity.noContent().build();
    }
}
