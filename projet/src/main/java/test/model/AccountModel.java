package test.model;

import test.bo.Role;

public class AccountModel {
    private String username;
    private String password;
    private Role role;
    private Long personId;

    public AccountModel() {
    }

    public AccountModel(Long personId) {
        this.personId = personId;
    }

    public AccountModel(Role role, Long personId) {
        this.role = role;
        this.personId = personId;
    }

    public AccountModel(String username, String password, Role role, Long personId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.personId = personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
