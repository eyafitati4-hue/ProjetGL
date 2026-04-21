package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projetPFE.example.monProjet.token.Token;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties(ignoreUnknown = true)

@NoArgsConstructor(force = true)

@Table(name = "utilisateur", schema = "public")
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idutilisateur", nullable = false)
    private Integer idutilisateur;

    @NotBlank
    @Column(name = "nomutilisateur", length = 254)
    private String nomutilisateur;

    @NotBlank
    @Column(name = "prenom", length = 254)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'adresse email est incorrect")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Format d'email invalide")
    @Column(name = "email", length = 254, unique = true)
    private String email;

    @NotBlank
    @Column(name = "telephone", length = 254)
    private String telephone;

    @NotBlank
    @Column(name = "motdepasse", length = 254)
    private String motdepasse;

    @Column(name = "adresse", length = 254)
    private String adresse;

    @Column(name = "ville", length = 254)
    private String ville;

    @Column(name = "codepostal", length = 254)
    private String codepostal;

    @Column(name = "datenaissance")
    @jakarta.validation.constraints.Past(message = "La date de naissance doit être dans le passé")
    private LocalDate datenaissance;



    //relation entre utilisateur et demande
    @JsonIgnore
    @OneToMany(mappedBy = "idutilisateur" ,cascade = CascadeType.ALL)
    private Set<Demande> demandes = new LinkedHashSet<>();




    //relation entre utilisateur et produit
    @OneToMany(mappedBy = "utilisateur")
    private Set<Produit> produits = new LinkedHashSet<>();



    //RELATION UTILISATEUR et ROLE
    @ManyToOne(fetch = FetchType.EAGER, optional = false) /* optional = false la colonne correspondante dans la table de rôle (idutilisateur) ne peut pas avoir de valeur null*/
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JsonIgnore
    @jakarta.validation.constraints.NotNull(message = "Le rôle est obligatoire")
    @JoinColumn(name = "idRole", nullable = false)
    private Role idRole;



@OneToMany(mappedBy = "id" ,cascade = CascadeType.ALL)
private List<Token> tokens ;

    //RELATION UTILISATEUR ETAT UTILISATEUR
    @ManyToOne(fetch = FetchType.LAZY) /* optional = false la colonne correspondante dans la table de rôle (idutilisateur) ne peut pas avoir de valeur null*/
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @jakarta.validation.constraints.NotNull(message = "L'état est obligatoire")
    @JoinColumn(name = "idEtat", nullable = false)
    @JsonIgnore
    private Etatutilisateur idEtat;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(idRole.getLabelrole()));
    }
// cette fonction permet de récupérer les autorisations (ou rôles) de l'utilisateur, en les convertissant en objets GrantedAuthority à utiliser dans le système de sécurité Spring



    @Override
    public String getPassword() {
        return motdepasse ;
    }

    @Override
    public String getUsername() {
        return email ;
    }


    //public Integer getRole(){ return idRole.getIdRole();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //setters et getters

}