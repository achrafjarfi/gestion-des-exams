package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.bo.Module;
import test.sevices.DepartementService;
import test.sevices.EnseignantService;
import test.sevices.ModuleService;
import test.sevices.NiveauService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private NiveauService niveauService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private EnseignantService enseignantService;
    @PostMapping("/addModule")
    public ResponseEntity<?> addModule(@RequestBody Map<String,Object> module){
        String nom = (String) module.get("nomModule");
        Long niveauId = module.get("niveauId") != null ? ((Number) module.get("niveauId")).longValue() : null;
        if (niveauId == null || nom == null){
            return ResponseEntity.badRequest().body("NiveauId or nom is missing or invalid");
        }else {
            Module module1 = moduleService.createModule(nom,niveauId);
            if (module1 == null){
                return ResponseEntity.badRequest().body("ce niveau n'existe pas");
            }
            return ResponseEntity.ok("Module ajouté avec succès");
        }
    }
    @DeleteMapping("/{idModule}")
    public ResponseEntity<?> deleteModule(@PathVariable("idModule") long idModule) {
        try {
            moduleService.deleteModuleById(idModule);
            return ResponseEntity.ok("Module deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/addModuleToNiveau")
    public ResponseEntity<?> addModuleToNiveau(@RequestBody Map<String, Object> request) {
        Long idModule = request.get("idModule") != null ? ((Number) request.get("idModule")).longValue() : null;
        Long idNiveau = request.get("idNiveau") != null ? ((Number) request.get("idNiveau")).longValue() : null;

        if (idModule == null || idNiveau == null) {
            return ResponseEntity.badRequest().body("idModule or idNiveau is missing or invalid");
        }

        try {
            niveauService.addModuleToNiveau(idModule, idNiveau);
            return ResponseEntity.ok("Module added to niveau successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the module to the niveau");
        }
    }
    @PostMapping("/addFilliereToDepartement")
    public ResponseEntity<?> addFilliereToDepartement(@RequestBody Map<String, Object> request) {
        Long idFilliere = request.get("idFilliere") != null ? ((Number) request.get("idFilliere")).longValue() : null;
        Long idDepartement = request.get("idDepartement") != null ? ((Number) request.get("idDepartement")).longValue() : null;

        if (idFilliere == null || idDepartement == null) {
            return ResponseEntity.badRequest().body("idFilliere or idDepartement is missing or invalid");
        }

        try {
            departementService.addFilliereToDepartement(idFilliere, idDepartement);
            return ResponseEntity.ok("Filliere added to departement successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the filliere to the departement");
        }
    }
    @PostMapping("/addModuleToEnseignant")
    public ResponseEntity<?> addModuleToEnseignant(@RequestBody Map<String, Object> request) {
        Long idModule = request.get("idModule") != null ? ((Number) request.get("idModule")).longValue() : null;
        Long idEnseignant = request.get("idEnseignant") != null ? ((Number) request.get("idEnseignant")).longValue() : null;

        if (idModule == null || idEnseignant == null) {
            return ResponseEntity.badRequest().body("idModule or idEnseignant is missing or invalid");
        }

        try {
            enseignantService.addModuleToEnseignant(idModule, idEnseignant);
            return ResponseEntity.ok("Module added to enseignant successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the module to the enseignant");
        }
    }
    @GetMapping("/getAllModules")
    public ResponseEntity<?> getAllModules(){
        return ResponseEntity.ok(moduleService.getAllModules());
    }

}
