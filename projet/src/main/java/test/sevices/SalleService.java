package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Examen;
import test.bo.Niveau;
import test.bo.Salle;
import test.dao.ExamenDao;
import test.dao.SalleDao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalleService {
    @Autowired
    private SalleDao salleDao;
    @Autowired
    private ExamenDao examenDao;
    public List<Salle> getAllSallesEmpty(Date date){
        List<Salle> salleList = salleDao.findEmptySallesByExamDate(date);
        if (!salleList.isEmpty()){
            return salleList;
        }else {
            throw new EntityNotFoundException("Salles not found");
        }
    }
    public void affecterSallesExam(List<Long> idSalles, Long idExam) {
        Optional<Examen> optionalExamen = examenDao.findById(idExam);
        if (optionalExamen.isEmpty()) {
            throw new EntityNotFoundException("Examen not found with id: " + idExam);
        }
        Examen examen = optionalExamen.get();
        for (Long idSalle : idSalles) {
            Optional<Salle> optionalSalle = salleDao.findById(idSalle);
            if (optionalSalle.isEmpty()) {
                throw new EntityNotFoundException("Salle not found with id: " + idSalle);
            }
            Salle salle = optionalSalle.get();
            examen.getSalles().add(salle);
            salle.setExamen(examen);
        }
        examenDao.save(examen);
        for (Long idSalle : idSalles) {
            Optional<Salle> optionalSalle = salleDao.findById(idSalle);
            optionalSalle.ifPresent(salle -> salleDao.save(salle));
        }
    }
    public List<Salle> getAllSalles(){
        List<Salle> salleList = salleDao.findAll();
        if (!salleList.isEmpty()){
            return salleList;
        }else {
            throw new EntityNotFoundException("Salle not found");
        }
    }

}
