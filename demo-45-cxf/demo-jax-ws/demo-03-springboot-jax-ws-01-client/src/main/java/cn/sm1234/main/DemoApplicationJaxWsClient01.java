package cn.sm1234.main;

import cn.sm1234.domain.User;
import cn.sm1234.service.UserService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class DemoApplicationJaxWsClient01 {
    public static void main(String[] args) {
        // 1. 创建工厂对象
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        // 2. 设置参数
        // 2.1 设置访问路径 - spring boot 有一个前置路径${cxf.path:services}
        factory.setAddress("http://localhost:8080/services/userService");
        // 2.2 设置接口(似乎接口的包名需要一致才行哟。)
        factory.setServiceClass(UserService.class);
        // 3. 创建接口的代理类对象
        UserService userService = (UserService) factory.create();

        // 设置日志拦截器
        Client client = ClientProxy.getClient(userService);
        // 输入拦截器
        client.getInInterceptors().add(new LoggingInInterceptor());
        // 输出拦截器
        client.getOutInterceptors().add(new LoggingOutInterceptor());

        // 4. 调用
        userService.saveUser(new User(1, "小张", "男"));
    }
}
