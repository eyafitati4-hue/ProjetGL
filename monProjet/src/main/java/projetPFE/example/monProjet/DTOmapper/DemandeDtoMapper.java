package projetPFE.example.monProjet.DTOmapper;
import org.springframework.stereotype.Component;

import projetPFE.example.monProjet.DTO.DemandeDto;
import projetPFE.example.monProjet.model.Demande;
@Component
public class DemandeDtoMapper {
    public static DemandeDto toDto(Demande demande) {
        return DemandeDto.builder()
                .idDemande(demande.getDemande())
                .idutilisateur(UtilisateurDtoMapper.toDto(demande.getUtilisateur()))
                .idetatdemade(EtatdemandeDtoMapper.toDto(demande.getEtatdemande()))
                .iddevis(DeviDtoMapper.toDto(demande.getDevis()))
                .date(demande.getDate())
                .montant(demande.getMontant())
                .status(demande.getStatus())
                .prixproduit(demande.getPrixproduit())
                .loyer(demande.getLoyer())
                .apportpropre(demande.getApportpropre())
                .marque(demande.getMarque())
                .produit(demande.getProduit())
                .build();
    }



    public static Demande toEntity(DemandeDto demandeDto) {
        Demande demande = new Demande();
        demande.setDemande(demandeDto.getIdDemande());
        demande.setUtilisateur(UtilisateurDtoMapper.toEntity(demandeDto.getIdutilisateur()));
        demande.setEtatdemande(EtatdemandeDtoMapper.toEntity(demandeDto.getIdetatdemade()));
        demande.setDevis(DeviDtoMapper.toEntity(demandeDto.getIddevis()));
        demande.setDate(demandeDto.getDate());
        demande.setMontant(demandeDto.getMontant());
        demande.setStatus(demandeDto.getStatus());
        demande.setPrixproduit(demandeDto.getPrixproduit());
        demande.setProduit(demandeDto.getProduit());
        demande.setApportpropre(demandeDto.getApportpropre());
        demande.setMarque(demandeDto.getMarque());
        demande.setLoyer(demandeDto.getLoyer());

        return demande;
    }
}
