package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.bo.Filliere;

import java.util.List;
import java.util.Optional;

public interface FilliereDao extends JpaRepository<Filliere,Long> {

    Optional<Filliere> findFilliereByIdFilliere(Long id);
    Filliere getFilliereByIdFilliere(Long id);
    @Override
    @NotNull
    Filliere save(Filliere filliere);
    void deleteByIdFilliere(Long aLong);

    @Query("SELECT e FROM Filliere e WHERE e.departement.idDepartement = :idDepartement")
    List<Filliere> getFilliereByDepartement(Long idDepartement);
}
