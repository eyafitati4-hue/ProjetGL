package projetPFE.example.monProjet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.config.filter.TokenExpirationEnforcer;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Contrôleur de test pour démontrer OCL Invariant.
 * Permet de déclencher manuellement le moteur OCL et de vérifier.
 */
@RestController
@RequestMapping("/api/ocl")
@RequiredArgsConstructor
public class OclTestController {

    private final TokenExpirationEnforcer enforcer;
    private final TokenRepository         tokenRepository;

    /**
     * TEST 1 : Déclencher le scan OCL manuellement.
     */
    @PostMapping("/enforce")
    public ResponseEntity<?> forceEnforce() {
        System.out.println("[OCL Test] Scan manuel déclenché");
        TokenExpirationEnforcer.EnforcementResult result = enforcer.enforceAll();
        return ResponseEntity.ok(Map.of(
            "tokensVérifiés",     result.totalTokensChecked(),
            "violationsCorrigées", result.violationsFixed(),
            "principe",           "OCL Invariant",
            "invariant",          "expirationDate > now() implies not expired"
        ));
    }

    /**
     * TEST 2 : Forcer l'expiration d'un token (simulation test).
     * Met expirationDate dans le passé pour déclencher l'invariant.
     */
    @PostMapping("/force-expire/{tokenId}")
    public ResponseEntity<?> forceExpire(@PathVariable Integer tokenId) {
        return tokenRepository.findById(tokenId)
            .map(token -> {
                // Simuler expiration : mettre la date dans le passé
                token.setExpirationDate(LocalDateTime.now().minusMinutes(5));
                tokenRepository.save(token);
                System.out.println("[OCL Test] Token " + tokenId
                    + " expirationDate forcée dans le passé");
                return ResponseEntity.ok(Map.of(
                    "message",        "Token " + tokenId + " expirationDate → passé",
                    "expirationDate",  token.getExpirationDate().toString(),
                    "note",           "Appeler POST /api/ocl/enforce pour déclencher l'invariant"
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * TEST 3 : Vérifier les tokens actifs en base.
     */
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        var tokens = tokenRepository.findAll();
        long active  = tokens.stream().filter(t->!t.isExpired()&&!t.isRevoked()).count();
        long expired = tokens.stream().filter(t->t.isExpired()).count();
        return ResponseEntity.ok(Map.of(
            "totalTokens",   tokens.size(),
            "tokensActifs",  active,
            "tokensExpirés", expired,
            "heureCourante", LocalDateTime.now().toString(),
            "invariant",     "OCL vérifie automatiquement toutes les 60s"
        ));
    }
}