package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.bo.Etudiant;
import test.dao.EtudiantDao;

import java.util.List;

@Service
public class EtudiantService {
    private EtudiantDao etudiantDao;
    @Autowired
    public EtudiantService(EtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }
    public List<Etudiant> getAllEtudiant(){
        List<Etudiant> etudiantList = etudiantDao.findAll();
        if (!etudiantList.isEmpty()){
            return etudiantList;
        }else {
            throw new EntityNotFoundException("Etudiants not found");
        }
    }


}
