package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Etudiant;
import test.bo.Module;
import test.bo.Niveau;
import test.dao.EtudiantDao;
import test.dao.ModuleDao;
import test.dao.NiveauDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NiveauService {
    @Autowired
    private NiveauDao niveauDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private EtudiantDao etudiantDao;
    public void createNiveau(String nom , String allias){
        Niveau niveau = new Niveau(nom,allias);
        niveauDao.save(niveau);
    }
    public void addModuleToNiveau(long idNiveau,long idModule) {
        Optional<Niveau> optionalNiveau = niveauDao.findNiveauByIdNiveau(idNiveau);
        Optional<Module> optionalModule = moduleDao.findModuleByIdModule(idModule);
        if (optionalModule.isPresent() && optionalNiveau.isPresent()){
            Niveau niveau = optionalNiveau.get();
            Module module = optionalModule.get();
            niveau.getModules().add(module);
            niveauDao.save(niveau);
        }
    }
    public void deleteNiveauById(Long idNiveau){
        if (!niveauDao.existsById(idNiveau)){
            throw new EntityNotFoundException("Niveau not found with id: " + idNiveau);
        }
        List<Module> modules = moduleDao.getModulesByNiveau(idNiveau);
        for (Module modu:modules) {
            modu.setNiveau(null);
            moduleDao.save(modu);
        }
        List<Etudiant> etudiants = etudiantDao.getEtudiantsByNiveau(idNiveau);
        for (Etudiant etudiant: etudiants) {
            etudiant.setNiveau(null);
            etudiantDao.save(etudiant);
        }
        niveauDao.deleteByIdNiveau(idNiveau);
    }
    public void addModuleToNiveau(Long idModule, Long idNiveau) {
        Niveau niveau = niveauDao.findById(idNiveau).orElse(null);
        if (niveau == null) {
            throw new EntityNotFoundException("Niveau not found with id: " + idNiveau);
        }

        Optional<Module> optionalModule = moduleDao.findById(idModule);
        if (optionalModule.isEmpty()) {
            throw new EntityNotFoundException("Module not found with id: " + idModule);
        }

        Module module = optionalModule.get();

        if (niveau.getModules() == null) {
            niveau.setModules(new ArrayList<>());
        }

        niveau.getModules().add(module);

        module.setNiveau(niveau);

        niveauDao.save(niveau);
        moduleDao.save(module);
    }
    public void addEtudiantToNiveau(Long idNiveau, Long idEtudiant) {
        Niveau niveau = niveauDao.findById(idNiveau).orElse(null);
        if (niveau == null) {
            throw new EntityNotFoundException("Niveau not found with id: " + idNiveau);
        }

        Optional<Etudiant> optionalEtudiant = etudiantDao.findById(idEtudiant);
        if (optionalEtudiant.isEmpty()) {
            throw new EntityNotFoundException("Étudiant not found with id: " + idEtudiant);
        }

        Etudiant etudiant = optionalEtudiant.get();
        etudiant.setNiveau(niveau);

        if (niveau.getEtudiants() == null) {
            niveau.setEtudiants(new ArrayList<>());
        }

        niveau.getEtudiants().add(etudiant);

        etudiantDao.save(etudiant);
        niveauDao.save(niveau);
    }
    public List<Niveau> getAllNiveau(){
        List<Niveau> niveauList = niveauDao.findAll();
        if (!niveauList.isEmpty()){
            return niveauList;
        }else {
            throw new EntityNotFoundException("Niveau not found");
        }
    }

}
