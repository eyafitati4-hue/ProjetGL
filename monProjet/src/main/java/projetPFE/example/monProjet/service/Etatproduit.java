package projetPFE.example.monProjet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.interfac.EtatproduitInter;
import projetPFE.example.monProjet.repository.EtatproduitRepository;

import java.util.List;

@Service
public class Etatproduit implements EtatproduitInter {
    @Autowired
    private EtatproduitRepository etatproduitRepository;

    @Override
    public projetPFE.example.monProjet.model.Etatproduit getById(Integer id) {
        return etatproduitRepository.findById(id).orElse(null);
    }

    @Override
    public List<projetPFE.example.monProjet.model.Etatproduit> getAll() {
        return etatproduitRepository.findAll();
    }

    @Override
    public projetPFE.example.monProjet.model.Etatproduit ajouterEtatproduit(projetPFE.example.monProjet.model.Etatproduit etatproduit) {
        return etatproduitRepository.save(etatproduit);
    }

    @Override
    public projetPFE.example.monProjet.model.Etatproduit modifierEtatproduit(projetPFE.example.monProjet.model.Etatproduit etatproduit) {
        if (etatproduitRepository.existsById(etatproduit.getIdetatproduit())) {
            return etatproduitRepository.save(etatproduit);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerEtatproduit(Integer id) {
        etatproduitRepository.deleteById(id);
    }
}
