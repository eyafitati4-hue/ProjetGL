package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.interfac.EtatproduitInter;
import projetPFE.example.monProjet.model.Etatproduit;

import java.util.List;
@RestController
@RequestMapping("/etat-produit")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes CORS depuis http://localhost:4200

public class EtatproduitController {
    @Autowired
    private EtatproduitInter etatProduitService;

    @GetMapping("/{id}")
    public ResponseEntity<Etatproduit> getById(@PathVariable Integer id) {
        Etatproduit etatProduit = etatProduitService.getById(id);
        return ResponseEntity.ok(etatProduit);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Etatproduit>> getAll() {
        List<Etatproduit> etatsProduit = etatProduitService.getAll();
        return ResponseEntity.ok(etatsProduit);
    }

    @PostMapping("/add")
    public ResponseEntity<Etatproduit> ajouterEtatproduit(@RequestBody Etatproduit etatProduit) {
        Etatproduit newEtatProduit = etatProduitService.ajouterEtatproduit(etatProduit);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEtatProduit);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Etatproduit> modifierEtatproduit(@PathVariable Integer id, @RequestBody Etatproduit etatProduit) {
        etatProduit.setIdetatproduit(id);
        Etatproduit updatedEtatProduit = etatProduitService.modifierEtatproduit(etatProduit);
        return ResponseEntity.ok(updatedEtatProduit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerEtatproduit(@PathVariable Integer id) {
        etatProduitService.supprimerEtatproduit(id);
        return ResponseEntity.noContent().build();
    }
}
