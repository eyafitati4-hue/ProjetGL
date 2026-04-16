package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projetPFE.example.monProjet.model.Association4;
import projetPFE.example.monProjet.model.Association4Id;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Document;
import projetPFE.example.monProjet.repository.Association4Repository;
import projetPFE.example.monProjet.repository.DemandeRepository;
 import projetPFE.example.monProjet.repository.DocumentRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class Associationdd {
    @Autowired
    private Association4Repository association4Repository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private DocumentRepository documentRepository;


    public Association4 ajouterDocumentALaDemande(Integer idDocument, Integer idDemande, MultipartFile file) {
        // Fetch document and demande
        Document document = documentRepository.findById(idDocument)
                .orElseThrow(() -> new IllegalArgumentException("Document non trouvé avec l'ID : " + idDocument));

        Demande demande = demandeRepository.findById(idDemande)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'ID : " + idDemande));

        // Prepare the association ID
        Association4Id associationId = new Association4Id();
        associationId.setIddocument(idDocument);
        associationId.setIddemande(idDemande);

        // Check for an existing association
        Association4 association = association4Repository.findById(associationId)
                .orElseGet(Association4::new); // create a new instance if not found

        // Convert MultipartFile to byte[]
        try {
            if (file != null && !file.isEmpty()) {
                byte[] fileBytes = file.getBytes();
                association.setFichiera(fileBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la conversion du fichier", e);
        }

        association.setId(associationId);  // Set the composite ID

        // Save and return the updated/new association
        return association4Repository.save(association);
    }







    public List<Association4> getAssociationsByDemandeId(Integer idDemande) {
      return association4Repository.findByIdDemande(idDemande);
  }

}
