package projetPFE.example.monProjet.interfac;

/**
 * SOLID – ISP (Interface Segregation Principle) :
 * Interface de composition qui hérite des 3 sous-interfaces spécialisées.
 *
 * Avantages :
 *  - Le service Demande.java implémente toujours UNE seule interface (zéro breaking change)
 *  - Chaque client (controller, composant) injecte uniquement la sous-interface dont il a besoin
 *  - Un CLIENT ne voit jamais les méthodes ADMIN, et vice-versa
 *
 *  IDemandeClientService       → ajouterDemande, getDemandesByIdUtilisateur
 *  IDemandeAdminService        → modifierDemande, supprimerDemande, count, modifierEtat
 *  IDemandeConsultationService → getById, getAll
 */
public interface DemandeInter
        extends IDemandeClientService,
                IDemandeAdminService,
                IDemandeConsultationService {
    // Aucune méthode supplémentaire — composition pure des 3 sous-interfaces
}
