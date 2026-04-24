package projetPFE.example.monProjet.factory;

import projetPFE.example.monProjet.auth.RegisterRequest;
import projetPFE.example.monProjet.model.Utilisateur;

public interface IUserFactory {
    Utilisateur createUser(RegisterRequest request, String encodedPassword);
}
