package cn.sm1234.cxf;

import cn.sm1234.service.UserServiceJson;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class JaxRsConfiguration {
    @Resource
    private Bus bus;

    @Resource
    private UserServiceJson userService;

    @Bean
    public Server endpoint() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        // 当前访问地址：http://localhost:8080/services/userService/10
        endpoint.setAddress("/userService");
        endpoint.setBus(bus);
        endpoint.setServiceBean(userService);

        return endpoint.create();
    }
}
