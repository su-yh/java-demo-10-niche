package cn.sm1234.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement // 默认情况下传的格式是XML，即soap模式，需要用到这个注解，否则会报错 cxf在传递对象的时候，需要对该对象添加该注解
public class User {
    private Integer id;
    private String name;
    private String gender;
}
