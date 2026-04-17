package projetPFE.example.monProjet.auth;

import projetPFE.example.monProjet.model.Utilisateur;

public interface IIdentityService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Utilisateur getUserIdFromToken(String jwtToken);
}
