package projetPFE.example.monProjet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.DTOmapper.ProduitDtoMapper;
import projetPFE.example.monProjet.repository.ProduitRepository;
import projetPFE.example.monProjet.service.Produit;
import projetPFE.example.monProjet.service.SimulerService;

@RestController
@RequestMapping("/simuler")
@CrossOrigin(origins = "*")
public class SimulerController {
    @Autowired
    private SimulerService simulerService;


    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private Produit produitService;

    @GetMapping("/calculerapportpropre")
    public double calculerApportPropre(@RequestParam int prixproduit){
        return simulerService.calculerApportPropre(prixproduit);
    }

    @GetMapping("/calculerloyermensuel/{idProduit}")
    public double calculerLoyerMensuel(@PathVariable Integer idProduit,@RequestParam double nouvelApportPropre){
        ProduitDto produitDto = produitService.getById(idProduit);
        projetPFE.example.monProjet.model.Produit produit = ProduitDtoMapper.toEntity(produitDto);
        return simulerService.calculerLoyerMensuel(produit,7,nouvelApportPropre);
    }

    @PutMapping("/modifier")
    public double modifierLoyerMensuel(@RequestParam int nombreAnnees,@RequestParam double nouvelApportPropre, @RequestBody ProduitDto produitDto) {
        projetPFE.example.monProjet.model.Produit produit = ProduitDtoMapper.toEntity(produitDto);
        double nouveauLoyer = simulerService.modifierLoyerMensuel(nombreAnnees, produit,nouvelApportPropre);
        produitRepository.save(produit);
        return nouveauLoyer;
    }

    @PutMapping("/modifier-apport-propre/{idProduit}")
    public double modifierApportPropre(@PathVariable Integer idProduit, @RequestParam double nouvelApportPropre , @RequestParam int nombreAnnees ) {
        projetPFE.example.monProjet.model.Produit produit = produitRepository.findById(idProduit).orElse(null);
        if (produit != null) {
            return simulerService.modifierApportPropre(nouvelApportPropre, produit,nombreAnnees);
        } else {
            // Gérer le cas où le produit n'est pas trouvé
            return -1; // Par exemple, retourner un code d'erreur
        }
    }

}
