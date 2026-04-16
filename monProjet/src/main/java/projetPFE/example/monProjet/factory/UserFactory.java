package projetPFE.example.monProjet.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.auth.RegisterRequest;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.RoleType;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.EtatutilisateurRepository;
import projetPFE.example.monProjet.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final RoleRepository roleRepository;
    private final EtatutilisateurRepository etatRepository;

    public Utilisateur createUser(RegisterRequest request, String encodedPassword, RoleType roleType) {
        Role role = roleRepository.findByLabelrole(roleType.name())
                .orElseThrow(() -> new RuntimeException("Role " + roleType.name() + " not found"));
        
        Etatutilisateur etat = etatRepository.findByLabelleetatutilisateur("ACTIF")
                .orElseThrow(() -> new RuntimeException("Etat ACTIF not found"));

        return Utilisateur.builder()
                .prenom(request.getPrenom())
                .nomutilisateur(request.getNom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .ville(request.getAdresse())
                .codepostal(request.getCodepostal())
                .datenaissance(request.getDatenaissance())
                .motdepasse(encodedPassword)
                .idRole(role)
                .idEtat(etat)
                .build();
    }
}
