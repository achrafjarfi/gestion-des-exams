package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.bo.Enseignant;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnseignantDao extends JpaRepository<Enseignant,Long> {
    public void deleteByNom(String nom);
    Optional<Enseignant> findEnseignantById(Long id);
    Enseignant getEnseignantById(Long id);
    @Override
    @NotNull
    Enseignant save(Enseignant enseignant);
    @Query("SELECT e FROM Enseignant e WHERE e.departement.idDepartement = :idDepartement")
    List<Enseignant> getEnseignantByDepartement(Long idDepartement);

    @Query("SELECT e FROM Enseignant e WHERE e.id NOT IN (" +
            "SELECT ens.id FROM Salle s JOIN s.surveillants ens JOIN s.examen ex WHERE ex.date = :date" +
            ")")
    List<Enseignant> findSurveillantsNotAssignedToSalleOnDate(@Param("date") Date date);
}
