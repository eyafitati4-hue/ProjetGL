package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.DemandeDto;
import projetPFE.example.monProjet.interfac.DemandeInter;
import projetPFE.example.monProjet.model.Demande;

import java.util.List;

@RestController
@RequestMapping("/demandes")
@CrossOrigin(origins = "*")

public class DemandeController {

    @Autowired
    private DemandeInter demandeService;

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getById(@PathVariable int id) {
        Demande demande = demandeService.getById(id);
        return ResponseEntity.ok(demande);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Demande>> getAll() {
        List<Demande> demandes = demandeService.getAll();
        return ResponseEntity.ok(demandes);
    }


   @PostMapping("/add")
   public DemandeDto ajouterDemande(@RequestBody DemandeDto demande, @RequestHeader("Authorization") String authorizationHeader) {
           return demandeService.ajouterDemande(demande, authorizationHeader);
   }

    @GetMapping("/getbyiduser")
    public ResponseEntity<List<DemandeDto>> getDemandesByIdUtilisateur(@RequestHeader("Authorization") String authorizationHeader) {
        List<DemandeDto> demandes = demandeService.getDemandesByIdUtilisateur(authorizationHeader);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Demande> modifierDemande(@PathVariable int id, @RequestBody Demande demande) {
        demande.setDemande(id);
        Demande updatedDemande = demandeService.modifierDemande(demande);
        return ResponseEntity.ok(updatedDemande);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerDemande(@PathVariable int id) {
        demandeService.supprimerDemande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/countByEtatDemande/{etatDemandeId}")
    public ResponseEntity<Integer> getCountByEtatDemande(@PathVariable int etatDemandeId) {
        int count = demandeService.getCountByEtatDemande(etatDemandeId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}/etat")
    public ResponseEntity<DemandeDto> modifierEtatDemande(@PathVariable("id") Integer id, @RequestBody DemandeDto newDemandeDto) {
        DemandeDto modifiedDemande = demandeService.modifierEtatDemande(id, newDemandeDto);
        if (modifiedDemande != null) {
            return ResponseEntity.ok(modifiedDemande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
