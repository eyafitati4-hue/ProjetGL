package projetPFE.example.monProjet.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.auth.RegisterRequest;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final AdminUserFactory adminFactory;
    private final ClientUserFactory clientFactory;
    private final ConcessionnaireUserFactory concessionnaireFactory;
    private final RoleRepository roleRepository;

    public Utilisateur createUser(RegisterRequest request, String encodedPassword) {
        String roleLabel = null;

        if (request.getIdRole() != null) {
            if (request.getIdRole().getLabelrole() != null) {
                roleLabel = request.getIdRole().getLabelrole();
            } else if (request.getIdRole().getIdRole() != null) {
                roleLabel = roleRepository.findById(request.getIdRole().getIdRole())
                        .map(r -> r.getLabelrole())
                        .orElse(null);
            }
        }

        // Sélection de la factory concrète selon le rôle
        if ("ADMIN".equalsIgnoreCase(roleLabel)) {
            return adminFactory.createUser(request, encodedPassword);
        } else if ("CONCESSIONNAIRE".equalsIgnoreCase(roleLabel)) {
            return concessionnaireFactory.createUser(request, encodedPassword);
        }
        
        // Par défaut, utilise la factory client
        return clientFactory.createUser(request, encodedPassword);
    }
}
