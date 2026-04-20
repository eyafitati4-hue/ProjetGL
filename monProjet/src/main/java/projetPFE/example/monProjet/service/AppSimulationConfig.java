package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implémentation concrète de la configuration récupérant les valeurs
 * depuis les fichiers de propriétés de Spring.
 */
@Component
public class AppSimulationConfig implements SimulationConfig {

    @Value("${simulation.taux-interet:20.0}")
    private double tauxInteret;

    @Override
    public double getTauxInteret() {
        return tauxInteret;
    }
}
