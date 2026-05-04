package projetPFE.example.monProjet.controller;
 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.config.filter.TokenExpirationEnforcer;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
 
@RestController
@RequestMapping("/api/ocl")
@RequiredArgsConstructor
public class OclTestController {
 
    private final TokenExpirationEnforcer enforcer;
    private final TokenRepository         tokenRepository;
 
    /** TEST INV-1 : scan OCL manuel → vérifie tous les tokens */
    @PostMapping("/enforce")
    public ResponseEntity<?> forceEnforce() {
        TokenExpirationEnforcer.EnforcementResult r = enforcer.enforceAll();
        return ResponseEntity.ok(Map.of(
            "invariant",           "INV-1 : dateEstDepassee() implies expired=true",
            "tokensVérifiés",      r.totalTokensChecked(),
            "violationsCorrigées", r.violationsFixed()
        ));
    }
 
    /** TEST INV-1 : force la date d'expiration dans le passé (simulation) */
    @PostMapping("/force-expire/{tokenId}")
    public ResponseEntity<?> forceExpire(@PathVariable Integer tokenId) {
        return tokenRepository.findById(tokenId).map(token -> {
            token.setExpirationDate(LocalDateTime.now().minusMinutes(5));
            tokenRepository.save(token);
            System.out.println("[OCL Test] Token " + tokenId
                + " expirationDate forcée dans le passé (simulation INV-1)");
            return ResponseEntity.ok(Map.of(
                "message",       "Token " + tokenId + " → date dans le passé",
                "expirationDate",  token.getExpirationDate().toString(),
                "étapeSuivante", "POST /api/ocl/enforce pour déclencher INV-1"
            ));
        }).orElse(ResponseEntity.notFound().build());
    }
 
    /** TEST INV-3 : vérifier qu'un token expiré est aussi revoked */
    @GetMapping("/check-inv3")
    public ResponseEntity<?> checkInv3() {
        List<Token> tokens = tokenRepository.findAll();
        long violations = tokens.stream()
            .filter(t -> t.isExpired() && !t.isRevoked())  // INV-3 violé
            .count();
        return ResponseEntity.ok(Map.of(
            "invariant",  "INV-3 : expired=true implies revoked=true",
            "violations", violations,
            "statut",     violations == 0 ? "✓ INV-3 respecté" : "✗ VIOLATIONS détectées"
        ));
    }
 
    /** Status général — état de tous les invariants */
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        List<Token> all = tokenRepository.findAll();
        long actifs  = all.stream().filter(t -> !t.isExpired() && !t.isRevoked()).count();
        long expires = all.stream().filter(Token::isExpired).count();
        long inv3viol = all.stream().filter(t -> t.isExpired() && !t.isRevoked()).count();
        return ResponseEntity.ok(Map.of(
            "total",          all.size(),
            "tokensActifs",   actifs,
            "tokensExpirés",  expires,
            "violationsINV3", inv3viol,
            "heureCourante",  LocalDateTime.now().toString(),
            "invariants", List.of(
                "INV-1: dateEstDepassee() implies expired=true",
                "INV-2: estTechniquementActif() implies utilisateur.actif",
                "INV-3: expired=true implies revoked=true"
            )
        ));
    }
}
