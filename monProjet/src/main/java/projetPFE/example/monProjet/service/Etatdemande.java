package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.interfac.EtatdemandeInter;
import projetPFE.example.monProjet.repository.EtatdemandeRepository;

import java.util.List;

@Service
public class Etatdemande implements EtatdemandeInter {
    @Autowired
    private EtatdemandeRepository etatdemandeRepository;

    @Override
    public projetPFE.example.monProjet.model.Etatdemande getById(Integer id) {
        return etatdemandeRepository.findById(id).orElse(null);
    }

    @Override
    public List<projetPFE.example.monProjet.model.Etatdemande> getAll() {
        return etatdemandeRepository.findAll();
    }

    @Override
    public projetPFE.example.monProjet.model.Etatdemande ajouterEtatdemande(projetPFE.example.monProjet.model.Etatdemande etatdemande) {
        return etatdemandeRepository.save(etatdemande);
    }

    @Override
    public projetPFE.example.monProjet.model.Etatdemande modifierEtatdemande(projetPFE.example.monProjet.model.Etatdemande etatdemande) {
        if (etatdemandeRepository.existsById(etatdemande.getIdetatdemande())) {
            return etatdemandeRepository.save(etatdemande);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerEtatdemande(Integer id) {
        etatdemandeRepository.deleteById(id);
    }
}
