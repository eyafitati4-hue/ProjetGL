package projetPFE.example.monProjet.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.model.Produit;

@Component
@Primary
public class CalculStandardStrategy implements CalculStrategy {

    private final SimulationConfig config;

    public CalculStandardStrategy(SimulationConfig config) {
        this.config = config;
    }

    @Override
    public double calculerApport(double prixProduit) {
        if (prixProduit <= 0) {
            throw new IllegalArgumentException("OCL Violation: le prix doit être > 0 pour calculer l'apport");
        }
        return (prixProduit * 10) / 100;
    }

    @Override
    public double calculerLoyer(Produit produit, int nombreAnnees, double nouvelApportPropre) {
        // Préconditions OCL
        if (produit == null) {
            throw new IllegalArgumentException("OCL Violation: le produit ne peut pas être nul.");
        }
        if (nombreAnnees <= 0) {
            throw new IllegalArgumentException("OCL Violation: la durée du prêt doit être supérieure à 0 an.");
        }
        if (nouvelApportPropre >= produit.getPrixproduit()) {
            throw new IllegalArgumentException("OCL Violation: l'apport ne peut pas être supérieur ou égal au prix du produit.");
        }

        double montantEmprunte = produit.getPrixproduit() - nouvelApportPropre;
        int nombrePaiementsTotal = nombreAnnees * 12;
        double tauxInteret = config.getTauxInteret();
        double tauxMensuel = tauxInteret / 12; // Conversion du taux annuel en taux mensuel
        double interetsMensuels = montantEmprunte * (tauxMensuel / 100);
        double loyerMensuel = (montantEmprunte + interetsMensuels) / nombrePaiementsTotal;
        return loyerMensuel;
    }
}
