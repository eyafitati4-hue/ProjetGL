package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.PanierDto;
import projetPFE.example.monProjet.interfac.AssociationppInter;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Association10Id;
import projetPFE.example.monProjet.model.Panier;
import projetPFE.example.monProjet.model.Produit;
import projetPFE.example.monProjet.repository.Association10Repository;
import projetPFE.example.monProjet.repository.PanierRepository;
import projetPFE.example.monProjet.repository.ProduitRepository;

import java.util.Optional;

@Service
public class Associationpp implements AssociationppInter {


    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private Association10Repository association10Repository;
    @Autowired
    private projetPFE.example.monProjet.service.Panier servicePanier;



    @Override
    public Association10 ajouterProduitAuPanier(Integer idProduit, Integer idPanier) {
        System.out.println(idProduit + "," + idPanier);
        Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        System.out.println(produit);
        Panier panier = panierRepository.findById(idPanier).orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        System.out.println(panier);
        Association10Id idPanierProduit = new Association10Id();
        idPanierProduit.setIdPanier(idPanier);
        idPanierProduit.setIdProduit(idProduit);
        Optional<Association10> testId = association10Repository.findById(idPanierProduit);
        System.out.println(testId.isPresent());
        if (testId.isPresent()) {
            Association10 oldPanierProduit = testId.get();
            System.out.println("found" + oldPanierProduit);
            return null;
        } else {
            Association10 newPanierProduit = new Association10();
            newPanierProduit.setId(idPanierProduit);
            //newPanierProduit.setPanier(panier);
            //newPanierProduit.setProduit(produit);
            Association10 savedPanierProduit = association10Repository.save(newPanierProduit);
            return savedPanierProduit;
        }
    }
}
