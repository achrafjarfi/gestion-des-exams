package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.bo.Element;
import test.sevices.ElementService;
import test.sevices.ModuleService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/element")
public class ElementController {
    @Autowired
    private ElementService elementService;
    @Autowired
    private ModuleService moduleService;
    @PostMapping("/addElement")
    public ResponseEntity<?> addElement(@RequestBody Map<String,Object> element){
        String nom = (String) element.get("nomElement");
        Long enseignantId = element.get("enseignantId") != null ? ((Number) element.get("enseignantId")).longValue() : null;
        Long moduleId = element.get("moduleId") != null ? ((Number) element.get("moduleId")).longValue() : null;
        if (enseignantId == null || moduleId == null || nom==null) {
            return ResponseEntity.badRequest().body("EnseignantId or ModuleId or nom is missing or invalid");
        } else {
            Element element1 =elementService.createElement(nom, enseignantId, moduleId);
            if (element1 == null){
                return ResponseEntity.badRequest().body("module ou enseignant n'est pas present");
            }
            return ResponseEntity.ok("Element ajouté avec succès");
        }
    }
    @DeleteMapping("/{idElement}")
    public ResponseEntity<?> deleteElement(@PathVariable("idElement") Long idElement) {
        try {
            elementService.deleteElementById(idElement);
            return ResponseEntity.ok("Element deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/addElementToModule")
    public ResponseEntity<?> addElementToModule(@RequestBody Map<String, Object> request) {
        Long idModule = request.get("idModule") != null ? ((Number) request.get("idModule")).longValue() : null;
        Long idElement = request.get("idElement") != null ? ((Number) request.get("idElement")).longValue() : null;

        if (idModule == null || idElement == null) {
            return ResponseEntity.badRequest().body("idModule or idElement is missing or invalid");
        }

        try {
            moduleService.addElementToModule(idElement, idModule);
            return ResponseEntity.ok("Element added to module successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the element to the module"+e.getMessage());
        }
    }
    @GetMapping("/getAllElements")
    public ResponseEntity<?> getAllElements(){
        return ResponseEntity.ok(elementService.getAllElements());
    }
}
