package cn.sm1234.service;


import cn.sm1234.domain.User;

import javax.jws.WebService;
import java.util.List;

/**
 * @WebService 把该类标注为WebService 接口，可被远程调用
 * JDK1.6 以上
 */
@WebService
public interface UserService {
    public void saveUser(User user);
    public void updateUser(User user);
    public void deleteUser(Integer id);
    public List<User> findAllUser();
    public User findById(Integer id);
}
