package projetPFE.example.monProjet.decorator;

/**
 * Décorateur abstrait – délègue à l'objet enveloppé.
 * Toutes les options héritent de cette classe.
 *
 * GoF – Decorator (Decorator abstrait)
 */
public abstract class DevisDecorator implements DevisComponent {

    protected final DevisComponent devisComponent;

    public DevisDecorator(DevisComponent devisComponent) {
        if (devisComponent == null) {
            throw new IllegalArgumentException("Le composant décoré ne peut pas être null.");
        }
        this.devisComponent = devisComponent;
    }

    @Override
    public double getMontantTotal() {
        return devisComponent.getMontantTotal();
    }

    @Override
    public String getDescription() {
        return devisComponent.getDescription();
    }
}