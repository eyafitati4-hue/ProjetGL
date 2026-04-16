package projetPFE.example.monProjet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import projetPFE.example.monProjet.interfac.AssociationppInter;
import projetPFE.example.monProjet.interfac.UtilisateurInter;
import projetPFE.example.monProjet.service.Associationpp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associationpp")
@CrossOrigin(origins = "*")
public class AssociationppController {
    @Autowired
    private AssociationppInter associationppService;

    @PostMapping("/ajouterProduitAuPanier/{idProduit}/{idPanier}")
    public void ajouterProduitAuPanier(@PathVariable Integer idProduit, @PathVariable Integer idPanier) {
        associationppService.ajouterProduitAuPanier(idProduit, idPanier);
    }

}
