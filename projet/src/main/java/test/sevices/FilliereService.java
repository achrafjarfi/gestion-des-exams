package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Enseignant;
import test.bo.Filliere;
import test.bo.Module;
import test.bo.Niveau;
import test.dao.EnseignantDao;
import test.dao.FilliereDao;
import test.dao.ModuleDao;
import test.dao.NiveauDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class FilliereService {
    @Autowired
    private EnseignantDao enseignantDao;
    @Autowired
    private FilliereDao filliereDao;
    @Autowired
    private NiveauDao niveauDao;
    public Filliere createFiliere(String nom,long chefFiliereId,String allias){
        Optional<Enseignant> chefFiliere = enseignantDao.findEnseignantById(chefFiliereId);
        if (chefFiliere.isPresent()){
            Filliere filliere = new Filliere(nom,allias,chefFiliere.get());
            return filliereDao.save(filliere);
        }
        return null;
    }

    public void deleteFilliereById(Long idFilliere){
        if(!filliereDao.existsById(idFilliere)){
            throw new EntityNotFoundException("Filliere not found with id: " + idFilliere);
        }
        List<Niveau> niveauList = niveauDao.getNiveauByFilliere(idFilliere);
        for (Niveau niveau:niveauList) {
            niveau.setFilliere(null);
            niveauDao.save(niveau);
        }
        filliereDao.deleteByIdFilliere(idFilliere);
    }

    public void addNiveauToFilliere(Long idNiveau, Long idFilliere) {
        Filliere filliere = filliereDao.findById(idFilliere).orElse(null);
        if (filliere == null) {
            throw new EntityNotFoundException("Filliere not found with id: " + idFilliere);
        }

        Optional<Niveau> optionalNiveau = niveauDao.findById(idNiveau);
        if (optionalNiveau.isEmpty()) {
            throw new EntityNotFoundException("Niveau not found with id: " + idNiveau);
        }

        Niveau niveau = optionalNiveau.get();

        if (filliere.getNiveauList() == null) {
            filliere.setNiveauList(new ArrayList<>());
        }

        filliere.getNiveauList().add(niveau);

        niveau.setFilliere(filliere);

        filliereDao.save(filliere);
        niveauDao.save(niveau);
    }


    public void addEnseignantsToFillieres(List<Long> idFillieres, List<Long> idEnseignants) {
        for (Long idFilliere : idFillieres) {
            Optional<Filliere> optionalFilliere = filliereDao.findById(idFilliere);
            if (optionalFilliere.isEmpty()) {
                throw new EntityNotFoundException("Filliere not found with id: " + idFilliere);
            }

            Filliere filliere = optionalFilliere.get();

            for (Long idEnseignant : idEnseignants) {
                Optional<Enseignant> optionalEnseignant = enseignantDao.findById(idEnseignant);
                if (optionalEnseignant.isEmpty()) {
                    throw new EntityNotFoundException("Enseignant not found with id: " + idEnseignant);
                }

                Enseignant enseignant = optionalEnseignant.get();

                enseignant.getFilieres().add(filliere);
                filliere.getEnseignants().add(enseignant);

                enseignantDao.save(enseignant);
                filliereDao.save(filliere);
            }
        }
    }
    public List<Filliere> getAllFillieres(){
        List<Filliere> fillieres = filliereDao.findAll();
        if (!fillieres.isEmpty()){
            return fillieres;
        }else {
            throw new EntityNotFoundException("Fillieres not found");
        }
    }

}
