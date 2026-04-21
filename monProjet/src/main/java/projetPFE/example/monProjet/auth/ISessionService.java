package projetPFE.example.monProjet.auth;
/**
 * GRASP — Indirection
 *
 * Interface médiateur pour la gestion des sessions.
 * L'application demande au médiateur 'Suis-je connecté ?'
 * sans savoir si c'est du JWT, OAuth2 ou des cookies.
 *
 * Avantage : pour passer de JWT à OAuth2, on change
 * seulement SessionServiceImpl, pas le reste du code.
 */
public interface ISessionService {

    /**
     * Vérifie si un token représente une session active.
     * @param token  le token brut (JWT Bearer...)
     * @return true si la session est valide et non expirée
     */
    boolean isConnected(String token);

    /**
     * Invalide (révoque) une session en cours.
     * @param token le token de la session à fermer
     */
    void invalidateSession(String token);

    /**
     * Récupère les informations sur la session active.
     * @param token  le token de session
     * @return SessionInfo avec email, rôle et expiration
     */
    SessionInfo getSessionInfo(String token);
}

