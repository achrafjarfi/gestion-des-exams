package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.bo.Salle;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SalleDao extends JpaRepository<Salle,Long> {
    void deleteByIdSalle(Long aLong);
    Optional<Salle> findByIdSalle(Long aLong);
    Salle getSalleByIdSalle(Long id);
    @Override
    @NotNull
    Salle save(Salle salle);

    @Query("SELECT s FROM Salle s WHERE s.idSalle NOT IN (SELECT salle.idSalle FROM Salle salle JOIN salle.examen e WHERE e.date = :date)")
    List<Salle> findEmptySallesByExamDate(@Param("date") Date date);
}
