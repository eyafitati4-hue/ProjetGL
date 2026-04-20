package projetPFE.example.monProjet.decorator;

/**
 * Décorateur concret : ajoute une option Assurance au devis.
 * Le taux d'assurance est paramétrable à la construction.
 *
 * GoF – Decorator (ConcreteDecorator)
 */
public class AssuranceDecorator extends DevisDecorator {

    private static final double TAUX_ASSURANCE_DEFAUT = 0.03; // 3%
    private final double tauxAssurance;

    public AssuranceDecorator(DevisComponent devisComponent) {
        this(devisComponent, TAUX_ASSURANCE_DEFAUT);
    }

    public AssuranceDecorator(DevisComponent devisComponent, double tauxAssurance) {
        super(devisComponent);
        if (tauxAssurance < 0 || tauxAssurance > 1) {
            throw new IllegalArgumentException("Le taux d'assurance doit être compris entre 0 et 1.");
        }
        this.tauxAssurance = tauxAssurance;
    }

    @Override
    public double getMontantTotal() {
        double base = devisComponent.getMontantTotal();
        return base + (base * tauxAssurance);
    }

    @Override
    public String getDescription() {
        return devisComponent.getDescription()
                + " + Assurance (" + (tauxAssurance * 100) + "%)";
    }
}