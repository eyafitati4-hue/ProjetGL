package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.DTO.DemandeDto;

/**
 * SOLID – ISP (Interface Segregation Principle) :
 * Interface dédiée aux opérations d'administration et de suivi.
 * Réservée aux rôles ADMIN et CONCESSIONNAIRE.
 * Un client n'a jamais accès à ces méthodes.
 */
public interface IDemandeAdminService {
    DemandeDto modifierDemande(int id, DemandeDto demande);
    void supprimerDemande(int id);
    int getCountByEtatDemande(int etatDemandeId);
    DemandeDto modifierEtatDemande(Integer id, DemandeDto newDemandeDto);
}
