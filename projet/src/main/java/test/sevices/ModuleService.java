package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.Element;
import test.bo.Module;
import test.bo.Niveau;
import test.dao.ElementDao;
import test.dao.ModuleDao;
import test.dao.NiveauDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModuleService {
    @Autowired
    private NiveauDao niveauDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ElementDao elementDao;

    public Module createModule(String nom ,long niveauId){
        Optional<Niveau> niveauOptional = niveauDao.findNiveauByIdNiveau(niveauId);
        if (niveauOptional.isPresent()){
            Module module = new Module(nom,niveauOptional.get());
            moduleDao.save(module);
            return module;
        }
        return null;
    }
    public void deleteModuleById(Long idModule){
        if (!moduleDao.existsById(idModule)){
            throw new EntityNotFoundException("Module not found with id: " + idModule);
        }
        List<Element> elements = elementDao.getElementsByModule(idModule);
        for (Element element : elements) {
            element.setModule(null);
            elementDao.save(element);
        }
        moduleDao.deleteByIdModule(idModule);
    }
    public void addElementToModule(Long idElement, Long idModule) {
        Module module = moduleDao.getModuleByIdModule(idModule);
        if (module == null) {
            throw new EntityNotFoundException("Module not found with id: " + idModule);
        }

        Optional<Element> optionalElement = elementDao.findElementByIdElement(idElement);
        if (optionalElement.isEmpty()) {
            throw new EntityNotFoundException("Element not found with id: " + idElement);
        }

        Element element = optionalElement.get();

        if (module.getElements() == null) {
            module.setElements(new ArrayList<>());
        }

        module.getElements().add(element);

        element.setModule(module);

        moduleDao.save(module);
        elementDao.save(element);
    }
    public List<Module> getAllModules(){
        List<Module> modules = moduleDao.findAll();
        if (!modules.isEmpty()){
            return modules;
        }else {
            throw new EntityNotFoundException("Modules not found");
        }
    }


}
