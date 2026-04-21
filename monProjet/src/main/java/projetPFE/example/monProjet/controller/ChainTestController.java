package projetPFE.example.monProjet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Contrôleur de test pour le principe GoF Chain of Responsibility.
 *
 * 3 endpoints protégés par rôle différent :
 *   /api/test/admin          → ADMIN seulement
 *   /api/test/concessionnaire → ADMIN + CONCESSIONNAIRE
 *   /api/test/client          → ADMIN + CONCESSIONNAIRE + CLIENT
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChainTestController {

    /**
     * Route accessible uniquement aux ADMIN.
     * Maillon 3 bloquera CLIENT et CONCESSIONNAIRE.
     */
    @GetMapping("/admin")
    public ResponseEntity<?> adminOnly() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
            "message", "✅ Accès ADMIN autorisé — Chaîne complète validée",
            "user",    auth.getName(),
            "roles",   auth.getAuthorities().toString(),
            "maillon", "[Chain #1 Extraction] → [Chain #2 Validation] → [Chain #3 Rôle ADMIN]"
        ));
    }

    /**
     * Route accessible aux ADMIN et CONCESSIONNAIRE.
     * Maillon 3 bloquera CLIENT.
     */
    @GetMapping("/concessionnaire")
    public ResponseEntity<?> concessionnaireAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
            "message", "✅ Accès CONCESSIONNAIRE autorisé",
            "user",    auth.getName(),
            "roles",   auth.getAuthorities().toString(),
            "maillon", "[Chain #3 Rôle CONCESSIONNAIRE ou ADMIN]"
        ));
    }

    /**
     * Route accessible à tous les rôles authentifiés.
     * Maillon 2 bloquera si token invalide.
     */
    @GetMapping("/client")
    public ResponseEntity<?> clientAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
            "message", "✅ Accès CLIENT autorisé",
            "user",    auth.getName(),
            "roles",   auth.getAuthorities().toString(),
            "maillon", "[Chain #3 Rôle CLIENT, CONCESSIONNAIRE ou ADMIN]"
        ));
    }

    /**
     * Route publique — test que les routes /api/v1/auth/** sont accessibles.
     */
    @GetMapping("/public")
    public ResponseEntity<?> publicRoute() {
        return ResponseEntity.ok(Map.of(
            "message", "Route publique — pas de token requis",
            "info",    "Le maillon 1 passe directement si pas de header"
        ));
    }
}

