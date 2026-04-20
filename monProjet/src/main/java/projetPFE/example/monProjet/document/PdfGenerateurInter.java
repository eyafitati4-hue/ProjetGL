package projetPFE.example.monProjet.document;

import projetPFE.example.monProjet.model.Devi;

/**
 * Interface du moteur de génération PDF.
 *
 * SOLID – SRP :
 *   Cette interface sépare la responsabilité technique de génération PDF
 *   de l'entité Document qui ne stocke que les métadonnées.
 *   Si l'outil PDF change (iText → Apache PDFBox), seule l'implémentation change,
 *   jamais la base de données ni l'entité Document.
 */
public interface PdfGenerateurInter {

    /**
     * Génère un fichier PDF pour le devis donné.
     *
     * @param devi le devis à mettre en document
     * @return le chemin absolu du fichier PDF généré
     *
     * OCL – Postcondition : le fichier existe sur le disque après l'appel.
     */
    String genererPdf(Devi devi);
}