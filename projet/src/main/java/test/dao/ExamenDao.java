package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import test.bo.Examen;

import java.util.Optional;

public interface ExamenDao extends JpaRepository<Examen,Long> {
    Optional<Examen> findExamenByIdExamen(Long aLong);
    Examen getExamenByIdExamen(Long id);
    @Override
    @NotNull
    Examen save(Examen examen);

    void deleteExamenByIdExamen(Long aLong);
}
