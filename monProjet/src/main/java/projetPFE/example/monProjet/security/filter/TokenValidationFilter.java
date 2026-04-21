package projetPFE.example.monProjet.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projetPFE.example.monProjet.config.JwtService;
import projetPFE.example.monProjet.token.TokenRepository;

import java.io.IOException;

/**
 * MAILLON 2 — Chain of Responsibility (GoF)
 *
 * Responsabilité UNIQUE : valider le token JWT.
 * Deux vérifications :
 *   1. Signature JWT valide + non expiré (côté cryptographique)
 *   2. Token non révoqué en base de données
 *
 * Si les deux passent → authentifier dans Spring Security.
 * Sinon → rejeter avec 401 UNAUTHORIZED.
 */
@Component
@Order(2)
@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {

    private final JwtService          jwtService;
    private final UserDetailsService   userDetailsService;
    private final TokenRepository      tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Récupérer ce que le maillon 1 a extrait
        String jwt   = (String) request.getAttribute("jwt");
        String email = (String) request.getAttribute("email");

        // Maillon non concerné : pas de token extrait
        if (jwt == null || email == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Pas besoin de valider si déjà authentifié
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            System.out.println("[Chain #2 - Validation] Utilisateur introuvable : " + email);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Utilisateur introuvable\"}");
            return;
        }

        // Vérification 1 : signature JWT + expiration cryptographique
        boolean jwtValide = jwtService.isTokenValid(jwt, userDetails);

        // Vérification 2 : token présent et non révoqué en base
        boolean dbValide = tokenRepository.findByToken(jwt)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        System.out.println("[Chain #2 - Validation] jwtValide=" + jwtValide
                + ", dbValide=" + dbValide + " pour email=" + email);

        if (jwtValide && dbValide) {
            // Authentifier dans le contexte Spring Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            System.out.println("[Chain #2 - Validation] ✓ Token VALIDE pour : " + email);
            // Passer au maillon 3
            filterChain.doFilter(request, response);

        } else {
            // BLOQUER la chaîne — 401 Unauthorized
            System.out.println("[Chain #2 - Validation] ✗ Token INVALIDE ou révoqué");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"error\":\"Token invalide ou révoqué\",\"code\":401}");
            // Ne PAS appeler filterChain.doFilter → chaîne bloquée
        }
    }
}
