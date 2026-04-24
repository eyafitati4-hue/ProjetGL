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
public class AdminUserFactory implements IUserFactory {

    private final RoleRepository roleRepository;
    private final EtatutilisateurRepository etatRepository;

    @Override
    public Utilisateur createUser(RegisterRequest request, String encodedPassword) {
        Role role = roleRepository.findByLabelrole(RoleType.ADMIN.name())
                .orElseThrow(() -> new RuntimeException("Rôle ADMIN non trouvé"));

        Etatutilisateur etat = etatRepository.findByLabelleetatutilisateur("ACTIF")
                .orElseThrow(() -> new RuntimeException("Etat ACTIF not found"));

        return Utilisateur.builder()
                .prenom(request.getPrenom())
                .nomutilisateur(request.getNom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .codepostal(request.getCodepostal())
                .datenaissance(request.getDatenaissance())
                .motdepasse(encodedPassword)
                .idRole(role)
                .idEtat(etat)
                .build();
    }
}
