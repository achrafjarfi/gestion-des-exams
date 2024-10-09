package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Enseignant;
import test.bo.Examen;
import test.bo.Module;
import test.dao.EnseignantDao;
import test.dao.ExamenDao;
import test.dao.ModuleDao;
import test.exceptions.Exeptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnseignantService {
    private EnseignantDao enseignantDao;
    private ModuleDao moduleDao;
    private ExamenDao examenDao;
    @Autowired
    public EnseignantService(EnseignantDao enseignantDao,ModuleDao moduleDao,ExamenDao examenDao) {
        this.enseignantDao = enseignantDao;
        this.moduleDao = moduleDao;
        this.examenDao = examenDao;
    }
    public void addEnseignant(Enseignant enseignant){
        enseignantDao.save(enseignant);
    }
    public void deleteEnseignant(Enseignant enseignant){
        enseignantDao.delete(enseignant);
    }
    public void deleteEnseignantById(Long id){
        enseignantDao.deleteById(id);
    }
    public void deleteEnseignantByName(String name){
        enseignantDao.deleteByNom(name);
    }
    public Enseignant findEnseignantById(Long id){
        return enseignantDao.findById(id).orElse(null);
    }
    public void addModuleToEnseignant(Long idModule, Long idEnseignant) {
        Enseignant enseignant = enseignantDao.findById(idEnseignant).orElse(null);
        if (enseignant == null) {
            throw new EntityNotFoundException("Enseignant not found with id: " + idEnseignant);
        }

        Optional<Module> optionalModule = moduleDao.findById(idModule);
        if (optionalModule.isEmpty()) {
            throw new EntityNotFoundException("Module not found with id: " + idModule);
        }

        Module module = optionalModule.get();
        module.setEnseignant(enseignant);

        if (enseignant.getModules() == null) {
            enseignant.setModules(new ArrayList<>());
        }

        enseignant.getModules().add(module);

        enseignantDao.save(enseignant);
        moduleDao.save(module);
    }
    public List<Enseignant> getAllEnseignants(){
        List<Enseignant> enseignants = enseignantDao.findAll();
        if (!enseignants.isEmpty()){
            return enseignants;
        }else {
            throw new EntityNotFoundException("Enseignants not found");
        }
    }
    public void affecterSurveillantExam(List<Long> idEnseignants, Long idExam) {
        Optional<Examen> optionalExamen = examenDao.findById(idExam);
        if (optionalExamen.isEmpty()) {
            throw new EntityNotFoundException("Examen not found with id: " + idExam);
        }
        Examen examen = optionalExamen.get();

        for (Long idEnseignant : idEnseignants) {
            Optional<Enseignant> optionalEnseignant = enseignantDao.findEnseignantById(idEnseignant);
            System.out.println(optionalEnseignant);
            if (optionalEnseignant.isEmpty()) {
                throw new EntityNotFoundException("Enseignant not found with id: " + idEnseignant);
            }
            Enseignant enseignant = optionalEnseignant.get();

            examen.getSurvellants().add(enseignant);

        }

        examenDao.save(examen);
        for (Long idEnseignant : idEnseignants) {
            Optional<Enseignant> optionalEnseignant = enseignantDao.findById(idEnseignant);
            optionalEnseignant.ifPresent(enseignant -> enseignantDao.save(enseignant));
        }
    }
    public List<Enseignant> getFreeEnseignant(Date date) throws Exeptions {
        List<Enseignant> enseignantList = enseignantDao.findSurveillantsNotAssignedToSalleOnDate(date);
        if (!enseignantList.isEmpty()){
            return enseignantList;
        }else {
            throw new Exeptions("tous les enseignant sont indisponibles dans ce temps");
        }
    }

}
