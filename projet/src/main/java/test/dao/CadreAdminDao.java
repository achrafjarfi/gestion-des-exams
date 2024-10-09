package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import test.bo.CadreAdmin;

import java.util.Optional;

public interface CadreAdminDao extends JpaRepository<CadreAdmin,Long> {

    Optional<CadreAdmin> findCadreAdminById(Long aLong);
    CadreAdmin getCadreAdminById(Long id);
    void deleteCadreAdminById(Long aLong);

    CadreAdmin save(CadreAdmin cadreAdmin);
}
