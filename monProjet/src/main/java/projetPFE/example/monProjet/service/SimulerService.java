package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import projetPFE.example.monProjet.model.Produit;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.interfac.SimulerInter;
import projetPFE.example.monProjet.repository.ProduitRepository;

/**
 * Service de calcul financier (Simulations).
 *
 * Sécurisé par des pré-conditions OCL pour garantir la justesse des calculs.
 * Spécification formelle : src/main/resources/ocl/contraintes-simulation-maths.ocl
 */
@Service
public class SimulerService  implements SimulerInter {

   @Autowired
   private ProduitRepository produitRepository;

   @Autowired
   private CalculStrategy calculStrategy;

    public double calculerApportPropre(Integer prixproduit){
        // /* OCL Precondition: prixStrictementPositifPourSimul */
        if (prixproduit == null || prixproduit <= 0) {
            throw new IllegalArgumentException("OCL Violation (Precondition): prixStrictementPositifPourSimul (le prix doit être > 0)");
        }
        return calculStrategy.calculerApport(prixproduit.doubleValue());
    }

    public double calculerLoyerMensuel(Produit produit, int nombreAnnees, double nouvelApportPropre){
        // /* OCL Preconditions: produitValide, dureeValide, apportNonNegatif */
        if (produit == null || produit.getPrixproduit() == null || produit.getPrixproduit() <= 0) {
            throw new IllegalArgumentException("OCL Violation (Precondition): produitValide (le produit doit avoir un prix > 0)");
        }
        if (nombreAnnees <= 0) {
            throw new IllegalArgumentException("OCL Violation (Precondition): dureeValide (la durée doit être > 0)");
        }
        if (nouvelApportPropre < 0) {
            throw new IllegalArgumentException("OCL Violation (Precondition): apportNonNegatif (l'apport ne peut pas être négatif)");
        }
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
