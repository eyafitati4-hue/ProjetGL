package projetPFE.example.monProjet.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetPFE.example.monProjet.model.Utilisateur;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String token ;



    @Enumerated(EnumType.STRING )
    private TokenType tokenType;

    private boolean expired ;
    private  boolean revoked ;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur ;





}
