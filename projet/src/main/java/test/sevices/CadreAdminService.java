package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.CadreAdmin;
import test.bo.User;
import test.dao.CadreAdminDao;
import test.dao.UserDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CadreAdminService {
    @Autowired
    private CadreAdminDao cadreAdminDao;
    @Autowired
    private UserDao userDao;
    public List<CadreAdmin> getAllCadreAdmin(){
        List<CadreAdmin> users = cadreAdminDao.findAll();
        if (!users.isEmpty()){
            return users;
        }else {
            throw new EntityNotFoundException("Users not found");
        }
    }
}
