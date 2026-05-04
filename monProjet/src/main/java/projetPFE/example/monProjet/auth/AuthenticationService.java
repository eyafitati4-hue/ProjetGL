package projetPFE.example.monProjet.auth;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.config.JwtService;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.RoleRepository;
import projetPFE.example.monProjet.repository.UtilisateurRepository;
import projetPFE.example.monProjet.factory.UserFactory;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import projetPFE.example.monProjet.token.TokenType;
import projetPFE.example.monProjet.exception.UserAlreadyExistsException;
import projetPFE.example.monProjet.exception.AccountInactiveException;

@Service
@Primary
@RequiredArgsConstructor
public class AuthenticationService implements IIdentityService, ISessionService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserFactory userFactory;
    private final RoleRepository roleRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec cet email existe déjà.");
        }

        // Utilisation de la Factory centralisée (GoF)
        var user = userFactory.createUser(
                request,
                passwordEncoder.encode(request.getMotdepasse()));

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getIdRole().getRedirectName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getMotdepasse()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.getIdEtat().getIdEtat() == 1) {
            // Le système appelle simplement la méthode générique (Polymorphisme)
            String role = user.getIdRole().getRedirectName();
            var jwtToken = jwtService.generateToken(user);
            revokedAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(role)
                    .build();
        } else {
            throw new AccountInactiveException("Votre compte est inactif. Veuillez contacter un administrateur.");
        }

    }

    // autre partie pour arriver a rendre les anciens token plus valide (dans la
    // base comme premiere etape)
    private void revokedAllUserTokens(Utilisateur user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdutilisateur());
        if (validUserTokens.isEmpty())
            return;

        // OCL INV-2 enforcement :
        // context Token inv INV2_TokenActifAppartientAUtilisateurActif:
        // self.estTechniquementActif() implies self.utilisateur.idEtat.idEtat = 1
        // → À la connexion, l'utilisateur reçoit un NOUVEAU token.
        // Tous ses anciens tokens ACTIFS doivent être révoqués
        // pour garantir l'unicité de session active.
        System.out.println("[OCL INV-2] Révocation de " + validUserTokens.size()
                + " ancien(s) token(s) pour utilisateur id=" + user.getIdutilisateur());

        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
            // INV-3 automatiquement respecté : expired=true ET revoked=true
        });
        tokenRepository.saveAll(validUserTokens);

        System.out.println("[OCL INV-2] ✓ Postcondition : anciens tokens révoqués");
    }

    // saveUserToken — ajouter log OCL précondition/postcondition
    private void saveUserToken(Utilisateur user, String jwtToken) {
        // OCL pre: utilisateurNonNul + jwtTokenNonVide → vérifiés par Spring Security
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);

        var token = Token.builder()
                .utilisateur(user).token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false).expired(false)
                .expirationDate(expirationDate) // OCL post: expirationDate > now()
                .build();

        // OCL post: tokenInitialementActif — expired=false, revoked=false
        // OCL post: tokenPersistéAvecDateExpiration — expirationDate dans le futur
        System.out.println("[OCL post:saveUserToken] Token créé expirationDate="
                + expirationDate + " expired=false revoked=false ✓");
        tokenRepository.save(token);
    }

    public Utilisateur getUserIdFromToken(String jwtToken) {
        System.out.println(jwtToken + "extract");
        String email = jwtService.extractNomUtilisateur(jwtToken);
        System.out.println(email + "extract user");
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElse(null);
        System.out.println(utilisateur + " user");
        if (utilisateur != null) {
            return utilisateur;
        } else {
            return null;
        }
    }

    @Override
    public boolean isConnected(String token) {
        System.out.println("[AuthService-Mediateur] isConnected()");
        try {
            String email = jwtService.extractNomUtilisateur(token);
            if (email == null)
                return false;
            return tokenRepository.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void invalidateSession(String token) {
        System.out.println("[AuthService-Mediateur] invalidateSession()");
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            tokenRepository.save(t);
        });
    }

    @Override
    public SessionInfo getSessionInfo(String token) {
        System.out.println("[AuthService-Mediateur] getSessionInfo()");
        try {
            String email = jwtService.extractNomUtilisateur(token);
            boolean active = isConnected(token);
            String role = tokenRepository.findByToken(token)
                    .map(t -> t.getUtilisateur().getIdRole().getLabelrole())
                    .orElse("UNKNOWN");
            return SessionInfo.builder()
                    .email(email).role(role).active(active)
                    .sessionType("JWT").build();
        } catch (Exception e) {
            return SessionInfo.builder().active(false).sessionType("JWT").build();
        }
    }

}