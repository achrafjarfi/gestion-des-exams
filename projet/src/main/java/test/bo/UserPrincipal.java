package test.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private Compte compte;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public UserPrincipal(Compte compte) {
        this.compte = compte;
    }

    public String getFirstName(){
        return user.getPrenom();
    }
    public String getLastName(){
        return user.getNom();
    }
    public String getEmail(){
        return user.getEmail();
    }
    public String getCin(){
        return user.getCin();
    }
    public String getPhoto(){
        return user.getPhoto();
    }
    public String getNumTele(){
        return user.getNumTele();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> arrayAuths = new ArrayList<>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(compte.getRole().name());
        arrayAuths.add(auth);
        return arrayAuths;
    }

    @Override
    public String getPassword() {
        return compte.getPassword();
    }

    @Override
    public String getUsername() {
        return compte.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return compte.getAccountNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return compte.getAccountNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return compte.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return compte.getEnabled();
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "compte=" + compte +
                '}';
    }

    public UserPrincipal() {
    }
}
