/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Dao.UserDao;
import Models.User;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class UserService {
    
    private UserDao user = new UserDao();
    
    public List<User> getAll(){
        return user.getAllUser();
    }
    
    public User getId(User user){
        return this.user.getByYd(user);
    }
    
    public boolean save(User user) throws Exception{
        return this.user.saveUser(user);
    }
    
    public boolean update(User user){
        return this.user.updateUser(user);
    }
    
    public boolean delete(User user){
        return this.user.deleteUser(user);
    }
    
}
