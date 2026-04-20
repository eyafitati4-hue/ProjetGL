package projetPFE.example.monProjet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.PanierDto;
import projetPFE.example.monProjet.interfac.PanierInter;
import projetPFE.example.monProjet.model.Panier;

import java.util.List;

@RestController
@RequestMapping("/paniers")
@CrossOrigin(origins = "*") // Autoriser les requêtes CORS depuis http://localhost:4200

public class PanierController {

    @Autowired
    private PanierInter panierService;

    @GetMapping("/{id}")
    public PanierDto getById(@PathVariable Integer id) {
        return panierService.getById(id);
    }

    @GetMapping("/all")
    public List<PanierDto> getAll() {
        return panierService.getAll();
    }

    @PostMapping("/add")
    public PanierDto ajouterPanier(@RequestBody PanierDto panierDto) {
        return panierService.ajouterPanier(panierDto);
    }

    @PutMapping("/update/{id}")
    public PanierDto modifierPanier(@RequestBody PanierDto panierDto) {
        return panierService.modifierPanier(panierDto);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimerPanier(@PathVariable Integer id) {
        panierService.supprimerPanier(id);
    }

    // --- Endpoints de Fabrication Pure ---

    @PostMapping("/{idPanier}/ajouter-produit/{idProduit}")
    public ResponseEntity<String> ajouterProduit(@PathVariable Integer idPanier, @PathVariable Integer idProduit) {
        try {
            panierService.ajouterProduitAuPanier(idProduit, idPanier);
            return ResponseEntity.ok("Produit ajouté au panier avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/total")
    public double calculerTotal(@PathVariable Integer id) {
        return panierService.calculerTotalHT(id);
    }
}
