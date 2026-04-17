package projetPFE.example.monProjet.interfac;
import projetPFE.example.monProjet.DTO.MarqueDto;
import projetPFE.example.monProjet.model.Marque;

import java.util.List;
public interface MarqueService {
    MarqueDto getById(Integer id);

    List<MarqueDto> getAll();

    List<MarqueDto> getAllvalider();

    MarqueDto modifierEtatMarque(Integer id, Integer nouvelEtat);

    MarqueDto ajouterMarque(MarqueDto marqueDto);


    MarqueDto modifierMarque(Integer id, MarqueDto newMarqueDto);

    void supprimerMarque(Integer id);
}
