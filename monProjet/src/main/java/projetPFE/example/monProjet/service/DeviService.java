package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetPFE.example.monProjet.decorator.AssuranceDecorator;
import projetPFE.example.monProjet.decorator.DevisBase;
import projetPFE.example.monProjet.decorator.DevisComponent;
import projetPFE.example.monProjet.decorator.FraisDossierDecorator;
import projetPFE.example.monProjet.document.PdfGenerateurInter;
import projetPFE.example.monProjet.interfac.DeviInter;
import projetPFE.example.monProjet.model.Devi;
import projetPFE.example.monProjet.repository.DeviRepository;

import java.util.List;

/**
 * Service Devi enrichi.
 *
 * Intègre le pattern Decorator pour calculer le montant total du devis
 * selon les options choisies (assurance, frais de dossier).
 *
 * SOLID – SRP : ce service ne génère pas lui-même le PDF ;
 * il délègue cette responsabilité à PdfGenerateurInter.
 *
 * OCL – Postcondition vérifiée dans ajouterDeviAvecOptions()
 */
@Service
public class DeviService implements DeviInter {

    @Autowired
    private DeviRepository deviRepository;

    @Autowired
    private PdfGenerateurInter pdfGenerateur;

    @Override
    public Devi getById(Integer id) {
        return deviRepository.findById(id).orElse(null);
    }

    @Override
    public List<Devi> getAll() {
        return deviRepository.findAll();
    }

    @Override
    public Devi ajouterDevi(Devi devis) {
        return deviRepository.save(devis);
    }

    /**
     * Crée un devis en appliquant les options via le pattern Decorator,
     * puis génère le PDF associé.
     *
     * @param devis            le devis de base
     * @param avecAssurance    true pour ajouter l'option Assurance
     * @param avecFrais        true pour ajouter les Frais de dossier
     * @return le devis persisté avec montantFinal calculé
     *
     * OCL – Postcondition : devi.getMontantFinal() > 0 après retour.
     */
    public Devi ajouterDeviAvecOptions(Devi devis, boolean avecAssurance, boolean avecFrais) {
        // Précondition
        if (devis.getPrixbase() == null || devis.getPrixbase() <= 0) {
            throw new IllegalArgumentException("Le prix de base doit être > 0.");
        }

        // Decorator : composition des options
        DevisComponent composant = new DevisBase(devis);

        if (avecAssurance) {
            composant = new AssuranceDecorator(composant);
        }
        if (avecFrais) {
            composant = new FraisDossierDecorator(composant);
        }

        double montantFinal = composant.getMontantTotal();

        // OCL – Postcondition
        if (montantFinal <= 0) {
            throw new IllegalStateException(
                    "Postcondition violée : le montant final calculé (" + montantFinal + ") doit être > 0."
            );
        }

        devis.setMontantFinal(montantFinal);
        Devi devisSauvegarde = deviRepository.save(devis);

        // Génération du PDF (responsabilité déléguée – SOLID SRP)
        pdfGenerateur.genererPdf(devisSauvegarde);

        return devisSauvegarde;
    }

    @Override
    public Devi modifierDevi(Devi devis) {
        if (deviRepository.existsById(devis.getIdDevis())) {
            return deviRepository.save(devis);
        }
        return null;
    }

    @Override
    public void supprimerDevi(Integer id) {
        deviRepository.deleteById(id);
    }
}