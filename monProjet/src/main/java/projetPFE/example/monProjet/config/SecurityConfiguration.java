package projetPFE.example.monProjet.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import projetPFE.example.monProjet.security.filter.TokenValidationFilter;
import projetPFE.example.monProjet.security.filter.TokenExtractionFilter;
import projetPFE.example.monProjet.security.filter.RoleAuthorizationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // ── Les 3 maillons de la chaîne (injectés par Spring) ───────────────
    private final TokenExtractionFilter   tokenExtractionFilter;
    private final TokenValidationFilter   tokenValidationFilter;
    private final RoleAuthorizationFilter roleAuthorizationFilter;

    // ── Fournisseur d'authentification (défini dans ApplicatioConfig) ───
    private final AuthenticationProvider authenticationProvider;

    // ── Handler de déconnexion (LogoutService) ───────────────────────────
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Désactiver CSRF (API REST stateless)
            .csrf(AbstractHttpConfigurer::disable)

            // 2. Règles d'autorisation
            .authorizeHttpRequests(auth -> auth
                // Routes publiques : register + authenticate
                .requestMatchers("/api/v1/auth/**").permitAll()
                // Toutes les autres routes nécessitent un token valide
                .anyRequest().authenticated()
            )

            // 3. Session STATELESS (JWT, pas de session HTTP)
            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 4. Fournisseur d'authentification DAO
            .authenticationProvider(authenticationProvider)

            // ════════════════════════════════════════════════════════════
            // 5. CHAÎNE — Maillon 1 → 2 → 3
            // ════════════════════════════════════════════════════════════
            .addFilterBefore(tokenExtractionFilter,
                             UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(tokenValidationFilter,
                            TokenExtractionFilter.class)
            .addFilterAfter(roleAuthorizationFilter,
                            TokenValidationFilter.class)

            // 6. Déconnexion
            .logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_OK);
                })
            );

        return http.build();
    }
}

