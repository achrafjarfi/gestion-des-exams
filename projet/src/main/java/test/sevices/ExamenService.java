package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import test.bo.*;
import test.bo.Module;
import test.dao.CadreAdminDao;
import test.dao.EnseignantDao;
import test.dao.ExamenDao;
import test.dao.ModuleDao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {
    private ExamenDao examenDao;
    private EnseignantDao enseignantDao;
    private ModuleDao moduleDao;
    private CadreAdminDao cadreAdminDao;
    @Autowired
    public ExamenService(ExamenDao examenDao,EnseignantDao enseignantDao,ModuleDao moduleDao,CadreAdminDao cadreAdminDao) {
        this.examenDao = examenDao;
        this.enseignantDao = enseignantDao;
        this.moduleDao = moduleDao;
        this.cadreAdminDao = cadreAdminDao;
    }

    public Examen createExamen(double heureDebut, double heureFin, Date date,String semestre,String session,String epreuve,Long idModule,Long controllerId) {
        Optional<CadreAdmin> controllerOptional = cadreAdminDao.findCadreAdminById(controllerId);
        if (controllerOptional.isEmpty()){
            throw new EntityNotFoundException("controller not fount");
        }
        CadreAdmin controller = controllerOptional.get();
        Optional<Module> moduleOptional = moduleDao.findModuleByIdModule(idModule);
        if (moduleOptional.isEmpty()){
            throw new EntityNotFoundException("module not found");
        }
        Module module = moduleOptional.get();
        Examen examen = new Examen();
        examen.setHeureDebut(heureDebut);
        examen.setHeureFin(heureFin);
        examen.setDate(date);
        examen.setSemestre(semestre);
        examen.setSession(session);
        examen.setEpreuve(epreuve);
        examen.setController(controller);
        examen.setModule(module);
        return examenDao.save(examen);

    }
    public List<Examen> getAllExamens(){
        List<Examen> examenList = examenDao.findAll();
        if (!examenList.isEmpty()){
            return examenList;
        }else {
            throw new EntityNotFoundException("Examens not found");
        }
    }
    public void finishedExam(long idExam,String rapport,String pv){
        Optional<Examen> optionalExamen = examenDao.findExamenByIdExamen(idExam);
        if (optionalExamen.isEmpty()){
            throw new EntityNotFoundException("examen not found");
        }
        Examen examen = optionalExamen.get();
        examen.setSalles(null);
        examen.setSurvellants(null);
        examen.setPv(pv);
        examen.setRapport(rapport);
        examenDao.save(examen);
    }
    public @NotNull Examen updateExamen(long id, double heureDebut, double heureFin, Date date, String semestre, String session, String epreuve, Long idModule, Long controllerId) {
        Optional<Examen> examenOptional = examenDao.findExamenByIdExamen(id);
        if (examenOptional.isEmpty()) {
            throw new EntityNotFoundException("Examen not found");
        }

        Optional<CadreAdmin> controllerOptional = cadreAdminDao.findCadreAdminById(controllerId);
        if (controllerOptional.isEmpty()) {
            throw new EntityNotFoundException("Controller not found");
        }
        CadreAdmin controller = controllerOptional.get();

        Optional<Module> moduleOptional = moduleDao.findModuleByIdModule(idModule);
        if (moduleOptional.isEmpty()) {
            throw new EntityNotFoundException("Module not found");
        }
        Module module = moduleOptional.get();

        Examen examen = examenOptional.get();

        examen.setHeureDebut(heureDebut);
        examen.setHeureFin(heureFin);
        examen.setDate(date);
        examen.setSemestre(semestre);
        examen.setSession(session);
        examen.setEpreuve(epreuve);
        examen.setController(controller);
        examen.setModule(module);

        return examenDao.save(examen);
    }
    public List<Examen> getAllExamen(){
        List<Examen> examenList = examenDao.findAll();
        if (!examenList.isEmpty()){
            return examenList;
        }else {
            throw new EntityNotFoundException("Examens not found");
        }
    }


}
