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
@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "role", schema = "public")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idrole", nullable = false)
    private Integer idRole;



    @Column(name = "labelrole", length = 254)
    private String labelrole;

    public String getRedirectName() {
        if (this.labelrole == null) return "guest";
        return this.labelrole.toLowerCase();
    }


   @OneToMany(mappedBy = "idRole",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Utilisateur> utilisateurList = new LinkedHashSet<>();




}