package com.suyh.demo04.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.suyh.demo04.json.deserializer.BizStringArrayDeserializer;
import com.suyh.demo04.json.deserializer.BizStringCollectionDeserializer;
import com.suyh.demo04.json.deserializer.BizStringDeserializer;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-14
 */
public class JacksonMain {
    public static void main(String[] args) {
        if (false) {
            BizStringCollectionDeserializer.rebuildStringCollectionDeserializer();
            return;
        }
        if (true) {
            BizStringArrayDeserializer.rebuildStringArrayDeserializer();
            BizStringCollectionDeserializer.rebuildStringCollectionDeserializer();

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, NumberSerializer.instance)
                    .addSerializer(Long.TYPE, NumberSerializer.instance);
//            simpleModule.addDeserializer(String.class, StringDeserializer.instance);
            simpleModule.addDeserializer(String.class, BizStringDeserializer.instance);
            objectMapper.registerModules(simpleModule);

            JsonUtils.initMapper(objectMapper);

            String json = "{\"username\": \"    ddd\", \"roles\": [\"abcd\", \"    \", \" ccc\", \"ddd   \" ], \"menus\": [\"messs\", \"   \", \"   c\", \"d   \" ], \n" +
                    "    \"temp\": {\"username\": \"username\", \"roles\": [\"abcd\", \"    \", \" ccc\", \"ddd   \" ], \"menus\": [\"messs\", \"   \", \"   c\", \"d   \" ] }, \"tempList\": [], \"temps\": [] }";
            System.out.println("json: " + json);
            TmpVo tmpVo = JsonUtils.deserialize(json, TmpVo.class);
            System.out.println(tmpVo);
            return;
        }
    }

    @ToString
    public static class TmpVo {
        private String username;

        private String[] roles;

        private List<String> menus;

        private Temp temp;

        private List<Temp> tempList;

//        private Temp[] temps;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public List<String> getMenus() {
            return menus;
        }

        public void setMenus(List<String> menus) {
            this.menus = menus;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public List<Temp> getTempList() {
            return tempList;
        }

        public void setTempList(List<Temp> tempList) {
            this.tempList = tempList;
        }

//        public Temp[] getTemps() {
//            return temps;
//        }
//
//        public void setTemps(Temp[] temps) {
//            this.temps = temps;
//        }
    }

    @Data
    public static class Temp {
        private String username;

        private String[] roles;

        private List<String> menus;
    }
}
