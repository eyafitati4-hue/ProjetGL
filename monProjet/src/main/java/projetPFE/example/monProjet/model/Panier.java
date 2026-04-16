package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude
@NoArgsConstructor

@Table(name = "panier", schema = "public")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idpanier", nullable = false)
    private Integer idPanier ;

    /*@OneToMany(mappedBy="Panier")
    @JsonIgnore
    Set<Association10> panierproduitList = new HashSet<>();*/



}