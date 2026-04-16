package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.EtatutilisateurDto;
import projetPFE.example.monProjet.model.Etatutilisateur;
import projetPFE.example.monProjet.repository.EtatutilisateurRepository;
import projetPFE.example.monProjet.DTOmapper.EtatUtilisateurDtoMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EtatUtilisateur {

    @Autowired
    private EtatutilisateurRepository etatutilisateurRepository;

    public EtatutilisateurDto getById(Integer id) {
        Etatutilisateur etatutilisateur = etatutilisateurRepository.findById(id).orElse(null);
        if (etatutilisateur != null) {
            return EtatUtilisateurDtoMapper.toDto(etatutilisateur);
        }
        return null;
    }

    public Set<EtatutilisateurDto> getAll() {
        List<Etatutilisateur> etatutilisateurs = etatutilisateurRepository.findAll();
        return EtatUtilisateurDtoMapper.toDtoList(new HashSet<>(etatutilisateurs));
    }

    public EtatutilisateurDto ajouterEtatutilisateur(Etatutilisateur etatutilisateur) {
        Etatutilisateur nouvelEtatutilisateur = etatutilisateurRepository.save(etatutilisateur);
        return EtatUtilisateurDtoMapper.toDto(nouvelEtatutilisateur);
    }

    public EtatutilisateurDto modifierEtatutilisateur(Etatutilisateur etatutilisateur) {
        if (etatutilisateurRepository.existsById(etatutilisateur.getIdEtat())) {
            Etatutilisateur etatutilisateurModifie = etatutilisateurRepository.save(etatutilisateur);
            return EtatUtilisateurDtoMapper.toDto(etatutilisateurModifie);
        } else {
            return null;
        }
    }

    public void supprimerEtatutilisateur(Integer id) {
        etatutilisateurRepository.deleteById(id);
    }
}
