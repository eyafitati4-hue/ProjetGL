package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.DemandeDto;
import projetPFE.example.monProjet.DTOmapper.DemandeDtoMapper;
import projetPFE.example.monProjet.DTOmapper.EtatdemandeDtoMapper;
import projetPFE.example.monProjet.DTOmapper.UtilisateurDtoMapper;
import projetPFE.example.monProjet.auth.IIdentityService;
import projetPFE.example.monProjet.config.JwtService;
import projetPFE.example.monProjet.interfac.DemandeInter;
import projetPFE.example.monProjet.model.*;
import projetPFE.example.monProjet.model.Devi;
import projetPFE.example.monProjet.model.Etatdemande;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.Association4Repository;
import projetPFE.example.monProjet.repository.DemandeRepository;
import projetPFE.example.monProjet.repository.UtilisateurRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Demande implements DemandeInter {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private Association4Repository association4Repository;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private IIdentityService authService;

    @Override
    public projetPFE.example.monProjet.model.Demande getById(int id) {
        return demandeRepository.findById(id).orElse(null);
    }

    @Override
    public List<projetPFE.example.monProjet.model.Demande> getAll() {
        return demandeRepository.findAll();
    }


    @Override
    public DemandeDto ajouterDemande (DemandeDto demande, String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        String EmailUtilisateur = jwtService.extractNomUtilisateur(jwt);
            Optional<Utilisateur> userDetails = utilisateurRepository.findByEmail(EmailUtilisateur);
            if (!userDetails.isPresent()) {
                throw new IllegalArgumentException("Impossible de récupérer l'ID de l'utilisateur à partir du token.");
            }
            demande.setIdutilisateur(UtilisateurDtoMapper.toDto(userDetails.get()));
            Devi deviParDefaut = new Devi();
            deviParDefaut.setIdDevis(1);
            demande.setDevis(deviParDefaut);
            Etatdemande etatDemandeParDefaut = new Etatdemande();
            etatDemandeParDefaut.setIdetatdemande(1);
            demande.setEtatdemade(etatDemandeParDefaut);
            System.out.println(demande);

        projetPFE.example.monProjet.model.Demande demandeSauvegardee = demandeRepository.save(DemandeDtoMapper.toEntity(demande));


        for (int i = 1; i <= 3; i++) {
            Association4 nouvelleAssociation = new Association4();
            Association4Id associationId = new Association4Id();
            associationId.setIddocument(i);
            associationId.setIddemande(demandeSauvegardee.getIdDemande());
            nouvelleAssociation.setId(associationId);
            association4Repository.save(nouvelleAssociation);

        }
            //return DemandeDtoMapper.toDto(demandeRepository.save(DemandeDtoMapper.toEntity(demande)));
        return DemandeDtoMapper.toDto(demandeSauvegardee);
    }
    @Override
    public projetPFE.example.monProjet.model.Demande modifierDemande(projetPFE.example.monProjet.model.Demande demande) {
        if (demandeRepository.existsById(demande.getIdDemande())) {
            return demandeRepository.save(demande);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerDemande(int id) {
        demandeRepository.deleteById(id);
    }

    @Override
    public int getCountByEtatDemande(int etatDemandeId) {
        return demandeRepository.countByEtatDemande_Idetatdemande(etatDemandeId);
    }

    @Override
    public DemandeDto modifierEtatDemande(Integer id, DemandeDto newDemandeDto) {
        Optional<projetPFE.example.monProjet.model.Demande> oldDemandeOptional = demandeRepository.findById(id);

        if (oldDemandeOptional.isPresent()) {
            projetPFE.example.monProjet.model.Demande oldDemandeEntity = oldDemandeOptional.get();
            if (newDemandeDto.getIdetatdemade() != null) {
                int nextEtatId = newDemandeDto.getIdetatdemade().getIdetatdemande();
                int currentStateId = oldDemandeEntity.getIdetatdemade() != null 
                        ? oldDemandeEntity.getIdetatdemade().getIdetatdemande() : 1;
                
                try {
                    // Si l'état demandé est déjà l'état actuel, on ne fait rien.
                    if (currentStateId == nextEtatId) {
                        // Pas d'action requise
                    } 
                    // Si on demande à passer à l'étape suivante logique
                    else if ((currentStateId == 1 && nextEtatId == 2) || (currentStateId == 2 && nextEtatId == 3)) {
                        oldDemandeEntity.valider();
                    } 
                    // Si on demande le rejet
                    else if (nextEtatId == 4) {
                        oldDemandeEntity.rejeter();
                    } 
                    // Sinon, tentative invalide
                    else {
                        throw new IllegalStateException("Transition de l'état " + currentStateId + " vers " + nextEtatId + " est invalide ou ne respecte pas l'ordre (1 -> 2 -> 3).");
                    }

                    demandeRepository.save(oldDemandeEntity);
                    return DemandeDtoMapper.toDto(oldDemandeEntity);
                    
                } catch (IllegalStateException e) {
                    throw new IllegalArgumentException("Transition d'état refusée : " + e.getMessage());
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

   @Override
    public List<DemandeDto> getDemandesByIdUtilisateur(String authorizationHeader) {
        String emailUtilisateur = extractUserEmailFromToken(authorizationHeader);
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(emailUtilisateur);
        if (!utilisateur.isPresent()) {
            throw new IllegalArgumentException("Utilisateur introuvable pour cet email.");
        }

        List<projetPFE.example.monProjet.model.Demande> demandes = demandeRepository.findByIdutilisateur(utilisateur.get());
        return demandes.stream().map(DemandeDtoMapper::toDto).collect(Collectors.toList());
    }

    private String extractUserEmailFromToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        return jwtService.extractNomUtilisateur(jwt);
    }
}


