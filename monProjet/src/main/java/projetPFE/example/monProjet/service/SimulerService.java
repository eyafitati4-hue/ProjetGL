package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import projetPFE.example.monProjet.model.Produit;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.interfac.SimulerInter;
import projetPFE.example.monProjet.repository.ProduitRepository;

@Service
public class SimulerService  implements SimulerInter {


   @Autowired
   private ProduitRepository produitRepository;

    private double tauxInteret = 20;
    public double calculerApportPropre(Integer prixproduit){
        if (prixproduit == null) return 0;
        return (prixproduit * 10)/100 ;
    }

    public double calculerLoyerMensuel(Produit produit,int nombreAnnees, double nouvelApportPropre ){
        double montantEmprunte = produit.getPrixproduit() - nouvelApportPropre;
        int nombrePaiementsTotal = nombreAnnees * 12;
        double tauxMensuel = tauxInteret / 12; // Conversion du taux annuel en taux mensuel
        double interetsMensuels = montantEmprunte * (tauxMensuel / 100);
        double loyerMensuel = (montantEmprunte + interetsMensuels) / nombrePaiementsTotal;
        return loyerMensuel;
    }


    public double modifierLoyerMensuel(int nombreAnnees, Produit produit, double nouvelApportPropre) {
        double nouveauLoyer = calculerLoyerMensuel(produit ,nombreAnnees,nouvelApportPropre);
      //  produit.setLoyer(nouveauLoyer); // Mettre à jour le loyer dans l'objet produit
       //  produitRepository.save(produit);
        return nouveauLoyer;
    }


    public double modifierApportPropre(double nouvelApportPropre, Produit produit , int nombreAnnees) {
      //  produit.setApportpropre(nouvelApportPropre);
        double nouveauLoyer = calculerLoyerMensuel(produit,nombreAnnees,nouvelApportPropre);
       // produit.setLoyer(nouveauLoyer);
       // produitRepository.save(produit);
        return nouvelApportPropre;
    }






}
