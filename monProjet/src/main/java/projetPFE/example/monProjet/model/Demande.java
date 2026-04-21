package projetPFE.example.monProjet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.decorator.AssuranceDecorator;
import projetPFE.example.monProjet.decorator.DevisBase;
import projetPFE.example.monProjet.decorator.DevisComponent;
import projetPFE.example.monProjet.decorator.FraisDossierDecorator;
import projetPFE.example.monProjet.state.*;

import java.time.LocalDate;

/**
 * Entité Demande enrichie.
 *
 * GRASP – Créateur :
 *   La Demande possède toutes les données nécessaires (produit, client, prix).
 *   Elle est donc la candidate naturelle pour générer son propre Devis via creerDevis().
 *
 * OCL – Postcondition (vérifiée dans creerDevis) :
 *   Le montantFinal stocké dans le Devis doit être strictement positif
 *   et cohérent avec le calcul appliqué.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor(force = true)
@Table(name = "demande", schema = "public")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddemande", nullable = false)
    private Integer idDemande;

    @Column(name = "date")
    private String date;

    @Column(name = "montant")
    private String montant;

    @Column(name = "status")
    private String status;

    @Column(name = "prixproduit")
    private Integer prixproduit;

    @Column(name = "loyer")
    private double loyer;

    @Column(name = "apportpropre")
    private double apportpropre;

    @Column(name = "marque")
    private String marque;

    @Column(name = "produit")
    private String produit;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur idutilisateur;

    @ManyToOne
    @JoinColumn(name = "idetatdemande")
    private Etatdemande idetatdemade;

    @ManyToOne
    @JoinColumn(name = "iddevis")
    private Devi devis;

    @Transient
    private DemandeState currentState;

    /**
     * Initialise l'état du State Pattern après le chargement depuis la DB.
     */
    @PostLoad
    private void initMappingState() {
        if (this.idetatdemade == null) {
            this.currentState = new EtatEnAttente();
            return;
        }

        switch (this.idetatdemade.getIdetatdemande()) {
            case 1 -> this.currentState = new EtatEnAttente();
            case 2 -> this.currentState = new EtatEnCours();
            case 3 -> this.currentState = new EtatValidee();
            case 4 -> this.currentState = new EtatRejetee();
            default -> this.currentState = new EtatEnAttente();
        }
    }

    public void setState(DemandeState state) {
        this.currentState = state;
        // Synchronisation avec l'entité persistante (on suppose les IDs 1, 2, 3, 4)
        if (this.idetatdemade == null) {
            this.idetatdemade = new Etatdemande();
        }
        
        if (state instanceof EtatEnAttente) this.idetatdemade.setIdetatdemande(1);
        else if (state instanceof EtatEnCours) this.idetatdemade.setIdetatdemande(2);
        else if (state instanceof EtatValidee) this.idetatdemade.setIdetatdemande(3);
        else if (state instanceof EtatRejetee) this.idetatdemade.setIdetatdemande(4);
    }

    // Méthodes de transition déléguées
    public void valider() { currentState.valider(this); }
    public void rejeter() { currentState.rejeter(this); }
    public void annuler() { currentState.annuler(this); }
    public void supprimer() { currentState.supprimer(this); }

    public void setDemande(int id) {
        this.idDemande = id;
    }

    // -------------------------------------------------------------------------
    // GRASP – Créateur : la Demande crée son propre Devis
    // -------------------------------------------------------------------------

    /**
     * Crée et retourne un Devis complet à partir des informations de la demande.
     * Applique le pattern Decorator pour ajouter Assurance et Frais de dossier.
     *
     * @param avecAssurance      true si l'option assurance doit être ajoutée
     * @param avecFraisDossier   true si les frais de dossier doivent être ajoutés
     * @return un objet Devi prêt à être persisté
     *
     * OCL – Postcondition : montantFinal > 0 après création.
     */
    public Devi creerDevis(boolean avecAssurance, boolean avecFraisDossier) {
        // Précondition
        if (this.prixproduit == null || this.prixproduit <= 0) {
            throw new IllegalStateException(
                    "Impossible de créer un devis : le prix du produit est invalide (doit être > 0)."
            );
        }
        if (this.idutilisateur == null) {
            throw new IllegalStateException(
                    "Impossible de créer un devis : aucun utilisateur associé à la demande."
            );
        }

        // Construction du Devis de base
        Devi devi = new Devi();
        devi.setDatedevis(LocalDate.now().toString());
        devi.setProduit(this.produit);
        devi.setPrixbase((double) this.prixproduit);

        // Récupération des infos client depuis l'utilisateur
        devi.setNomdev(this.idutilisateur.getUsername());
        devi.setPrenomdev(this.idutilisateur.getPrenom());
        devi.setTelephonedev(Integer.parseInt(this.idutilisateur.getTelephone()));

        // Application du pattern Decorator
        DevisComponent composant = new DevisBase(devi);

        if (avecAssurance) {
            composant = new AssuranceDecorator(composant);
        }
        if (avecFraisDossier) {
            composant = new FraisDossierDecorator(composant);
        }

        double montantCalcule = composant.getMontantTotal();

        // OCL – Postcondition : le montant final doit être positif
        if (montantCalcule <= 0) {
            throw new IllegalStateException(
                    "Postcondition violée : le montant final du devis (" + montantCalcule + ") doit être > 0."
            );
        }

        devi.setMontantFinal(montantCalcule);
        return devi;
    }
}