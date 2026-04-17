package projetPFE.example.monProjet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.interfac.ProduitService;
import projetPFE.example.monProjet.model.Produit;

import java.util.List;

@RestController
@RequestMapping("/produits")
@CrossOrigin(origins = "*") // Autoriser les requêtes CORS depuis http://localhost:4200

public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @GetMapping("/{id}")
    public ProduitDto getById(@PathVariable Integer id) {
        return produitService.getById(id);
    }

    @GetMapping("/all")
    public List<ProduitDto> getAll() {
        return produitService.getAll();
    }


    @PutMapping("/{id}/modifieretat")
    public ProduitDto modifierEtatProduit(@PathVariable("id") Integer id, @RequestBody ProduitDto newProduitDto) {
        return produitService.modifierEtatProduit(id, newProduitDto);
    }



    @GetMapping("/marque/{idMarque}")
    public List<ProduitDto> getProduitsParMarque(@PathVariable Integer idMarque) {
        return produitService.getProduitsParMarque(idMarque);
    }


    @PostMapping("/add")
    public ProduitDto ajouterProduit(@RequestBody ProduitDto produitDto) {
        return produitService.ajouterProduit(produitDto);
    }


   @PutMapping("/update/{id}")
   public ProduitDto modifierProduit(@RequestBody ProduitDto produitDto,@PathVariable int id  ) {
       return produitService.modifierProduit(produitDto, id);
   }


    @DeleteMapping("/delete/{id}")
    public void supprimerProduit(@PathVariable Integer id) {
        produitService.supprimerProduit(id);
    }

    @GetMapping("/countByEtatProduit/{etatId}")
    public ResponseEntity<Integer> getCountByEtatProduit(@PathVariable Integer etatId) {
        Integer count = produitService.getCountByEtatProduit(etatId);
        return ResponseEntity.ok().body(count);
    }
}
