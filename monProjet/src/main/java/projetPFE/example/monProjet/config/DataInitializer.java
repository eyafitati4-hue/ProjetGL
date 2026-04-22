package projetPFE.example.monProjet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Injecter l'état attendu par défaut (ID = 1)
        jdbcTemplate.execute("INSERT INTO etatutilisateur (idetatutilisateur, labelleetatutilisateur) VALUES (1, 'ACTIF') ON CONFLICT (idetatutilisateur) DO NOTHING");

        // Injecter les rôles attendus par l'AuthenticationService (ID = 1, 2, 3)
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (1, 'ADMIN') ON CONFLICT (idrole) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (2, 'CONCESSIONNAIRE') ON CONFLICT (idrole) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (3, 'CLIENT') ON CONFLICT (idrole) DO NOTHING");
        
        // Injecter les etats de demande attendus
        jdbcTemplate.execute("INSERT INTO etatdemande (idetatdemande, labelleetatdemande) VALUES (1, 'EN_ATTENTE') ON CONFLICT (idetatdemande) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO etatdemande (idetatdemande, labelleetatdemande) VALUES (2, 'EN_COURS') ON CONFLICT (idetatdemande) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO etatdemande (idetatdemande, labelleetatdemande) VALUES (3, 'VALIDEE') ON CONFLICT (idetatdemande) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO etatdemande (idetatdemande, labelleetatdemande) VALUES (4, 'REJETEE') ON CONFLICT (idetatdemande) DO NOTHING");

        // Injecter un devis par défaut pour satisfaire la contrainte de clé étrangère dans DemandeService
        jdbcTemplate.execute("INSERT INTO devis (iddevis, produit) VALUES (1, 'Devis Par Défaut') ON CONFLICT (iddevis) DO NOTHING");

        System.out.println("Données initiales (Rôles, Etat, EtatDemande, Devis) ont été chargées avec succès !");
    }
}
