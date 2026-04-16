package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.DTO.EtatutilisateurDto;
import projetPFE.example.monProjet.model.Etatutilisateur;

import java.util.List;
import java.util.Set;

public interface EtatutilisateurInter {
    EtatutilisateurDto getById(Integer id);

    Set<EtatutilisateurDto> getAll();

    EtatutilisateurDto ajouterEtatutilisateur(Etatutilisateur etatutilisateur);

    EtatutilisateurDto modifierEtatutilisateur(Etatutilisateur etatutilisateur);

    void supprimerEtatutilisateur(Integer id);
}
