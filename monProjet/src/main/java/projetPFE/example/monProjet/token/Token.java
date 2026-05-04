package projetPFE.example.monProjet.token;
 
import jakarta.persistence.*;
import lombok.*;
import projetPFE.example.monProjet.model.Utilisateur;
import java.time.LocalDateTime;
 
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Token {
 
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String      token;
 
    @Enumerated(EnumType.STRING)
    private TokenType   tokenType;
 
    private boolean     expired;   // flag état
    private boolean     revoked;   // flag révocation
 
    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;
 
    // ── AJOUTÉ pour OCL INV-1 ───────────────────────────────────────
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    // ────────────────────────────────────────────────────────────────
 
    /**
     * SOLID SRP — responsabilité unique : validité de la session.
     * context Token def: estTechniquementActif() = not expired and not revoked
     */
    public boolean isSessionActive() {
        System.out.println("[Token SRP] isSessionActive() expired=" + expired
            + " revoked=" + revoked);
        return !expired && !revoked;
    }
 
    /**
     * OCL — Enforce INV-1 + INV-3 :
     *   INV-1 : context Token inv INV1_ExpirationCoherente:
     *             self.dateEstDepassee() implies self.expired = true
     *   INV-3 : context Token inv INV3_ExpiredImpliesRevoked:
     *             self.expired = true implies self.revoked = true
     *
     * @return true  si l'invariant était déjà respecté (aucune correction)
     *         false si une violation a été détectée et corrigée
     */
    public boolean checkAndEnforceInvariant() {
        if (expirationDate == null) return true; // pas de date → pas d'invariant
 
        boolean dateDepassee = LocalDateTime.now().isAfter(expirationDate);
 
        // Vérif INV-1 : dateEstDepassee() implies expired = true
        if (dateDepassee && !this.expired) {
            System.out.println("[OCL INV-1] VIOLATION token id=" + id
                + " → date dépassée mais expired=false → correction");
            this.expired = true;  // INV-1 : correction
            this.revoked = true;  // INV-3 : expired=true implies revoked=true
            return false;
        }
 
        // Vérif INV-3 : expired = true implies revoked = true
        if (this.expired && !this.revoked) {
            System.out.println("[OCL INV-3] VIOLATION token id=" + id
                + " → expired=true mais revoked=false → correction");
            this.revoked = true;  // INV-3 : correction
            return false;
        }
 
        System.out.println("[OCL INV-1+INV-3] OK token id=" + id
            + " expired=" + expired + " dateDepassee=" + dateDepassee);
        return true;
    }
}
