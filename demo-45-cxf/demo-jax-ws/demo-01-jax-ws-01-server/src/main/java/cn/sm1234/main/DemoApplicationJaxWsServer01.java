package cn.sm1234.main;

import cn.sm1234.service.UserService;
import cn.sm1234.service.impl.UserServiceImpl;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class DemoApplicationJaxWsServer01 {
    public static void main(String[] args) {
        // 1. 创建工厂对象
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();

        // 2. 设置参数
        // 2.1 设置访问地址(让客户端访问)
        factory.setAddress("http://localhost:8159/userService");
        // 2.2 设置接口类型
        factory.setServiceClass(UserService.class);
        // 2.3 设置实现类对象(必须是有注解@WebService标注的类)
        factory.setServiceBean(new UserServiceImpl());

        // 设置日志拦截器
        // 输入拦截器
        factory.getInInterceptors().add(new LoggingInInterceptor());
        // 输出拦截器
        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        // 3. 发布接口
        factory.create();

        System.out.println("WebService 服务程序发布成功...");
    }
}
