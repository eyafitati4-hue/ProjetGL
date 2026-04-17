package projetPFE.example.monProjet.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.config.JwtService;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;
import projetPFE.example.monProjet.repository.UtilisateurRepository;
import projetPFE.example.monProjet.factory.UserFactory;
import projetPFE.example.monProjet.model.RoleType;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import projetPFE.example.monProjet.token.TokenType;
import projetPFE.example.monProjet.exception.UserAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IIdentityService {

private final UtilisateurRepository repository;
private final PasswordEncoder passwordEncoder;
private final JwtService jwtService;
private final UserFactory userFactory;
    @Autowired
    private UtilisateurRepository utilisateurRepository;



private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec cet email existe déjà.");
        }
        var user = userFactory.createUser(
                request, 
                passwordEncoder.encode(request.getMotdepasse()), 
                RoleType.CLIENT
        );
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
                request.getMotdepasse()
        )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.getIdEtat().getIdEtat()==1) {
            // Le système appelle simplement la méthode générique (Polymorphisme)
            String role = user.getIdRole().getRedirectName();
            var jwtToken = jwtService.generateToken(user);
            revokedAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .role(role)
                    .build();
        }else{
            throw new RuntimeException("Votre Compte est inactif");
        }

    }

    //autre partie pour arriver a rendre les anciens token plus valide (dans la base comme premiere etape)
    private void revokedAllUserTokens( Utilisateur user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdutilisateur());
        if (validUserTokens.isEmpty())
            return; // early return
        validUserTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    private void saveUserToken(Utilisateur user, String jwtToken) {
        var token = Token.builder()
                .utilisateur(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        System.out.println(token);
        tokenRepository.save(token);

    }

    public Utilisateur getUserIdFromToken(String jwtToken) {
        System.out.println(jwtToken+"extract");
        String email = jwtService.extractNomUtilisateur(jwtToken);
        System.out.println(email+"extract user");
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElse(null);
        System.out.println(utilisateur+" user");
        if (utilisateur != null) {
            return utilisateur;
        } else {
            return null;
        }
    }
}
