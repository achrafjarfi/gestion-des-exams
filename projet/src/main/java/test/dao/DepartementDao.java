package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import test.bo.Departement;

import java.util.Optional;

public interface DepartementDao extends JpaRepository<Departement,Long> {
    Optional<Departement> findDepartementByIdDepartement(Long id);
    @Override
    @NotNull Departement save(Departement departement);

}
