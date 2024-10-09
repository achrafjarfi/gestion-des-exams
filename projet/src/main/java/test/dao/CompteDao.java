package test.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import test.bo.Compte;
import test.bo.Role;

import java.util.Optional;
import java.util.function.Function;

public interface CompteDao extends JpaRepository<Compte,Long> {

    Compte save(Compte compte);

    Optional<Compte> findCompteByLogin(String login);
    Compte getCompteByLogin(String login);
    void deleteCompteByIdCompte(Long aLong);
}
