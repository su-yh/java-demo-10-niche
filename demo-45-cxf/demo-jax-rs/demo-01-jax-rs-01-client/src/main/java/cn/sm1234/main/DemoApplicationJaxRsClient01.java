package cn.sm1234.main;

import cn.sm1234.domain.User;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

public class DemoApplicationJaxRsClient01 {
    public static void main(String[] args) {
        testJson();
    }

    /**
     * cxf 使用xml 格式进行传输
     */
    private static void testXml() {
        WebClient.create("http://localhost:8159/userService").post(new User(1, "小张", "男"));
        WebClient.create("http://localhost:8159/userService").put(new User(2, "小红", "女"));
        WebClient.create("http://localhost:8159/userService/10").delete();
        Collection<? extends User> users = WebClient.create("http://localhost:8159/userService").getCollection(User.class);
        User user = WebClient.create("http://localhost:8159/userService/10").get(User.class);
    }

    /**
     * cxf 使用json 格式进行传输
     */
    private static void testJson() {
        WebClient.create("http://localhost:8159/userService/json").accept(MediaType.APPLICATION_JSON).post(new User(1, "小张", "男"));
        WebClient.create("http://localhost:8159/userService/json").accept(MediaType.APPLICATION_JSON_TYPE).put(new User(2, "小红", "女"));
        WebClient.create("http://localhost:8159/userService/json/10").accept(MediaType.APPLICATION_JSON_TYPE).delete();
        Collection<? extends User> users = WebClient.create("http://localhost:8159/userService/json").accept(MediaType.APPLICATION_JSON_TYPE).getCollection(User.class);
        User user = WebClient.create("http://localhost:8159/userService/json/10").accept(MediaType.APPLICATION_JSON_TYPE).get(User.class);
    }
}
