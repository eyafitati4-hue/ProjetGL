package projetPFE.example.monProjet.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.config.JwtService;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * GRASP — Indirection : Implémentation du médiateur de session.
 *
 * Détails cachés : JWT, TokenRepository, crypto.
 * Interface exposée : isConnected(), invalidateSession(), getSessionInfo()
 *
 * Pour passer à OAuth2 → créer OAuth2SessionServiceImpl
 * et l'injecter à la place — zéro changement dans les contrôleurs.
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {

    private final JwtService       jwtService;
    private final TokenRepository  tokenRepository;

    @Override
    public boolean isConnected(String token) {
        System.out.println("[Mediateur] isConnected() appelé");
        try {
            String email = jwtService.extractNomUtilisateur(token);
            if (email == null) return false;
            boolean dbValid = tokenRepository.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            boolean jwtValid = !isExpiredByDate(token);
            System.out.println("[Mediateur] email=" + email
                + " jwtValid=" + jwtValid + " dbValid=" + dbValid);
            return jwtValid && dbValid;
        } catch (Exception e) {
            System.out.println("[Mediateur] token invalide : " + e.getMessage());
            return false;
        }
    }

    @Override
    public void invalidateSession(String token) {
        System.out.println("[Mediateur] invalidateSession() appelé");
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            tokenRepository.save(t);
            System.out.println("[Mediateur] Session révoquée pour token DB id=" + t.getId());
        });
    }

    @Override
    public SessionInfo getSessionInfo(String token) {
        System.out.println("[Mediateur] getSessionInfo() appelé");
        try {
            String email  = jwtService.extractNomUtilisateur(token);
            boolean active = isConnected(token);
            Date exp = jwtService.extractClaim(token,
                claims -> claims.getExpiration());
            String expStr = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(exp.toInstant());
            // Récupérer le rôle depuis DB via token
            String role = tokenRepository.findByToken(token)
                    .map(t -> t.getUtilisateur().getIdRole().getLabelrole())
                    .orElse("UNKNOWN");
            return SessionInfo.builder()
                    .email(email)
                    .role(role)
                    .active(active)
                    .expiresAt(expStr)
                    .sessionType("JWT")
                    .build();
        } catch (Exception e) {
            return SessionInfo.builder()
                    .active(false)
                    .sessionType("JWT")
                    .build();
        }
    }

    private boolean isExpiredByDate(String token) {
        try {
            Date exp = jwtService.extractClaim(token,
                claims -> claims.getExpiration());
            return exp.before(new Date());
        } catch (Exception e) { return true; }
    }
}

