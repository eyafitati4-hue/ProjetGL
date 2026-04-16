package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.DTO.DemandeDto;
import projetPFE.example.monProjet.model.Demande;

import java.util.List;
public interface DemandeInter {
    Demande getById(int id);
    List<Demande> getAll();
    DemandeDto ajouterDemande(DemandeDto demande, String authorizationHeader);

    Demande modifierDemande(Demande demande);
    void supprimerDemande(int id);
    int getCountByEtatDemande(int etatDemandeId);

     DemandeDto modifierEtatDemande(Integer id, DemandeDto newDemandeDto);


    List<DemandeDto> getDemandesByIdUtilisateur(String authorizationHeader);
}
