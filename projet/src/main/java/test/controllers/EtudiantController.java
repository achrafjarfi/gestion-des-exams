package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.sevices.DepartementService;
import test.sevices.NiveauService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/etudiant")
public class EtudiantController {
    @Autowired
    private NiveauService niveauService;
    @Autowired
    private DepartementService departementService;
    @PostMapping("/addEtudiantToNiveau")
    public ResponseEntity<?> addEtudiantToNiveau(@RequestBody Map<String, Object> request) {
        Long idNiveau = request.get("idNiveau") != null ? ((Number) request.get("idNiveau")).longValue() : null;
        Long idEtudiant = request.get("idEtudiant") != null ? ((Number) request.get("idEtudiant")).longValue() : null;

        if (idNiveau == null || idEtudiant == null) {
            return ResponseEntity.badRequest().body("idNiveau or idEtudiant is missing or invalid");
        }

        try {
            niveauService.addEtudiantToNiveau(idNiveau, idEtudiant);
            return ResponseEntity.ok("Étudiant ajouté au niveau avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the étudiant to the niveau");
        }
    }
    @PostMapping("/addEnseignantToDepartement")
    public ResponseEntity<?> addEnseignantToDepartement(@RequestBody Map<String, Object> request) {
        Long idEnseignant = request.get("idEnseignant") != null ? ((Number) request.get("idEnseignant")).longValue() : null;
        Long idDepartement = request.get("idDepartement") != null ? ((Number) request.get("idDepartement")).longValue() : null;

        if (idEnseignant == null || idDepartement == null) {
            return ResponseEntity.badRequest().body("idEnseignant or idDepartement is missing or invalid");
        }

        try {
            departementService.addEnseignantToDepartement(idEnseignant, idDepartement);
            return ResponseEntity.ok("Enseignant ajouté au département avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the enseignant to the departement");
        }
    }

}
