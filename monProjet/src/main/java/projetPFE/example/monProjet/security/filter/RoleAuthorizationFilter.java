package projetPFE.example.monProjet.security.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * MAILLON 3 — Chain of Responsibility (GoF)
 *
 * Responsabilité UNIQUE : vérifier que l'utilisateur authentifié
 * possède le rôle requis pour l'URL demandée.
 *
 * Rôles du projet (DataInitializer) :
 *   ADMIN          → id=1
 *   CONCESSIONNAIRE → id=2
 *   CLIENT         → id=3
 */
@Component
@Order(3)
public class RoleAuthorizationFilter extends OncePerRequestFilter {

    /**
     * Carte des routes protégées → rôles autorisés.
     * Adaptée aux rôles réels de votre DataInitializer.
     */
    private static final Map<String, List<String>> ROLE_MAP = Map.of(
        "/api/test/admin",          List.of("ADMIN"),
        "/api/test/concessionnaire", List.of("ADMIN", "CONCESSIONNAIRE"),
        "/api/test/client",         List.of("ADMIN", "CONCESSIONNAIRE", "CLIENT")
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (Map.Entry<String, List<String>> entry : ROLE_MAP.entrySet()) {
            if (path.startsWith(entry.getKey())) {

                if (auth == null || !hasRole(auth, entry.getValue())) {
                    String userRole = auth != null
                            ? auth.getAuthorities().toString()
                            : "non authentifié";
                    System.out.println("[Chain #3 - Role] ✗ Accès refusé à "
                            + path + " | Rôle actuel : " + userRole);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"error\":\"Accès interdit : rôle insuffisant\",\"code\":403}");
                    return; // BLOQUER
                }

                System.out.println("[Chain #3 - Role] ✓ Accès autorisé à "
                        + path + " pour rôle : "
                        + auth.getAuthorities());
            }
        }

        // Passer au contrôleur
        filterChain.doFilter(request, response);
    }

    private boolean hasRole(Authentication auth, List<String> requiredRoles) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> requiredRoles.contains(a.getAuthority()));
    }
}

