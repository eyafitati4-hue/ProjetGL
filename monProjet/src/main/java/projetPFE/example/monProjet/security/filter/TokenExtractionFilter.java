package projetPFE.example.monProjet.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projetPFE.example.monProjet.config.JwtService;

import java.io.IOException;

/**
 * MAILLON 1 — Chain of Responsibility (GoF)
 *
 * Responsabilité UNIQUE : extraire le token JWT du header HTTP
 * et le stocker comme attribut de la requête pour les maillons suivants.
 *
 * Ce maillon ne valide RIEN — il extrait seulement.
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class TokenExtractionFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Pas de token → passer directement au maillon suivant
        // (les routes publiques /api/v1/auth/** n'ont pas de token)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraire le JWT (après "Bearer ")
        String jwt   = authHeader.substring(7);
        String email = null;

        try {
            email = jwtService.extractNomUtilisateur(jwt);
        } catch (Exception e) {
            // Token malformé → pas d'email, le maillon 2 rejettera
            System.out.println("[Chain #1 - Extraction] Token malformé : " + e.getMessage());
        }

        // Stocker dans les attributs de la requête pour le maillon 2
        request.setAttribute("jwt",   jwt);
        request.setAttribute("email", email);

        System.out.println("[Chain #1 - Extraction] JWT extrait, email=" + email);

        // Passer au maillon suivant (TokenValidationFilter)
        filterChain.doFilter(request, response);
    }
}
