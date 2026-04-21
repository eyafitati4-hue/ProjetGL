package projetPFE.example.monProjet.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.Collection;
import java.util.List;

/**
 * SOLID — SRP : Responsabilité UNIQUE = adaptateur Spring Security.
 *
 * Sépare la responsabilité sécurité de l'entité métier Utilisateur.
 * Utilisateur.java = identité humaine (nom, email, téléphone).
 * UtilisateurSecurityAdapter.java = contrat Spring Security.
 *
 * Raison de changer : UNIQUEMENT si le contrat Spring Security change.
 * Pas si les règles métier de l'utilisateur changent.
 */
public class UtilisateurSecurityAdapter implements UserDetails {

    private final Utilisateur utilisateur;

    public UtilisateurSecurityAdapter(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    // === Responsabilité Spring Security ===

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(
            utilisateur.getIdRole().getLabelrole()));
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotdepasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override public boolean isAccountNonExpired()    { return true; }
    @Override public boolean isAccountNonLocked()     { return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled()              { return true; }

    // === Accès à l'entité métier (read-only) ===
    public Utilisateur getUtilisateur() { return utilisateur; }
}
