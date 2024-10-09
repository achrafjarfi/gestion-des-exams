package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.bo.Filliere;
import test.sevices.FilliereService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin/filliere")
public class FilliereController {
    @Autowired
    private FilliereService filliereService;
    @PostMapping("/addFilliere")
    public ResponseEntity<?> addFilliere(@RequestBody Map<String ,Object> fillieres){
        String nom = (String) fillieres.get("nomFilliere");
        String allias = (String) fillieres.get("allias");
        Long chefFiliereId = fillieres.get("chefFiliereId") != null ? ((Number) fillieres.get("chefFiliereId")).longValue() : null;
        if (chefFiliereId == null || nom == null || allias == null) {
            return ResponseEntity.badRequest().body("FiliereId or nom or allias is missing or invalid");
        }else {
            Filliere filliere =filliereService.createFiliere(nom,chefFiliereId,allias);
            if (filliere == null){
                return ResponseEntity.badRequest().body("chef filliere n'existe pas");
            }
            return ResponseEntity.ok("Filliere ajouté avec succès");
        }
    }
    @DeleteMapping("{idFilliere}")
    public ResponseEntity<?> deleteFilliere(@PathVariable("idFilliere") Long idFilliere){
        try {
            filliereService.deleteFilliereById(idFilliere);
            return ResponseEntity.ok("Filliere deleted successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getAllFillieres")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(filliereService.getAllFillieres());
    }
}
