package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Element;
import test.bo.Enseignant;
import test.bo.Module;
import test.dao.ElementDao;
import test.dao.EnseignantDao;
import test.dao.ModuleDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ElementService {
    @Autowired
    private ElementDao elementDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private EnseignantDao enseignantDao;
    public Element createElement(String nom, long enseignantId, long moduleId) {
        Optional<Module> moduleOptional = moduleDao.findModuleByIdModule(moduleId);
        Optional<Enseignant> optionalEnseignant = enseignantDao.findEnseignantById(enseignantId);

        if (moduleOptional.isPresent() && optionalEnseignant.isPresent()) {
            Element element = new Element(nom, moduleOptional.get(), optionalEnseignant.get());
            elementDao.save(element);
            return element;
        }
        return null;
    }

    public void deleteElementById(Long id) throws EntityNotFoundException {
        if (!elementDao.existsById(id)) {
            throw new EntityNotFoundException("Element not found with id: " + id);
        }
        elementDao.deleteById(id);
    }
    public void deleteElementByName(String name){
        elementDao.deleteElementByNom(name);
    }
    public Element findElementByNom(String nom){
        return elementDao.findElementByNom(nom).orElse(null);
    }
    public List<Element> getAllElements(){
        List<Element> elements = elementDao.findAll();
        if (!elements.isEmpty()){
            return elements;
        }else {
            throw new EntityNotFoundException("Elements not found");
        }
    }
}
