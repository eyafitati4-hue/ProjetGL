package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.DTO.DemandeDto;
import java.util.List;

/**
 * SOLID – ISP (Interface Segregation Principle) :
 * Interface dédiée aux opérations réservées au CLIENT.
 * Un client peut créer une demande et consulter ses propres demandes.
 * Il n'a aucune visibilité sur les méthodes d'administration.
 */
public interface IDemandeClientService {
    DemandeDto ajouterDemande(DemandeDto demande, String authorizationHeader);
    List<DemandeDto> getDemandesByIdUtilisateur(String authorizationHeader);
}
