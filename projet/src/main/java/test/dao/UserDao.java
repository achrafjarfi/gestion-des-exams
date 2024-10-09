package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import test.bo.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Long> {
    User getUserByCin(String cin);
    Optional<User> findUserById(Long aLong);
    Optional<User> findUserByPrenom(String prenom);
    User getUserById(Long id);
    @Override
    @NotNull
    User save(User user);
    Optional<User> findUserByCin(String cin);
}
