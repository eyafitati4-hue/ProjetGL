package projetPFE.example.monProjet.decorator;

/**
 * Décorateur concret : ajoute des frais de dossier fixes au devis.
 *
 * GoF – Decorator (ConcreteDecorator)
 */
public class FraisDossierDecorator extends DevisDecorator {

    private static final double FRAIS_DEFAUT = 150.0; // en dinars
    private final double fraisDossier;

    public FraisDossierDecorator(DevisComponent devisComponent) {
        this(devisComponent, FRAIS_DEFAUT);
    }

    public FraisDossierDecorator(DevisComponent devisComponent, double fraisDossier) {
        super(devisComponent);
        if (fraisDossier < 0) {
            throw new IllegalArgumentException("Les frais de dossier ne peuvent pas être négatifs.");
        }
        this.fraisDossier = fraisDossier;
    }

    @Override
    public double getMontantTotal() {
        return devisComponent.getMontantTotal() + fraisDossier;
    }

    @Override
    public String getDescription() {
        return devisComponent.getDescription()
                + " + Frais de dossier (" + fraisDossier + " DT)";
    }
}