package projetPFE.example.monProjet.token;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetPFE.example.monProjet.model.Utilisateur;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    /**
     * SOLID — SRP : Token est responsable de sa propre validité.
     * Cette méthode exprime la règle métier du token.
     * Avant : la logique était dispersée dans JwtAuthenticationFilter.
     *
     * @return true si le token représente une session active
     */
    public boolean isSessionActive() {
        System.out.println("[Token SRP] isSessionActive() → expired="
                + expired + " revoked=" + revoked);
        return !expired && !revoked;
    }

    /**
     * OCL — Invariant : context Token inv tokenExpirationInvariant
     * self.expirationDate > Date::now() implies not self.expired
     *
     * Vérifie l'invariant : si date expirée, mettre expired=true.
     * 
     * @return true si l'invariant est respecté AVANT correction
     *         false si l'invariant était violé (correction appliquée)
     */
    public boolean checkAndEnforceInvariant() {
        if (expirationDate == null)
            return true; // pas de date = pas d'invariant
        boolean dateExpired = LocalDateTime.now().isAfter(expirationDate);
        if (dateExpired && !this.expired) {
            // INVARIANT VIOLÉ : dateExpiration <= maintenant ET expired=false
            System.out.println("[OCL Invariant] VIOLATION détectée token id="
                    + id + " → désactivation automatique");
            this.expired = true;
            this.revoked = true;
            return false; // invariant était violé → corrigé
        }
        System.out.println("[OCL Invariant] OK token id=" + id
                + " dateExpired=" + dateExpired + " expired=" + expired);
        return true; // invariant respecté
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime d) {
        this.expirationDate = d;
    }

}
