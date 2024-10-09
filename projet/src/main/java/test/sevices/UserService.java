package test.sevices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.bo.User;
import test.dao.UserDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers(){
        List<User> users = userDao.findAll();
        if (!users.isEmpty()){
            return users;
        }else {
            throw new EntityNotFoundException("Users not found");
        }
    }
    public User deleteUser(Long id){
        Optional<User> optionalUser= userDao.findUserById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            userDao.delete(user);
            return user;
        }else {
            throw new EntityNotFoundException("User Not found with this id:"+id);
        }
    }
}
