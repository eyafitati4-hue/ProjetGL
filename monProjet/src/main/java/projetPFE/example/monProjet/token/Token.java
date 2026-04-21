package projetPFE.example.monProjet.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetPFE.example.monProjet.model.Utilisateur;

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

}
