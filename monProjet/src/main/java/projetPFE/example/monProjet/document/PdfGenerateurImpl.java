package projetPFE.example.monProjet.document;

import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.model.Devi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Implémentation du générateur PDF.
 *
 * SOLID – SRP :
 *   Cette classe a UNE SEULE responsabilité : produire le fichier PDF.
 *   Elle ne connaît pas la base de données, les DTOs ni les contrôleurs.
 *
 * OCL – Postcondition :
 *   Après genererPdf(), le fichier doit exister et le montant imprimé
 *   doit correspondre exactement à devi.getMontantFinal().
 *
 * NOTE : L'implémentation ci-dessous génère un fichier texte simulant un PDF.
 *        En production, remplacez par iText ou Apache PDFBox.
 */
@Component
public class PdfGenerateurImpl implements PdfGenerateurInter {

    private static final String DOSSIER_SORTIE = "devis-pdf/";

    @Override
    public String genererPdf(Devi devi) {
        // Préconditions
        if (devi == null) {
            throw new IllegalArgumentException("Le devis ne peut pas être null.");
        }
        if (devi.getMontantFinal() == null || devi.getMontantFinal() <= 0) {
            throw new IllegalStateException(
                    "Impossible de générer le PDF : le montant final est invalide (" + devi.getMontantFinal() + ")."
            );
        }

        // Création du dossier de sortie si nécessaire
        File dossier = new File(DOSSIER_SORTIE);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        String nomFichier = DOSSIER_SORTIE + "devis_" + devi.getIdDevis()
                + "_" + LocalDateTime.now().toLocalDate() + ".pdf";

        // Contenu du document (simulé – remplacer par iText/PDFBox en production)
        String contenu = construireContenu(devi);

        try (FileWriter writer = new FileWriter(nomFichier)) {
            writer.write(contenu);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF : " + e.getMessage(), e);
        }

        // OCL – Postcondition : vérification que le fichier existe réellement
        verifierPostcondition(nomFichier, devi.getMontantFinal());

        return nomFichier;
    }

    // -------------------------------------------------------------------------
    // Méthodes privées
    // -------------------------------------------------------------------------

    private String construireContenu(Devi devi) {
        return """
                ====================================
                           DEVIS OFFICIEL
                ====================================
                Client     : %s %s
                Téléphone  : %s
                Ville      : %s
                Produit    : %s
                Date       : %s
                ------------------------------------
                Prix de base   : %.2f DT
                Montant final  : %.2f DT
                ====================================
                """.formatted(
                devi.getNomdev(),
                devi.getPrenomdev(),
                devi.getTelephonedev(),
                devi.getVilledev(),
                devi.getProduit(),
                devi.getDatedevis(),
                devi.getPrixbase(),
                devi.getMontantFinal()
        );
    }

    /**
     * OCL – Postcondition :
     * Vérifie que le fichier a bien été créé sur le disque.
     * En production, vous pouvez aussi lire le PDF généré et vérifier
     * que le montant imprimé correspond au montant en base.
     */
    private void verifierPostcondition(String cheminFichier, double montantAttendu) {
        File fichier = new File(cheminFichier);
        if (!fichier.exists() || fichier.length() == 0) {
            throw new IllegalStateException(
                    "Postcondition violée : le fichier PDF '" + cheminFichier + "' n'a pas été créé correctement."
            );
        }
        // Dans une implémentation iText/PDFBox, on pourrait relire le PDF
        // et vérifier que le montant inscrit == montantAttendu.
        // Exemple : assert lireMontantDuPdf(cheminFichier) == montantAttendu;
    }
}