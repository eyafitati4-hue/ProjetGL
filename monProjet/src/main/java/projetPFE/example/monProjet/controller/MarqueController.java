package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.MarqueDto;
import projetPFE.example.monProjet.interfac.MarqueService;
import projetPFE.example.monProjet.model.Marque;

import java.util.List;

@RestController
@RequestMapping("/marques")
@CrossOrigin(origins = "*") // Autoriser les requêtes CORS depuis http://localhost:4200

public class MarqueController {

    @Autowired
    private MarqueService marqueService;

    @GetMapping("/{id}")
    public MarqueDto getById(@PathVariable Integer id) {
        return marqueService.getById(id);
    }

    @GetMapping("/all")
    public List<MarqueDto> getAll() {
        return marqueService.getAll();
    }

    @GetMapping("/etatmarque=1")
    public List<MarqueDto> getAllvalider() {
        return marqueService.getAllvalider();
    }


    @PutMapping("/{id}/changer-etat/{nouvelEtat}")
    public MarqueDto changerEtatMarque(@PathVariable Integer id, @PathVariable Integer nouvelEtat) {
        return marqueService.modifierEtatMarque(id, nouvelEtat);
    }


    @PostMapping("/add")
    public MarqueDto ajouterMarque(@RequestBody MarqueDto marqueDto) {
        return marqueService.ajouterMarque(marqueDto);
    }



    @PutMapping("update/{id}")
    public MarqueDto modifierMarque(@PathVariable Integer id, @RequestBody MarqueDto marqueDto) {
        return marqueService.modifierMarque(id,marqueDto);
    }




    @DeleteMapping("/delete/{id}")
    public void supprimerMarque(@PathVariable Integer id) {
        marqueService.supprimerMarque(id);
    }
}
