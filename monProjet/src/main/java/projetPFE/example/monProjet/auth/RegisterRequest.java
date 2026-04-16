package projetPFE.example.monProjet.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String prenom;
    private String nom;
    private String email;
    private  String motdepasse ;
    private String telephone ;
    private String  adresse ;
    private String ville ;
    private String codepostal ;
    private LocalDate datenaissance ;

}
