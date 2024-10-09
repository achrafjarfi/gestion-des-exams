package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.sevices.CadreAdminService;
@CrossOrigin
@RestController
@RequestMapping("/admin/CadreAdmin")
public class CadreAdminController {
    @Autowired
    private CadreAdminService cadreAdminService;
    @GetMapping("/GetAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(cadreAdminService.getAllCadreAdmin());
    }
}
