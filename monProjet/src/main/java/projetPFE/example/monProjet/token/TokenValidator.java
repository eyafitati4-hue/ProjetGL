package projetPFE.example.monProjet.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.config.JwtService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SOLID — SRP : Responsabilité UNIQUE = valider un token JWT.
 *
 * Avant : cette logique était dans JwtAuthenticationFilter (mélangée).
 * Après : classe dédiée, testable indépendamment.
 *
 * Raison de changer : UNIQUEMENT si les règles de validation changent.
 */
@Service
@RequiredArgsConstructor
public class TokenValidator {

    private final JwtService       jwtService;
    private final TokenRepository  tokenRepository;

    /**
     * Valide un token : signature JWT + non révoqué en DB.
     * Utilise Token.isSessionActive() pour la validation DB.
     */
    public boolean validate(String jwt, UserDetails userDetails) {
        System.out.println("[TokenValidator SRP] validate() pour : "
            + userDetails.getUsername());

        // Validation 1 : signature et expiration JWT
        boolean jwtOk = jwtService.isTokenValid(jwt, userDetails);

        // Validation 2 : statut en base (utilise Token.isSessionActive())
        boolean dbOk = tokenRepository.findByToken(jwt)
                .map(Token::isSessionActive)  // SRP : Token connaît sa validité
                .orElse(false);

        System.out.println("[TokenValidator SRP] jwtOk=" + jwtOk
            + " dbOk=" + dbOk);
        return jwtOk && dbOk;
    }

    /**
     * Valide uniquement la signature JWT (sans vérification DB).
     */
    public boolean validateSignatureOnly(String jwt, UserDetails userDetails) {
        return jwtService.isTokenValid(jwt, userDetails);
    }
}
