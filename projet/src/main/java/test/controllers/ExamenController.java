package test.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.sevices.EnseignantService;
import test.sevices.ExamenService;
import test.sevices.SalleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/examen")
public class ExamenController {
    @Autowired
    private ExamenService examenService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private EnseignantService enseignantService;
    @PostMapping("/createExamen")
    public ResponseEntity<?> createExamen(@Valid @RequestBody Map<String,Object> request){
        try {
            String semestre = (String) request.get("semestre");
            String session = (String) request.get("session");
            String dateString = (String) request.get("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            }catch (ParseException e){
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid date format");
            }
            double heureDebut = 0;
            double heureFin = 0;

            if (request.get("heureDebut") instanceof String) {
                heureDebut = Double.parseDouble((String) request.get("heureDebut"));
            } else if (request.get("heureDebut") instanceof Number) {
                heureDebut = ((Number) request.get("heureDebut")).doubleValue();
            }
            if (request.get("heureFin") instanceof String) {
                heureFin = Double.parseDouble((String) request.get("heureFin"));
            } else if (request.get("heureFin") instanceof Number) {
                heureFin = ((Number) request.get("heureFin")).doubleValue();
            }
            String epreuve = (String) request.get("epreuve");
            Long idModule = request.get("idModule") != null ? ((Number) request.get("idModule")).longValue() : null;
            Long controlleurId = request.get("controlleurId") != null ? ((Number) request.get("controlleurId")).longValue() : null;
            if (semestre == null || session == null || epreuve == null || date == null || heureDebut == 0 || heureFin == 0 || idModule == null || controlleurId == null ){
                return ResponseEntity.badRequest().body("something is missing or invalid");
            }
            return ResponseEntity.ok(examenService.createExamen(heureDebut,heureFin,date,semestre,session,epreuve,idModule,controlleurId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the examen"+e.getMessage());
        }
    }
    @PutMapping("/UpdateExamen/{id}")
    public ResponseEntity<?> UpdateExamen(@PathVariable int id, @RequestBody Map<String, Object> request){
        try {
            String semestre = (String) request.get("semestre");
            String session = (String) request.get("session");
            String dateString = (String) request.get("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            }catch (ParseException e){
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid date format");
            }
            double heureDebut = 0;
            double heureFin = 0;

            if (request.get("heureDebut") instanceof String) {
                heureDebut = Double.parseDouble((String) request.get("heureDebut"));
            } else if (request.get("heureDebut") instanceof Number) {
                heureDebut = ((Number) request.get("heureDebut")).doubleValue();
            }
            if (request.get("heureFin") instanceof String) {
                heureFin = Double.parseDouble((String) request.get("heureFin"));
            } else if (request.get("heureFin") instanceof Number) {
                heureFin = ((Number) request.get("heureFin")).doubleValue();
            }
            String epreuve = (String) request.get("epreuve");
            Long idModule = request.get("idModule") != null ? ((Number) request.get("idModule")).longValue() : null;
            Long controlleurId = request.get("controlleurId") != null ? ((Number) request.get("controlleurId")).longValue() : null;
            if (semestre == null || session == null || epreuve == null || date == null || heureDebut == 0 || heureFin == 0 || idModule == null || controlleurId == null ){
                return ResponseEntity.badRequest().body("something is missing or invalid");
            }
            return ResponseEntity.ok(examenService.updateExamen(id,heureDebut,heureFin,date,semestre,session,epreuve,idModule,controlleurId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the examen"+e.getMessage());
        }
    }
    @PostMapping("/affecterSalles")
    public ResponseEntity<?> affecterSallesExam(@RequestBody Map<String, Object> request) {
        try {
            List<Integer> idSallesInt = (List<Integer>) request.get("idSalles");
            Long idExam = Long.valueOf(request.get("idExam").toString());

            List<Long> idSalles = new ArrayList<>();
            for (Integer idSalle : idSallesInt) {
                idSalles.add(Long.valueOf(idSalle));
            }

            salleService.affecterSallesExam(idSalles, idExam);
            return ResponseEntity.ok("Salles successfully assigned to exam");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while assigning salles to exam: " + e.getMessage());
        }
    }
    @PostMapping("/affecterSurveillants")
    public ResponseEntity<?> affecterSurveillantsExam(@RequestBody Map<String, Object> request) {
        try {
            List<Integer> idEnseignantsInt = (List<Integer>) request.get("idEnseignants");
            Long idExam = Long.valueOf(request.get("idExam").toString());

            List<Long> idEnseignants = new ArrayList<>();
            for (Integer idEnseignant : idEnseignantsInt) {
                idEnseignants.add(Long.valueOf(idEnseignant));
            }

            enseignantService.affecterSurveillantExam(idEnseignants, idExam);
            return ResponseEntity.ok("Surveillants successfully assigned to exam");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while assigning surveillants to exam: " + e.getMessage());
        }
    }
    @PutMapping("/FinishedExamen/{id}")
    public ResponseEntity<?> FinishedExamen(@PathVariable int id, @RequestBody Map<String, Object> requestBody) {
        try {
            String rapport = (String) requestBody.get("rapport");
            String pv = (String) requestBody.get("pv");
            examenService.finishedExam(id, rapport, pv);
            return ResponseEntity.ok("Exam finished successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while finishing exam: " + e.getMessage());
        }
    }
    @GetMapping("/GetAllExamnes")
    public ResponseEntity<?> getAllExamnes(){
        return ResponseEntity.ok(examenService.getAllExamens());
    }
}
