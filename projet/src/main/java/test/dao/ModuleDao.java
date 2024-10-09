package test.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.bo.Module;

import java.util.List;
import java.util.Optional;


public interface ModuleDao extends JpaRepository<Module,Long> {
    Optional<Module> findModuleByIdModule(Long id);
    Module getModuleByIdModule(Long id);
    void deleteByIdModule(Long aLong);
    @Override
    @NotNull
    Module save(Module module);

    @Query("SELECT m FROM Module m WHERE m.niveau.idNiveau = :idNiveau")
    List<Module> getModulesByNiveau(@Param("idNiveau") Long idNiveau);
}
