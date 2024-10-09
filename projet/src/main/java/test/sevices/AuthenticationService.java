package test.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.bo.*;
import test.config.JwtService;
import test.dao.CompteDao;
import test.dao.UserDao;
import test.model.AuthenticationResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompteDao compteDao;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity<?> registre(UserPrincipal userPrincipal) {
        try {
            if (userPrincipal.getCompte() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compte is null");
            }

            Optional<Compte> user = compteDao.findCompteByLogin(userPrincipal.getUsername());
            if (user.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }
            Optional<User> cin = userDao.findUserByCin(userPrincipal.getCin());
            if (cin.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cin already exists");
            }

            User user1;
            Compte compte = userPrincipal.getCompte();
            if (compte.getRole() == Role.CadreAdmin) {
                user1 = new CadreAdmin();
            } else if (compte.getRole() == Role.Enseignant) {
                user1 = new Enseignant();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Role");
            }

            user1.setEmail(userPrincipal.getEmail());
            user1.setCin(userPrincipal.getCin());
            user1.setPrenom(userPrincipal.getFirstName());
            user1.setNom(userPrincipal.getLastName());
            user1.setNumTele(userPrincipal.getNumTele());
            userDao.save(user1);

            compte.setUser(user1);
            compte.setPassword(passwordEncoder.encode(compte.getPassword()));

            compte = compteDao.save(compte);

            String token = jwtService.generateToken(compte, generateExtraClaims(compte));
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }




    public ResponseEntity<?> login(UserPrincipal userPrincipal){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userPrincipal.getUsername(),
                            userPrincipal.getPassword()
                    )
            );
            Compte compte = compteDao.findCompteByLogin(userPrincipal.getUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            String token = jwtService.generateToken(userPrincipal.getCompte(),generateExtraClaims(compte));
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    private Map<String , Object> generateExtraClaims(Compte user) {
        Map<String , Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getLogin());
        extraClaims.put("role",user.getRole().name());

        return extraClaims;
    }
}
