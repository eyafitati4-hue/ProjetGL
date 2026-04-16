package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.DTO.MarqueDto;
import projetPFE.example.monProjet.DTOmapper.MarqueDtoMapper;
import projetPFE.example.monProjet.interfac.MarqueInter;
import projetPFE.example.monProjet.repository.MarqueRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Marque implements MarqueInter {


    @Autowired
    private MarqueRepository marqueRepository;

    @Override
    public MarqueDto getById(Integer id) {
        Optional<projetPFE.example.monProjet.model.Marque> optionalMarque = marqueRepository.findById(id);
        return optionalMarque.map(MarqueDtoMapper::toDto).orElse(null);
    }

    @Override
    public List<MarqueDto> getAll() {
        List<projetPFE.example.monProjet.model.Marque> marques = marqueRepository.findAll();
        return marques.stream()
                .map(MarqueDtoMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<MarqueDto> getAllvalider() {
        List<projetPFE.example.monProjet.model.Marque> marques = marqueRepository.findAll();
        return marques.stream()
                .filter(marque -> marque.getEtatmarque() == 1) // Filtrer les marques avec etatmarque == 1
                .map(MarqueDtoMapper::toDto)
                .collect(Collectors.toList());
    }
@Override
    public MarqueDto modifierEtatMarque(Integer id, Integer nouvelEtat) {
    Optional<projetPFE.example.monProjet.model.Marque> marqueOptional = marqueRepository.findById(id);
    if (marqueOptional.isPresent()) {
        projetPFE.example.monProjet.model.Marque marque = marqueOptional.get();
        marque.setEtatmarque(nouvelEtat);
        marqueRepository.save(marque);
        return MarqueDtoMapper.toDto(marque);
    } else {
        System.out.println("erreur lors de la modification de l'etat marque ");
    }
    return null;
}


    public MarqueDto ajouterMarque(MarqueDto marqueDto) {
        projetPFE.example.monProjet.model.Marque marque = MarqueDtoMapper.toEntity(marqueDto);
        return MarqueDtoMapper.toDto(marqueRepository.save(marque));
    }
  /*  public MarqueDto ajouterMarqueconc(MarqueDto marqueDto) {
        projetPFE.example.monProjet.model.Marque marque = MarqueDtoMapper.toEntity(marqueDto);
        marque.setEtatmarque(2);
        return MarqueDtoMapper.toDto(marqueRepository.save(marque));
    }
*/
 /*   public MarqueDto modifierMarque(MarqueDto marqueDto) {
        projetPFE.example.monProjet.model.Marque marque = MarqueDtoMapper.toEntity(marqueDto);
        return MarqueDtoMapper.toDto(marqueRepository.save(marque));
    }*/


    @Override
    public MarqueDto modifierMarque(Integer id, MarqueDto newMarqueDto) {
        Optional<projetPFE.example.monProjet.model.Marque> optionalMarque = marqueRepository.findById(id);
        if (optionalMarque.isPresent()) {
            projetPFE.example.monProjet.model.Marque marque = optionalMarque.get();
            // Mettre à jour les champs de la marque avec les nouvelles valeurs
            marque.setNommarque(newMarqueDto.getNommarque());
            marque.setLogomarque(newMarqueDto.getLogomarque());
            marque.setEtatmarque(newMarqueDto.getEtatmarque());
            // Ajoutez d'autres mises à jour de champ si nécessaire
            // Enregistrez les modifications dans la base de données
            marqueRepository.save(marque);

            // Retournez la marque modifiée convertie en DTO
            return MarqueDtoMapper.toDto(marque);
        } else {
            // Si la marque avec l'ID spécifié n'est pas trouvée, lancez une exception appropriée
            throw new IllegalArgumentException("Marque non trouvée avec l'ID : " + id);
        }
    }

    @Override
    public void supprimerMarque(Integer id) {
        marqueRepository.deleteById(id);
    }
}
