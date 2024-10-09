package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import test.bo.Departement;
import test.sevices.DepartementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/departement")
public class DepartementController {
    @Autowired
    private DepartementService departementService;
    @PostMapping("/addDepartement")
    public ResponseEntity<?> addDepartement(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        Long coordonateurId = requestBody.get("coordonateurId") != null ? ((Number) requestBody.get("coordonateurId")).longValue() : null;


        if (coordonateurId == null) {
            return ResponseEntity.badRequest().body("coordonateurId is missing or invalid");
        }else {
            Departement departement = departementService.createDepartement(nom,coordonateurId);
            if (departement == null){
                return ResponseEntity.badRequest().body("coordonateur n'existe pas");
            }
            return ResponseEntity.ok("Département ajouté avec succès");
        }
    }
    @DeleteMapping("/{idDepartement}")
    public ResponseEntity<?> deleteDepartement(@PathVariable("idDepartement") Long idDepartement){
        try {
            departementService.deleteDepartement(idDepartement);
            return ResponseEntity.ok("Departement deleted successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getAllDepartement")
    public ResponseEntity<?> getAllDepartement(){
        return ResponseEntity.ok(departementService.getAllDepartments());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handValidEx(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : validationErrors) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
