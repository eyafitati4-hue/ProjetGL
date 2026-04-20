package projetPFE.example.monProjet.decorator;

import projetPFE.example.monProjet.model.Devi;

/**
 * Composant concret de base du pattern Decorator.
 * Enveloppe un objet Devi et expose son montant de base.
 *
 * GoF – Decorator (ConcreteComponent)
 */
public class DevisBase implements DevisComponent {

    private final Devi devi;

    public DevisBase(Devi devi) {
        if (devi == null) {
            throw new IllegalArgumentException("Le devis ne peut pas être null.");
        }
        this.devi = devi;
    }

    @Override
    public double getMontantTotal() {
        // Le prix de base du produit associé au devis
        return devi.getPrixbase() != null ? devi.getPrixbase() : 0.0;
    }

    @Override
    public String getDescription() {
        return "Devis de base [" + devi.getNomdev() + " " + devi.getPrenomdev() + "]"
                + " - Produit : " + devi.getProduit();
    }

    public Devi getDevi() {
        return devi;
    }
}