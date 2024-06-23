package cn.sm1234.service;


import cn.sm1234.domain.User;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * restful 风格接口，默认格式，使用XML，soap
 */
public interface UserService {
    @POST
    public void saveUser(User user);
    @PUT
    public void updateUser(User user);

    @DELETE
    @Path("/{id}") // http://localhost:8888/userService/1
    public void deleteUser(@PathParam("id") Integer id);

    @GET
    public List<User> findAllUser();

    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") Integer id);
}
