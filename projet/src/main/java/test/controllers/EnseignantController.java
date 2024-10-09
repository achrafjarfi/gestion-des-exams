package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.exceptions.Exeptions;
import test.sevices.EnseignantService;
import test.sevices.FilliereService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/enseignant")
public class EnseignantController {
    @Autowired
    private FilliereService filliereService;
    @Autowired
    private EnseignantService enseignantService;
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @PostMapping("/addEnseignantToFillieres")
    public ResponseEntity<?> addEnseignantsToFillieres(@RequestBody Map<String, Object> request) {
        List<Long> idFillieres = (List<Long>) request.get("idFilliere");
        List<Long> idEnseignants = (List<Long>) request.get("idEnseignant");

        if (idFillieres == null || idFillieres.isEmpty() || idEnseignants == null || idEnseignants.isEmpty()) {
            return ResponseEntity.badRequest().body("Filliere IDs or Enseignant IDs are missing or invalid");
        }

        try {
            filliereService.addEnseignantsToFillieres(idFillieres, idEnseignants);
            return ResponseEntity.ok("Enseignants ajoutés aux filières avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding enseignants to fillieres");
        }
    }
    @GetMapping("/getFreeEnseignant/{date}")
    public ResponseEntity<?> getFreeEnseignant(@PathVariable String date) throws Exeptions {
        Date date1 = convertStringToDate(date);
        return ResponseEntity.ok(enseignantService.getFreeEnseignant(date1));
    }
    @GetMapping("/getAllEnseignants")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(enseignantService.getAllEnseignants());
    }
}
