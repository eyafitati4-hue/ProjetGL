package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.DTO.UtilisateurDto;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.service.Role;

import java.util.List;

public interface UtilisateurInter {

        UtilisateurDto getById(Integer id);

        public List<UtilisateurDto> getUsersByRoleId(projetPFE.example.monProjet.model.Role idRole) ;

        List<UtilisateurDto> getAll();

        UtilisateurDto ajouterUtilisateur(UtilisateurDto utilisateurDto);

        UtilisateurDto modifierUtilisateur(Integer id, UtilisateurDto utilisateurDto);

        void supprimerUtilisateur(Integer id);
        UtilisateurDto modifierEtatUtilisateur(Integer id, UtilisateurDto utilisateurDto);



        long countTotalUsers();

     //   long countTotalUsersByRole(int idRole);

  // long countUsersByRoleId(int role) ;

       // long countUsersByRoleId(Role role) ;
    long countUsersByRoleId(Integer idRole);

        }
