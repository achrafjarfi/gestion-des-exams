package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.bo.*;
import test.sevices.AuthenticationService;


@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserPrincipal userPrincipal) {
        if (userPrincipal.getCompte() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compte is required");
        }
        return ResponseEntity.ok(authenticationService.registre(userPrincipal));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Compte compte){
        UserPrincipal userPrincipal = new UserPrincipal(compte);
        return ResponseEntity.ok(authenticationService.login(userPrincipal));
    }
}
