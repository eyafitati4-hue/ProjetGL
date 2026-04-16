package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.interfac.UtilisateurInter;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.UtilisateurRepository;
import projetPFE.example.monProjet.tool.Count;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "*")

public class UtilisateurController {
    @Autowired
    private UtilisateurInter utilisateurService;


    @Autowired
   UtilisateurRepository utilisateurRepository;

    @GetMapping("/all")
    public List<UtilisateurDto> getAll() {
        return utilisateurService.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public Utilisateur findByEmail(@PathVariable String email) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur.isPresent()){
            Utilisateur utilisateurEntity=utilisateur.get();
        return utilisateurEntity;}
        else {
            return null;
        }
    }

    @GetMapping("/{id}")
    public UtilisateurDto getById(@PathVariable Integer id) {
        return utilisateurService.getById(id);
    }




    @PostMapping("/conc")
    public ResponseEntity<List<UtilisateurDto>> getUsersByRoleId(@RequestBody Role idRole) {
        List<UtilisateurDto> users = utilisateurService.getUsersByRoleId(idRole);
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{id}/etat")
    public UtilisateurDto updateEtat(@PathVariable int id,@RequestBody UtilisateurDto utilisateur){
        return utilisateurService.modifierEtatUtilisateur(id,utilisateur);
    }



    @PostMapping("/add")
    public UtilisateurDto ajouterUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return utilisateurService.ajouterUtilisateur(utilisateurDto);
    }

    @PutMapping("/update/{id}")
    public UtilisateurDto modifierUtilisateur(@PathVariable(name="id") Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        return utilisateurService.modifierUtilisateur(id, utilisateurDto);
    }

    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(@PathVariable Integer id) {
        utilisateurService.supprimerUtilisateur(id);
    }



    @GetMapping("/total-users")
    public long getTotalUsersCount() {
        long totalUsers = utilisateurService.countTotalUsers();
        return totalUsers;
    }


    @GetMapping("/countbyrole/{idRole}")
    public ResponseEntity<Long> countUsersByRoleId(@PathVariable Integer idRole) {
        long count = utilisateurService.countUsersByRoleId(idRole);
        return ResponseEntity.ok(count);
    }
}