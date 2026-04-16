package projetPFE.example.monProjet.DTOmapper;

import org.springframework.stereotype.Component;
import projetPFE.example.monProjet.DTO.DocumentDto;
import projetPFE.example.monProjet.model.Document;

@Component
public class DocumentDtoMapper {
    public static DocumentDto toDto(Document document) {
   return DocumentDto.builder()
                .idDocument(document.getIdDocument())
                .typedocument(document.getTypedocument())
                .cheminfichier(document.getCheminfichier())
                .fichier(document.getFichier()) // Ajout de l'attribut fichier
           // Map other properties as needed
                .build();
    }

    public static Document toEntity(DocumentDto documentDto) {
        Document document = new Document();
        document.setIdDocument(documentDto.getIdDocument());
        document.setTypedocument(documentDto.getTypedocument());
        document.setCheminfichier(documentDto.getCheminfichier());
        document.setFichier(documentDto.getFichier()); // Ajout de l'attribut fichier

        // Map other properties as needed
        return document;
    }

}
