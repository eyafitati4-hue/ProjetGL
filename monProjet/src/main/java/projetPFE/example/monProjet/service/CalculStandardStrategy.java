package projetPFE.example.monProjet.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.model.Produit;

@Component
@Primary
public class CalculStandardStrategy implements CalculStrategy {
    private final double tauxInteret = 20.0;

    @Override
    public double calculerApport(double prixProduit) {
        if (prixProduit <= 0) {
            throw new IllegalArgumentException("OCL Violation: le prix doit être > 0 pour calculer l'apport");
        }
        return (prixProduit * 10) / 100;
    }
    
    @Override
    public double calculerLoyer(Produit produit, int nombreAnnees, double nouvelApportPropre) {
        double montantEmprunte = produit.getPrixproduit() - nouvelApportPropre;
        int nombrePaiementsTotal = nombreAnnees * 12;
        double tauxMensuel = tauxInteret / 12; // Conversion du taux annuel en taux mensuel
        double interetsMensuels = montantEmprunte * (tauxMensuel / 100);
        double loyerMensuel = (montantEmprunte + interetsMensuels) / nombrePaiementsTotal;
        return loyerMensuel;
    }
}
