package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.interfac.DeviInter;
import projetPFE.example.monProjet.model.Devi;

import java.util.List;

@RestController
@RequestMapping("/devis")
@CrossOrigin(origins = "*") // Autoriser les requêtes CORS depuis http://localhost:4200

public class DeviController {

    @Autowired
    private DeviInter deviService;

    @GetMapping("/{id}")
    public ResponseEntity<Devi> getById(@PathVariable Integer id) {
        Devi devi = deviService.getById(id);
        return ResponseEntity.ok(devi);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Devi>> getAll() {
        List<Devi> devis = deviService.getAll();
        return ResponseEntity.ok(devis);
    }

    @PostMapping("/add")
    public ResponseEntity<Devi> ajouterDevi(@RequestBody Devi devi) {
        Devi newDevi = deviService.ajouterDevi(devi);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDevi);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Devi> modifierDevi(@PathVariable Integer id, @RequestBody Devi devi) {
        devi.setIdDevis(id);
        Devi updatedDevi = deviService.modifierDevi(devi);
        return ResponseEntity.ok(updatedDevi);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerDevi(@PathVariable Integer id) {
        deviService.supprimerDevi(id);
        return ResponseEntity.noContent().build();
    }
}
