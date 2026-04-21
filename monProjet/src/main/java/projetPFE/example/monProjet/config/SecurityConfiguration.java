package projetPFE.example.monProjet.config;

import jakarta.servlet.Filter;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private  final LogoutHandler LogoutHandler ;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //j'ai une copie d'une ancienne version de cette methode dans note
                // Désactiver CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Autoriser toutes les requêtes vers /api/v1/auth/ sans authentification
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        .requestMatchers("/roles/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Définir la politique de gestion de session sur STATELESS
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configuration du logout
                .logout(logout -> logout
                        // URL de déconnexion
                        .logoutUrl("/api/v1/auth/logout")
                        // Gestionnaire de déconnexion personnalisé
                        // Ajouter un LogoutHandler personnalisé
                        .addLogoutHandler(LogoutHandler )
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Logique à exécuter après la déconnexion réussie
                            SecurityContextHolder.clearContext();
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                );
        return http.build();

    }


/*.logout()
    .logoutUrl("/api/v1/auth/logout")
.addLogaoutHandler(null)
    .logoutSuccessHandler((request,response ,authentication)->SecurityContextHolder.clearContext)*/




}

