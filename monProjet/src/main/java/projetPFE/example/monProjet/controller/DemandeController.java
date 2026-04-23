package projetPFE.example.monProjet.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.DemandeDto;
import projetPFE.example.monProjet.interfac.DemandeInter;
import projetPFE.example.monProjet.model.Demande;

import java.util.List;

/**
 * GRASP – Principe Contrôleur :
 * Ce controller est un simple aiguilleur HTTP.
 * Il reçoit les paramètres de la requête, les transmet au service compétent,
 * et renvoie le résultat. Il ne contient aucun calcul ni aucune prise de décision métier.
 */
@RestController
@RequestMapping("/demandes")
@CrossOrigin(origins = "*")
public class DemandeController {

    @Autowired
    private DemandeInter demandeService;

    // ── GET par ID ────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Demande> getById(@PathVariable int id) {
        return ResponseEntity.ok(demandeService.getById(id));
    }

    // ── GET tous ──────────────────────────────────────────────────────
    @GetMapping("/all")
    public ResponseEntity<List<Demande>> getAll() {
        return ResponseEntity.ok(demandeService.getAll());
    }

    // ── POST créer ────────────────────────────────────────────────────
    // CORRIGÉ : retourne 201 Created (sémantiquement correct pour une création)
    @PostMapping("/add")
    public ResponseEntity<DemandeDto> ajouterDemande(
            @RequestBody DemandeDto demande,
            @RequestHeader("Authorization") String authorizationHeader) {
        DemandeDto created = demandeService.ajouterDemande(demande, authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── GET par utilisateur connecté ──────────────────────────────────
    @GetMapping("/getbyiduser")
    public ResponseEntity<List<DemandeDto>> getDemandesByIdUtilisateur(
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(demandeService.getDemandesByIdUtilisateur(authorizationHeader));
    }

    // ── PUT modifier ──────────────────────────────────────────────────
    // CORRIGÉ : reçoit un DTO (pas une entité JPA brute), passe id + DTO au service
    // Plus de demande.setDemande(id) ici — le service s'en charge
    @PutMapping("/update/{id}")
    public ResponseEntity<DemandeDto> modifierDemande(
            @PathVariable int id,
            @RequestBody DemandeDto demandeDto) {
        return ResponseEntity.ok(demandeService.modifierDemande(id, demandeDto));
    }

    // ── DELETE ────────────────────────────────────────────────────────
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> supprimerDemande(@PathVariable int id) {
        demandeService.supprimerDemande(id);
        return ResponseEntity.noContent().build();
    }

    // ── GET comptage par état ─────────────────────────────────────────
    @GetMapping("/countByEtatDemande/{etatDemandeId}")
    public ResponseEntity<Integer> getCountByEtatDemande(@PathVariable int etatDemandeId) {
        return ResponseEntity.ok(demandeService.getCountByEtatDemande(etatDemandeId));
    }

    // ── PUT changer l'état (State Pattern) ────────────────────────────
    // CORRIGÉ : plus de if/else null ici — le service lève une exception si absent
    @PutMapping("/{id}/etat")
    public ResponseEntity<DemandeDto> modifierEtatDemande(
            @PathVariable Integer id,
            @RequestBody DemandeDto newDemandeDto) {
        return ResponseEntity.ok(demandeService.modifierEtatDemande(id, newDemandeDto));
    }

    // ── Gestion centralisée des erreurs ───────────────────────────────
    // GRASP Contrôleur : les décisions d'erreur sont traitées ici de façon uniforme,
    // sans if/else dispersés dans chaque méthode.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
