package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.sevices.FilliereService;
import test.sevices.NiveauService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/niveau")
public class NiveauController {
    @Autowired
    private NiveauService niveauService;
    @Autowired
    private FilliereService filliereService;
    @PostMapping("/addNiveau")
    public ResponseEntity<?> addNiveau(@RequestBody Map<String,Object> niveau){
        String nom = (String) niveau.get("nomNiveau");
        String allias = (String) niveau.get("allias");
        if (nom == null || allias == null){
            return ResponseEntity.badRequest().body("nom or allias is missing or invalid");
        }
        niveauService.createNiveau(nom,allias);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{idNiveau}")
    public ResponseEntity<?> deleteNiveau(@PathVariable("idNiveau") Long idNiveau) {
        try {
            niveauService.deleteNiveauById(idNiveau);
            return ResponseEntity.ok("Niveau deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/addNiveauToFilliere")
    public ResponseEntity<?> addNiveauToFilliere(@RequestBody Map<String, Object> request) {
        Long idNiveau = request.get("idNiveau") != null ? ((Number) request.get("idNiveau")).longValue() : null;
        Long idFilliere = request.get("idFilliere") != null ? ((Number) request.get("idFilliere")).longValue() : null;

        if (idNiveau == null || idFilliere == null) {
            return ResponseEntity.badRequest().body("idNiveau or idFilliere is missing or invalid");
        }

        try {
            filliereService.addNiveauToFilliere(idNiveau, idFilliere);
            return ResponseEntity.ok("Niveau added to filiere successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the niveau to the filiere");
        }
    }
    @GetMapping("/getAllNiveau")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(niveauService.getAllNiveau());
    }
}
