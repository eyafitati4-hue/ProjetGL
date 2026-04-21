package projetPFE.example.monProjet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetPFE.example.monProjet.auth.ISessionService;
import projetPFE.example.monProjet.auth.SessionInfo;

import java.util.Map;

/**
 * Contrôleur de test pour démontrer GRASP Indirection.
 * Utilise UNIQUEMENT ISessionService (le médiateur).
 * Ne connaît PAS JwtService ni TokenRepository.
 */
@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionTestController {

    // INJECTION DE L'INTERFACE, pas de l'implémentation !
    // C'est le principe d'Indirection : on depend de l'abstraction
    private final ISessionService sessionService;

    /**
     * TEST 1 : Vérifier si une session est active.
     * Le contrôleur demande au médiateur sans savoir comment c'est fait.
     */
    @GetMapping("/status")
    public ResponseEntity<?> checkSession(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        boolean connected = sessionService.isConnected(token);
        return ResponseEntity.ok(Map.of(
            "connected",    connected,
            "principe",     "GRASP Indirection",
            "description",  "Réponse via médiateur ISessionService"
        ));
    }

    /**
     * TEST 2 : Obtenir les infos de session.
     */
    @GetMapping("/info")
    public ResponseEntity<?> sessionInfo(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        SessionInfo info = sessionService.getSessionInfo(token);
        return ResponseEntity.ok(Map.of(
            "session",   info,
            "principe",  "GRASP Indirection",
            "mediateur", "ISessionService → SessionServiceImpl (JWT)"
        ));
    }

    /**
     * TEST 3 : Invalider une session via médiateur.
     */
    @PostMapping("/invalidate")
    public ResponseEntity<?> invalidate(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        sessionService.invalidateSession(token);
        return ResponseEntity.ok(Map.of(
            "message",   "Session invalidée via médiateur",
            "principe",  "GRASP Indirection",
            "avantage",  "Zero couplage direct JWT dans ce contrôleur"
        ));
    }
}
