package projetPFE.example.monProjet.config.filter;
 
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
 
/**
 * OCL — Moteur d'enforcement de l'invariant INV-1 :
 *   context Token inv INV1_ExpirationCoherente:
 *     self.dateEstDepassee() implies self.expired = true
 *
 * Postcondition garantie après enforceAll() :
 *   Token.allInstances()
 *     ->select(t | t.expirationDate <= DateTime::now() and t.expired = false)
 *     ->isEmpty()
 *
 * Deux modes : @Scheduled (60s) + enforceForToken() à la demande.
 */
@Service
@RequiredArgsConstructor
public class TokenExpirationEnforcer {
 
    private final TokenRepository tokenRepository;
 
    /**
     * Scan planifié — enforce INV-1 sur tous les tokens actifs.
     * Ajouter @EnableScheduling dans MonProjetApplication.java.
     */
    @Scheduled(fixedDelay = 60000)  // toutes les 60 secondes
    @Transactional
    public EnforcementResult enforceAll() {
        System.out.println("[OCL Enforcer] Scan INV-1 démarré...");
 
        // Ne vérifier que les tokens non encore marqués expired ou revoked
        List<Token> actifs = tokenRepository.findAll().stream()
            .filter(t -> !t.isExpired() && !t.isRevoked())
            .toList();
 
        AtomicInteger violations = new AtomicInteger(0);
        actifs.forEach(token -> {
            boolean ok = token.checkAndEnforceInvariant(); // INV-1 + INV-3
            if (!ok) {
                violations.incrementAndGet();
                tokenRepository.save(token); // persister la correction
            }
        });
 
        int total = actifs.size();
        int fixed = violations.get();
        System.out.println("[OCL Enforcer] Scan terminé. Total=" + total
            + " ViolationsCorrigées=" + fixed);
        return new EnforcementResult(total, fixed);
    }
 
    /**
     * Enforcement à la demande — appelé par OclTestController
     * et potentiellement par les filtres de sécurité.
     */
    @Transactional
    public boolean enforceForToken(String tokenStr) {
        return tokenRepository.findByToken(tokenStr)
            .map(token -> {
                boolean ok = token.checkAndEnforceInvariant();
                if (!ok) tokenRepository.save(token);
                return ok;
            })
            .orElse(true); // token inconnu → invariant non applicable
    }
 
    /** DTO résultat du scan OCL — champs alignés sur OclTestController. */
    public record EnforcementResult(
        int totalTokensChecked,
        int violationsFixed
    ) {}
}
