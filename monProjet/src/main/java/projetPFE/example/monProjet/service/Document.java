package projetPFE.example.monProjet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projetPFE.example.monProjet.DTO.DocumentDto;
import projetPFE.example.monProjet.DTOmapper.DocumentDtoMapper;
import projetPFE.example.monProjet.interfac.DocumentInter;
import projetPFE.example.monProjet.repository.DocumentRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Document implements DocumentInter {
    @Autowired
    private DocumentRepository documentRepository;


    @Override
    public DocumentDto getById(Integer id) {
        projetPFE.example.monProjet.model.Document document = documentRepository.findById(id).orElse(null);
        return DocumentDtoMapper.toDto(document);
    }

    @Override
    public List<DocumentDto> getAll() {
        List<projetPFE.example.monProjet.model.Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(DocumentDtoMapper::toDto)
                .collect(Collectors.toList());
    }

   /* @Override
    public DocumentDto ajouterDocument(DocumentDto documentDto) {
        projetPFE.example.monProjet.model.Document document = DocumentDtoMapper.toEntity(documentDto);
        document = documentRepository.save(document);
        return DocumentDtoMapper.toDto(document);
    }*/

    public DocumentDto ajouterDocument(MultipartFile file, DocumentDto documentDto) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Le fichier est vide.");
            }

            byte[] fichierBytes = file.getBytes();
            documentDto.setFichier(fichierBytes);

            projetPFE.example.monProjet.model.Document document = DocumentDtoMapper.toEntity(documentDto);
            document = documentRepository.save(document);

            return DocumentDtoMapper.toDto(document);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public DocumentDto modifierDocument(DocumentDto documentDto) {
        projetPFE.example.monProjet.model.Document document = DocumentDtoMapper.toEntity(documentDto);
        if (documentRepository.existsById(document.getIdDocument())) {
            document = documentRepository.save(document);
            return DocumentDtoMapper.toDto(document);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerDocument(Integer id) {
        documentRepository.deleteById(id);
    }
}