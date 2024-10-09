package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Enseignant;
import test.bo.GroupeEnseignant;
import test.dao.EnseignantDao;
import test.dao.GroupeEnseignantDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupeEnseignantService {
    @Autowired
    private GroupeEnseignantDao groupeEnseignantDao;
    @Autowired
    private EnseignantDao enseignantDao;
    public void createGrpEns(@NotNull List<Integer> enseignantIds, String name) {
        List<Enseignant> enseignantList = new ArrayList<>();
        for (Integer enseignantId : enseignantIds) {
            Optional<Enseignant> enseignantOptional = enseignantDao.findEnseignantById(Long.valueOf(enseignantId));
            enseignantOptional.ifPresent(enseignantList::add);
        }
        if (enseignantList.size() != enseignantIds.size()) {
            throw new EntityNotFoundException("One or more enseignants not found");
        }
        GroupeEnseignant grp = new GroupeEnseignant(name, enseignantList);
        groupeEnseignantDao.save(grp);
    }
    public void addEnseignantToGrpEnseignant(long idEnseignant, long idGrpEnseignant) {
        Optional<Enseignant> enseignantOptional = enseignantDao.findEnseignantById(idEnseignant);
        Optional<GroupeEnseignant> groupeEnseignantOptional = groupeEnseignantDao.findGroupeEnseignantByIdGroupeEnseignant(idGrpEnseignant);

        if (enseignantOptional.isPresent() && groupeEnseignantOptional.isPresent()) {
            GroupeEnseignant groupeEnseignant = groupeEnseignantOptional.get();
            Enseignant enseignant = enseignantOptional.get();
            groupeEnseignant.getEnseignants().add(enseignant);
            groupeEnseignantDao.save(groupeEnseignant);
        } else {
            if (enseignantOptional.isEmpty()) {
                throw new EntityNotFoundException("Enseignant not found with id: " + idEnseignant);
            }
            throw new EntityNotFoundException("GroupeEnseignant not found with id: " + idGrpEnseignant);
        }
    }
    public List<GroupeEnseignant> getAllGroupeEnseignants(){
        List<GroupeEnseignant> groupeEnseignantList = groupeEnseignantDao.findAll();
        if (!groupeEnseignantList.isEmpty()){
            return groupeEnseignantList;
        }else {
            throw new EntityNotFoundException("Groupe Enseignants not found");
        }
    }
    public void deleteGroupeEnseignant(long idGroupe){
        Optional<GroupeEnseignant> groupeEnseignantOptional = groupeEnseignantDao.findGroupeEnseignantByIdGroupeEnseignant(idGroupe);
        if (groupeEnseignantOptional.isPresent()){
            groupeEnseignantDao.deleteByIdGroupeEnseignant(idGroupe);
        }else {
            throw new EntityNotFoundException("groupe Enseignant not found");
        }
    }
}
