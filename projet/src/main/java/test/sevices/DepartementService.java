package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Departement;
import test.bo.Enseignant;
import test.bo.Filliere;
import test.dao.DepartementDao;
import test.dao.EnseignantDao;
import test.dao.FilliereDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartementService {
    @Autowired
    private DepartementDao departementDao;
    @Autowired
    private EnseignantDao enseignantDao;
    @Autowired
    private FilliereDao filliereDao;

    public Departement createDepartement(String nom,Long coordonateurId){
        Optional<Enseignant> coordonateur = enseignantDao.findEnseignantById(coordonateurId);
        if (coordonateur.isPresent()){
            Departement departement = new Departement(nom,coordonateur.get());
            return departementDao.save(departement);
        }
        return null;
    }
    public void deleteDepartement(Long idDepartement){
        if (!departementDao.existsById(idDepartement)){
            throw new EntityNotFoundException("Departement not found with id: " + idDepartement);
        }
        List<Enseignant> enseignants = enseignantDao.getEnseignantByDepartement(idDepartement);
        for (Enseignant enseignant:enseignants){
            enseignant.setDepartement(null);
            enseignantDao.save(enseignant);
        }
        List<Filliere> fillieres = filliereDao.getFilliereByDepartement(idDepartement);
        for (Filliere filliere:fillieres) {
            filliere.setDepartement(null);
            filliereDao.save(filliere);
        }
        filliereDao.deleteByIdFilliere(idDepartement);
    }
    public void addFilliereToDepartement(Long idFilliere, Long idDepartement) {
        Departement departement = departementDao.findById(idDepartement).orElse(null);
        if (departement == null) {
            throw new EntityNotFoundException("Departement not found with id: " + idDepartement);
        }

        Optional<Filliere> optionalFilliere = filliereDao.findById(idFilliere);
        if (optionalFilliere.isEmpty()) {
            throw new EntityNotFoundException("Filliere not found with id: " + idFilliere);
        }

        Filliere filliere = optionalFilliere.get();

        if (departement.getFillieres() == null) {
            departement.setFillieres(new ArrayList<>());
        }

        departement.getFillieres().add(filliere);

        filliere.setDepartement(departement);

        departementDao.save(departement);
        filliereDao.save(filliere);
    }
    public void addEnseignantToDepartement(Long idEnseignant, Long idDepartement) {
        Departement departement = departementDao.findById(idDepartement).orElse(null);
        if (departement == null) {
            throw new EntityNotFoundException("Departement not found with id: " + idDepartement);
        }

        Optional<Enseignant> optionalEnseignant = enseignantDao.findById(idEnseignant);
        if (optionalEnseignant.isEmpty()) {
            throw new EntityNotFoundException("Enseignant not found with id: " + idEnseignant);
        }

        Enseignant enseignant = optionalEnseignant.get();
        enseignant.setDepartement(departement);

        if (departement.getEnseignants() == null) {
            departement.setEnseignants(new ArrayList<>());
        }

        departement.getEnseignants().add(enseignant);

        enseignantDao.save(enseignant);
        departementDao.save(departement);
    }
    public List<Departement> getAllDepartments(){
        List<Departement>  departementList = departementDao.findAll();
        if (!departementList.isEmpty()){
            return departementList;
        }else {
            throw new EntityNotFoundException("Departments not found");
        }
    }
}
