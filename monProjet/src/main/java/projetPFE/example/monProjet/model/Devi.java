package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "devis", schema = "public")
public class Devi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddevis", nullable = false)
    private Integer idDevis;

    @Column(name = "datedevis")
    private String datedevis;

    @Column(name = "nomdev")
    private String nomdev;

    @Column(name = "prenomdev")
    private String prenomdev;

    @Column(name = "telephonedev")
    private Integer telephonedev;

    @Column(name = "villedev")
    private String villedev;

    @Column(name = "produit")
    private String produit;

    @Column(name = "prixbase")
    private Double prixbase;

    @Column(name = "montantfinal")
    private Double montantFinal;



    @OneToMany(mappedBy = "devis")
    @JsonIgnore
    private Set<Demande> demandes = new LinkedHashSet<>();




/*
    public Devi(){

    }
    public Devi(Integer idDevis) {
        this.idDevis = idDevis;
    }



    public void setId(Integer id) {
        this.idDevis = id;
    }

    public String getDatedevis() {
        return datedevis;
    }


    public void setDatedevis(String datedevis) {
        this.datedevis = datedevis;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(Set<Demande> demandes) {
        this.demandes = demandes;
    }
*/
}