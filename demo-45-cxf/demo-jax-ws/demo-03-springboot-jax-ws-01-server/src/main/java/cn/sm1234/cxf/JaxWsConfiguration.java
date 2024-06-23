package cn.sm1234.cxf;

import cn.sm1234.service.UserService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

@Configuration
public class JaxWsConfiguration {
    @Resource
    private Bus bus;

    @Resource
    private UserService userService;

    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(bus, userService);
        // 注意：spring boot 发布的cxf 有一个前置路径：${cxf.path:services}
        // 即：访问时的路径为：http://localhost:8080/services/userService
        endpoint.publish("/userService");
        return endpoint;
    }
}
