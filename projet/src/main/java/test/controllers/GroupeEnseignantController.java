package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.bo.GroupeEnseignant;
import test.sevices.GroupeEnseignantService;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/grpEns")
public class GroupeEnseignantController {
    @Autowired
    private GroupeEnseignantService groupeEnseignantService;
    @PostMapping("/createGrpEns")
    public ResponseEntity<?> createGrpEns(@RequestBody Map<String, Object> request) {
        List<Integer> enseignantIds = (List<Integer>) request.get("enseignantIds");
        String name = (String) request.get("name");

        if (enseignantIds == null || enseignantIds.isEmpty() || name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body("Enseignant IDs or group name are missing or invalid");
        }

        try {
            groupeEnseignantService.createGrpEns(enseignantIds, name);
            return ResponseEntity.ok("Group created successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the group");
        }
    }
    @PostMapping("/addEnseignantToGrpEnseignant")
    public ResponseEntity<?> addEnseignantToGrpEnseignant(@RequestBody Map<String, Object> request) {
        Object idEnseignantObj = request.get("idEnseignant");
        Object idGrpEnseignantObj = request.get("idGrpEnseignant");

        if (idEnseignantObj == null || idGrpEnseignantObj == null) {
            return ResponseEntity.badRequest().body("Enseignant ID or Group ID is missing or invalid");
        }

        long idEnseignant = ((Number) idEnseignantObj).longValue();
        long idGrpEnseignant = ((Number) idGrpEnseignantObj).longValue();

        try {
            groupeEnseignantService.addEnseignantToGrpEnseignant(idEnseignant, idGrpEnseignant);
            return ResponseEntity.ok("Enseignant added to group successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding enseignant to group");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllGroupeEnseignants() {
        try {
            List<GroupeEnseignant> groupeEnseignants = groupeEnseignantService.getAllGroupeEnseignants();
            return ResponseEntity.ok(groupeEnseignants);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the groups");
        }
    }
    @DeleteMapping("/deleteGroupe/{id}")
    public ResponseEntity<?> deleteGroupe(@PathVariable long id){
        try {
            groupeEnseignantService.deleteGroupeEnseignant(id);
            return ResponseEntity.ok("groupe suprimer avec succes");
        }catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
