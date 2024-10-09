package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Compte;
import test.bo.Role;
import test.bo.User;
import test.dao.CompteDao;
import test.dao.UserDao;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CompteService {
    @Autowired
    private CompteDao compteDao;
    @Autowired
    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    public CompteService(CompteDao compteDao) {
        this.compteDao = compteDao;
    }
    public void addCompte(Compte compte){
        compteDao.save(compte);
    }
    public void updateCompte(Compte compte){
        compteDao.save(compte);
    }

    public Compte findCompteById(Long id) {
        return compteDao.findById(id).orElse(null);
    }
    public Compte findCompteByLogin(String login) {
        return compteDao.findCompteByLogin(login).orElse(null);
    }
    public List<Compte> getAllAccounts(){
        return compteDao.findAll();
    }
    public String createCompte(Long idUser,Role role){
        User user = userDao.findById(idUser).get();
        Compte userCompte = new Compte();
        userCompte.setUser(user);
        userCompte.setRole(Role.valueOf(role.name()));
        String generatedPass = generatePassayPassword();
        String encodedPass = passwordEncoder.encode(generatedPass);
        userCompte.setPassword(encodedPass);
        String login = user.getNom()+user.getPrenom();
        Compte compte = compteDao.getCompteByLogin(login);
        if (compte == null){
            userCompte.setLogin(login);
            compteDao.save(userCompte);
            return generatedPass;
        }
        int i=0;
        while (true){
            login=user.getNom()+user.getPrenom()+"_"+i;
            if (compte==null){
                userCompte.setLogin(login);
                compteDao.save(userCompte);
                return generatedPass;
            }
            i++;
        }
    }
    public String generatePassayPassword() {
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(10, digits);

        return password;
    }
    public Compte getAccountByUserName(String login) {


        return compteDao.getCompteByLogin(login);
    }
    public List<Role> getAllRoles() {
        return Arrays.asList(Role.values());
    }
    public List<Compte> getAllComptes() {
        List<Compte> users = compteDao.findAll();
        if (!users.isEmpty()){
            return users;
        }else {
            throw new EntityNotFoundException("Comptes not found");
        }
    }


}
