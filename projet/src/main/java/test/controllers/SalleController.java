package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.sevices.SalleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class SalleController {
    @Autowired
    private SalleService salleService;
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
    @GetMapping("/getEmptySalles/{date}")
    public ResponseEntity<?> getEmptySalles(@PathVariable String date){
        Date date1 = convertStringToDate(date);
        return ResponseEntity.ok(salleService.getAllSallesEmpty(date1));
    }
    @GetMapping("/getAllSalles")
    public ResponseEntity<?> getAllSalles(){
        return ResponseEntity.ok(salleService.getAllSalles());
    }
}
