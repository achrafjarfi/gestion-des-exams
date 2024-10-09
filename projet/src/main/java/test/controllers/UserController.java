package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.sevices.UserService;

@CrossOrigin
@RestController
@RequestMapping("/admin/User")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
