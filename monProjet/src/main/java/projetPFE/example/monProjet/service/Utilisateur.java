package projetPFE.example.monProjet.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.DTOmapper.EtatUtilisateurDtoMapper;
import projetPFE.example.monProjet.DTOmapper.UtilisateurDtoMapper;
import projetPFE.example.monProjet.interfac.UtilisateurInter;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.repository.UtilisateurRepository;
import projetPFE.example.monProjet.token.TokenRepository;
import projetPFE.example.monProjet.tool.Count;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Utilisateur implements UtilisateurInter {


   // public static Integer getRole;
    @Autowired
    private UtilisateurRepository UtilisateurRepository;

    @Autowired
    private TokenRepository tokenRepository ;




    @Override
    public UtilisateurDto getById(Integer id) {
        Optional<projetPFE.example.monProjet.model.Utilisateur> optionalUtilisateur = UtilisateurRepository.findById(id);
        return optionalUtilisateur.map(UtilisateurDtoMapper::toDto).orElse(null);

    }

    @Override
    public List<UtilisateurDto> getUsersByRoleId(projetPFE.example.monProjet.model.Role idRole) {
        // Récupérer les utilisateurs par ID de rôle en utilisant une méthode personnalisée du repository
        List<projetPFE.example.monProjet.model.Utilisateur> usersByRoleId = UtilisateurRepository.findByIdRole(idRole);

        // Create a HashSet to hold UtilisateurDto objects
        HashSet<UtilisateurDto> userDtoHashSet = new HashSet<>();

        // Convert each Utilisateur object to UtilisateurDto and add it to the HashSet
        for (projetPFE.example.monProjet.model.Utilisateur utilisateur : usersByRoleId) {
            UtilisateurDto utilisateurDto = UtilisateurDtoMapper.toDto(utilisateur);
            userDtoHashSet.add(utilisateurDto);
        }

        // Convert HashSet to List if necessary
        List<UtilisateurDto> userDtos = new ArrayList<>(userDtoHashSet);

        return userDtos;
    }
    public UtilisateurDto modifierEtatUtilisateur(Integer id, UtilisateurDto newUtilisateurDto){
        System.out.println(id);
        Optional<projetPFE.example.monProjet.model.Utilisateur> oldUtilisateur=UtilisateurRepository.findById(id);
        System.out.println("found old user");
        if(oldUtilisateur.isPresent()){
            projetPFE.example.monProjet.model.Utilisateur oldUtilisateurEntity= oldUtilisateur.get();
            System.out.println(oldUtilisateurEntity);

            oldUtilisateurEntity.setIdEtat(EtatUtilisateurDtoMapper.toEntity(newUtilisateurDto.getIdEtat()));
            System.out.println(newUtilisateurDto.getIdEtat());
            UtilisateurRepository.save(oldUtilisateurEntity);
            return UtilisateurDtoMapper.toDto(oldUtilisateurEntity);
        }else{
            return null;
        }
    }



    @Override
    public List<UtilisateurDto> getAll() {
        List<projetPFE.example.monProjet.model.Utilisateur> utilisateurs = UtilisateurRepository.findAll();
        return utilisateurs.stream()
                .map(UtilisateurDtoMapper::toDto) // Convertir chaque Utilisateur en UtilisateurDto
                .collect(Collectors.toList());

    }

    public UtilisateurDto ajouterUtilisateur(UtilisateurDto utilisateurDto) {

        // Assurez-vous que la propriété idEtat de l'utilisateur est définie
        if (utilisateurDto.getIdEtat() == null) {
            throw new IllegalArgumentException("La propriété idEtat de l'utilisateur ne peut pas être nulle.");
        }
        if (utilisateurDto.getIdRole() == null) {
            throw new IllegalArgumentException("La propriété idRole de l'utilisateur ne peut pas être nulle.");
        }

        projetPFE.example.monProjet.model.Utilisateur utilisateur = UtilisateurDtoMapper.toEntity(utilisateurDto);
        utilisateur = UtilisateurRepository.save(utilisateur);
        return UtilisateurDtoMapper.toDto(utilisateur);
    }


  @Override
  public UtilisateurDto modifierUtilisateur(Integer id, UtilisateurDto newUtilisateurDto) {
      System.out.println(id);
      Optional<projetPFE.example.monProjet.model.Utilisateur> oldUtilisateur=UtilisateurRepository.findById(id);
      System.out.println("found old user");
      if(oldUtilisateur.isPresent()){
          projetPFE.example.monProjet.model.Utilisateur oldUtilisateurEntity= oldUtilisateur.get();
          System.out.println(oldUtilisateurEntity);
          oldUtilisateurEntity.setNomutilisateur((newUtilisateurDto.getNomutilisateur()));
          oldUtilisateurEntity.setPrenom((newUtilisateurDto.getPrenom()));
          oldUtilisateurEntity.setEmail((newUtilisateurDto.getEmail()));
          oldUtilisateurEntity.setMotdepasse((newUtilisateurDto.getMotdepasse()));
          oldUtilisateurEntity.setAdresse((newUtilisateurDto.getAdresse()));
          oldUtilisateurEntity.setCodepostal((newUtilisateurDto.getCodepostal()));
          oldUtilisateurEntity.setDatenaissance((newUtilisateurDto.getDatenaissance()));
          oldUtilisateurEntity.setTelephone((newUtilisateurDto.getTelephone()));
          oldUtilisateurEntity.setVille((newUtilisateurDto.getVille()));

          System.out.println(newUtilisateurDto.getIdEtat());
          return  UtilisateurDtoMapper.toDto(  UtilisateurRepository.save(oldUtilisateurEntity));
      }else{
          return null;
      }
  }


    @Transactional
    @Override
    public void supprimerUtilisateur(Integer id) {

        Optional<projetPFE.example.monProjet.model.Utilisateur> utilisateurOptional = UtilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            projetPFE.example.monProjet.model.Utilisateur utilisateur = utilisateurOptional.get();
            System.out.println (utilisateur.getTokens());
            System.out.println (utilisateur.getTokens().isEmpty());
            if (!utilisateur.getTokens().isEmpty()) {
                // Si des tokens sont associés à cet utilisateur, nous devons les supprimer d'abord
                tokenRepository.deleteAllByUtilisateur(utilisateur);
            }
            // Une fois que les tokens sont supprimés (s'il y en avait), nous pouvons supprimer l'utilisateur
            UtilisateurRepository.deleteById(id);
        } else {
            // Gérer le cas où aucun utilisateur avec cet ID n'est trouvé
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
}

    @Override
    public long countTotalUsers() {
        return UtilisateurRepository.count();
    }
    @Override
    public long countUsersByRoleId(Integer idRole) {
        return UtilisateurRepository.countByIdRole_IdRole(idRole);
    }
}