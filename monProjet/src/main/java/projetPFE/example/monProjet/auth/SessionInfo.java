package projetPFE.example.monProjet.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de résultat de session — retourné par le médiateur.
 * Contient les infos nécessaires sans exposer JWT interne.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfo {
    private String  email;
    private String  role;
    private boolean active;
    private String  expiresAt;   // format lisible pour les tests
    private String  sessionType; // "JWT", "OAUTH2", etc.
}
