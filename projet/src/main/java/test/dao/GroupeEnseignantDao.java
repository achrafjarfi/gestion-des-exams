package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import test.bo.GroupeEnseignant;

import java.util.Optional;

public interface GroupeEnseignantDao extends JpaRepository<GroupeEnseignant,Long> {
    Optional<GroupeEnseignant> findGroupeEnseignantByIdGroupeEnseignant(Long id);
    GroupeEnseignant getGroupeEnseignantByIdGroupeEnseignant(Long id);
    void deleteByIdGroupeEnseignant(Long aLong);
    @Override
    @NotNull
    GroupeEnseignant save(GroupeEnseignant groupeEnseignant);
}
