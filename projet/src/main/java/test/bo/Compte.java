package test.bo;

import jakarta.persistence.*;

@Entity
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompte;
    private Boolean enabled = true;
    private Boolean accountNotExpired = true;
    private Boolean accountNotLocked = true;
    private boolean credentialsNonExpired = true;
    private String login;
    private String password;
    private Boolean affichePhoto;
    private Boolean afficheEmail;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Compte() {
    }

    public Compte(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Compte(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(Boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public Boolean getAccountNotLocked() {
        return accountNotLocked;
    }

    public void setAccountNotLocked(Boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAffichePhoto() {
        return affichePhoto;
    }

    public void setAffichePhoto(Boolean affichePhoto) {
        this.affichePhoto = affichePhoto;
    }

    public Boolean getAfficheEmail() {
        return afficheEmail;
    }

    public void setAfficheEmail(Boolean afficheEmail) {
        this.afficheEmail = afficheEmail;
    }

    public void setIdCompte(Long idCompte) {
        this.idCompte = idCompte;
    }

    public Long getIdCompte() {
        return idCompte;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Compte(String login, String password, Role role, User user) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.user = user;
        this.enabled = true;
        this.accountNotExpired = true;
        this.accountNotLocked = true;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "idCompte=" + idCompte +
                ", enabled=" + enabled +
                ", accountNotExpired=" + accountNotExpired +
                ", accountNotLocked=" + accountNotLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", affichePhoto=" + affichePhoto +
                ", afficheEmail=" + afficheEmail +
                ", role=" + role +
                '}';
    }
}
