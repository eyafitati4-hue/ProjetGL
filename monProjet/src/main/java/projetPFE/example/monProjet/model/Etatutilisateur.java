package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "etatutilisateur", schema = "public")
public class Etatutilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idetatutilisateur", nullable = false)
    private Integer idEtat;




    @OneToMany(mappedBy = "idEtat")
    @JsonIgnore
    private Set<Utilisateur> utilisateurList = new LinkedHashSet<>();



    @Column(name = "labelleetatutilisateur", length = 254)
    private String labelleetatutilisateur;


}