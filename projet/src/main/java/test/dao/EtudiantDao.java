package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.bo.Etudiant;
import test.bo.Module;

import java.util.List;
import java.util.Optional;

public interface EtudiantDao extends JpaRepository<Etudiant,Long> {
    void deleteByIdEtudiant(Long aLong);
    @Override
    @NotNull
    Etudiant save(Etudiant etudiant);
    Etudiant getEtudiantByIdEtudiant(Long id);
    Optional<Etudiant> findEtudiantByIdEtudiant(Long aLong);
    @Query("SELECT e FROM Etudiant e WHERE e.niveau.idNiveau = :idNiveau")
    List<Etudiant> getEtudiantsByNiveau(@Param("idNiveau") Long idNiveau);
}
