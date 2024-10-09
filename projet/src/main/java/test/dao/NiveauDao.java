package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.bo.Niveau;

import java.util.List;
import java.util.Optional;

public interface NiveauDao extends JpaRepository<Niveau,Long> {
    Optional<Niveau> findNiveauByIdNiveau(Long id);
    Niveau getNiveauByIdNiveau(Long id);
    @Override
    @NotNull
    Niveau save(Niveau niveau);
    void deleteByIdNiveau(Long aLong);

    @Query("SELECT e FROM Niveau e WHERE e.filliere.idFilliere = :IdFilliere")
    List<Niveau> getNiveauByFilliere(Long IdFilliere);
}
