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
 * OCL — Invariant : Moteur de vérification.
 *
 * Vérifie en permanence que la date d'expiration de chaque token
 * est supérieure à l'heure actuelle. Tout token expiré est
 * désactivé immédiatement.
 *
 * Invariant OCL :
 *   context Token inv tokenExpirationInvariant:
 *     self.expirationDate > Date::now() implies not self.expired
 *
 * Fréquence : toutes les 60 secondes + appel à la demande.
 */
@Service
@RequiredArgsConstructor
public class TokenExpirationEnforcer {

    private final TokenRepository tokenRepository;

    /**
     * Vérification planifiée : toutes les 60 secondes.
     * Spring Boot démarre le scheduler automatiquement.
     * Ajouter @EnableScheduling dans MonProjetApplication.
     */
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public EnforcementResult enforceAll() {
        System.out.println("[OCL Enforcer] Scan de tous les tokens actifs...");

        List<Token> activeTokens = tokenRepository.findAll().stream()
                .filter(t -> !t.isExpired() && !t.isRevoked())
                .toList();

        AtomicInteger violations = new AtomicInteger(0);

        activeTokens.forEach(token -> {
            boolean invariantOk = token.checkAndEnforceInvariant();
            if (!invariantOk) {
                violations.incrementAndGet();
                tokenRepository.save(token); // Persister la correction
            }
        });

        int total  = activeTokens.size();
        int fixed  = violations.get();
        System.out.println("[OCL Enforcer] Scan terminé. Total=" + total
            + " Violations corrigées=" + fixed);

        return new EnforcementResult(total, fixed);
    }

    /**
     * Vérifier l'invariant pour un token spécifique.
     * Appelé par les filtres à chaque requête.
     */
    @Transactional
    public boolean enforceForToken(String tokenStr) {
        return tokenRepository.findByToken(tokenStr)
            .map(token -> {
                boolean ok = token.checkAndEnforceInvariant();
                if (!ok) tokenRepository.save(token);
                return ok;
            })
            .orElse(true); // token inconnu → pas d'invariant à vérifier
    }

    /**
     * DTO résultat du scan OCL.
     */
    public record EnforcementResult(
        int totalTokensChecked,
        int violationsFixed
    ) {}
}