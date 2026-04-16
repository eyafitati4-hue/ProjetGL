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
import projetPFE.example.monProjet.repository.EtatutilisateurRepository;
import projetPFE.example.monProjet.repository.RoleRepository;
import projetPFE.example.monProjet.repository.UtilisateurRepository;
import projetPFE.example.monProjet.token.Token;
import projetPFE.example.monProjet.token.TokenRepository;
import projetPFE.example.monProjet.token.TokenType;

//import static jdk.internal.classfile.Classfile.build;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

private final UtilisateurRepository repository;
private final PasswordEncoder passwordEncoder;
private final JwtService jwtService;
private final RoleRepository roleRepository;
private final EtatutilisateurRepository etatRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;



private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Role defaultRole = roleRepository.findById(3).orElseThrow(() -> new RuntimeException("Default role not found"));
        Etatutilisateur defaultEtat = etatRepository.findById(1).orElseThrow(() -> new RuntimeException("Default etat not found"));

        var user = Utilisateur.builder()
             .prenom(request.getPrenom())
             .nomutilisateur(request.getNom())
             .email(request.getEmail())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .ville(request.getAdresse())
                .codepostal(request.getCodepostal())
                .datenaissance(request.getDatenaissance())
             .motdepasse(passwordEncoder.encode(request.getMotdepasse()))
             .idRole(defaultRole)
                .idEtat(defaultEtat)
                .build();
         var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
            String role ="";
            //ajouter pour faire la redirection vers l'interface admin
            if (user.getIdRole().getIdRole() == 1) {
                role="admin";
            } else if (user.getIdRole().getIdRole() == 2) {
                role="concessionnaire";
            }else if (user.getIdRole().getIdRole() == 3) {
                role = "client";
            }
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
