package test.sevices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.bo.Compte;
import test.bo.UserPrincipal;
import test.dao.CompteDao;

@Service
public class CustomAuthentificationService implements UserDetailsService {
    @Autowired
    private CompteDao compteDao;
    public CustomAuthentificationService() {
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Compte compte = null;
        compte = compteDao.getCompteByLogin(username);
        if (compte==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(compte);
    }
}