package projetPFE.example.monProjet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import projetPFE.example.monProjet.token.TokenValidator;
import projetPFE.example.monProjet.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * Démontre SOLID SRP :
 * - TokenValidator a 1 responsabilité : valider
 * - Token.isSessionActive() a 1 responsabilité : état
 * - Utilisateur = identité métier (pas de logique sécurité ici)
 */
@RestController
@RequestMapping("/api/srp")
@RequiredArgsConstructor
public class SrpTestController {

    private final TokenValidator       tokenValidator;
    private final TokenRepository      tokenRepository;
    private final UserDetailsService   userDetailsService;

    /**
     * TEST 1 : Valider un token via TokenValidator (SRP).
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.replace("Bearer ", "").trim();
        try {
            String email = extractEmail(jwt);
            var userDetails = userDetailsService.loadUserByUsername(email);
            boolean valid = tokenValidator.validate(jwt, userDetails);
            return ResponseEntity.ok(Map.of(
                "tokenValide",   valid,
                "email",         email,
                "principe",      "SOLID SRP — TokenValidator",
                "responsabilite","TokenValidator.validate() = 1 seule responsabilité"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "tokenValide", false,
                "erreur",      e.getMessage()
            ));
        }
    }

    /**
     * TEST 2 : Vérifier l'état d'un token via Token.isSessionActive().
     */
    @GetMapping("/token-state")
    public ResponseEntity<?> tokenState(
            @RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.replace("Bearer ", "").trim();
        return tokenRepository.findByToken(jwt)
            .map(token -> {
                boolean active = token.isSessionActive(); // SRP !
                return ResponseEntity.ok(Map.of(
                    "sessionActive",  active,
                    "expired",        token.isExpired(),
                    "revoked",        token.isRevoked(),
                    "principe",       "SOLID SRP — Token.isSessionActive()",
                    "responsabilite", "Token connaît sa propre validité"
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * TEST 3 : Infos métier de l'utilisateur (séparées de la sécurité).
     */
    @GetMapping("/user-info")
    public ResponseEntity<?> userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = (Utilisateur) auth.getPrincipal();
        return ResponseEntity.ok(Map.of(
            "email",          user.getEmail(),
            "nom",            user.getNomutilisateur(),
            "prenom",         user.getPrenom(),
            "telephone",      user.getTelephone(),
            "principe",       "SOLID SRP — Utilisateur = identité métier",
            "note",           "Utilisateur ne gère PAS la sécurité Spring"
        ));
    }

    private String extractEmail(String jwt) {
        // Extraction simple via JwtService
        // En production, injecter JwtService ici
        return jwt; // placeholder — injecter JwtService dans ce contrôleur
    }
}

