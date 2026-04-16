package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.interfac.DeviInter;
import projetPFE.example.monProjet.repository.DeviRepository;
import java.util.List;

@Service
public class Devi implements DeviInter {

    @Autowired
    private DeviRepository deviRepository;

    @Override
    public projetPFE.example.monProjet.model.Devi getById(Integer id) {
        return deviRepository.findById(id).orElse(null);
    }

    @Override
    public List<projetPFE.example.monProjet.model.Devi> getAll() {
        return deviRepository.findAll();
    }

    @Override
    public projetPFE.example.monProjet.model.Devi ajouterDevi(projetPFE.example.monProjet.model.Devi devis) {
        return deviRepository.save(devis);
    }

    @Override
    public projetPFE.example.monProjet.model.Devi modifierDevi(projetPFE.example.monProjet.model.Devi devis) {
        if (deviRepository.existsById(devis.getIdDevis())) {
            return deviRepository.save(devis);
        } else {
            return null;
        }
    }


    @Override
    public void supprimerDevi(Integer id) {
        deviRepository.deleteById(id);
    }


}
