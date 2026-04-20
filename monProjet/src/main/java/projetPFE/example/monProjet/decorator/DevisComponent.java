package projetPFE.example.monProjet.decorator;


/**
 * Interface de base pour le pattern Decorator appliqué au Devis.
 * Permet d'ajouter des couches optionnelles (Assurance, Frais de dossier)
 * sans modifier la structure de l'entité Devi.
 *
 * GoF – Decorator
 */
public interface DevisComponent {
    double getMontantTotal();
    String getDescription();
}
