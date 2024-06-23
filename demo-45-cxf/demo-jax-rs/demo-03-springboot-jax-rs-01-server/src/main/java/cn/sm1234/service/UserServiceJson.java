package cn.sm1234.service;


import cn.sm1234.domain.User;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * restful 风格接口，改为json 格式
 */
public interface UserServiceJson {
    @POST
    // @Consumes(MediaType.APPLICATION_JSON)  // TODO: 这个是怎么用的呢??? 在请求端
    public void saveUser(User user);
    @PUT
    // @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User user);

    @DELETE
    @Path("/{id}") // http://localhost:8888/userService/1
    public void deleteUser(@PathParam("id") Integer id);

    @GET
    public List<User> findAllUser();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)   // 该注解表示，返回值处理为json 格式
    public User findById(@PathParam("id") Integer id);
}
