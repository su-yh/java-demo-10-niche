package com.suyh4002.init;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化，项目启动成功之后触发调用。
 */
@Component
public class InitRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 这里触发NacosDataSourceInitFunc 的初始化
        // 主要是 InitFunc 接口的所有子类实现的调用。
        // 可以参考dashboard 的main() 方法: new Thread(InitExecutor::doInit).start();
        // suyh - 如果不在这里处理，那就只有第一次访问接口的时候才会初始sentinel client 的功能，心跳也是。
        InitExecutor.doInit();
    }
}
