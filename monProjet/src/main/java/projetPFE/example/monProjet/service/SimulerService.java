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

   @Autowired
   private CalculStrategy calculStrategy;


    public double calculerApportPropre(Integer prixproduit){
    return calculStrategy.calculerApport(prixproduit.doubleValue());
    }


    public double calculerLoyerMensuel(Produit produit, int nombreAnnees, double nouvelApportPropre){
        return calculStrategy.calculerLoyer(produit, nombreAnnees, nouvelApportPropre);
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
