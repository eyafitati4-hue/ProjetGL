package projetPFE.example.monProjet.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.token.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            // === OCL Postcondition: post: all tokens of this user must be expired/revoked ===
            // On récupère tous les tokens valides de cet utilisateur
            var allUserTokens = tokenRepository.findAllValidTokenByUser(
                    storedToken.getUtilisateur().getIdutilisateur()
            );
            // On révoque tous les tokens actifs (pas seulement le courant)
            allUserTokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(allUserTokens);

            System.out.println("[Postcondition OCL] Déconnexion de l'utilisateur '"
                    + storedToken.getUtilisateur().getEmail()
                    + "'. Tous les tokens ont été révoqués.");
        }
    }
}
